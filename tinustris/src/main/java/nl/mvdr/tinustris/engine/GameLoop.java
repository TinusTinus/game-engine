package nl.mvdr.tinustris.engine;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.mvdr.tinustris.gui.GameRenderer;
import nl.mvdr.tinustris.input.InputController;
import nl.mvdr.tinustris.input.InputState;
import nl.mvdr.tinustris.model.GameState;

/**
 * Offers functionality for starting and stopping the game loop.
 * 
 * @param <S> game state type
 * 
 * @author Martijn van de Rijdt
 */
@Slf4j
@RequiredArgsConstructor
public class GameLoop<S extends GameState> {
    /** Update rate for the game state. */
    public static final double GAME_HERTZ = 60.0;
    
    /** How much time each frame should take for our target frame rate, in nanoseconds. */
    private static final double TIME_BETWEEN_UPDATES = 1_000_000_000 / GAME_HERTZ;
     /** At the very most we will update the game this many times before a new render. **/
    private static final int MAX_UPDATES_BEFORE_RENDER = 5;
    /** Target frame rate for the game. */
    private static final double TARGET_FPS = 60;
    /** Target time between renders, in nanoseconds. */
    private static final double TARGET_TIME_BETWEEN_RENDERS = 1_000_000_000 / TARGET_FPS;
    
    /** Input controller. */
    @NonNull
    private final InputController inputController;
    /** Game engine. */
    @NonNull
    private final GameEngine<S> gameEngine;
    /** Game renderer. */
    @NonNull
    private final GameRenderer<S> gameRenderer;

    /** Indicates whether the game should be running. */
    private boolean running;
    /** Indicates whether the game is paused. */
    private boolean paused;
    
    /**
     * Starts the game loop.
     * 
     * @param label
     *            the label onto which the game state is drawn
     */
    public void start() {
        running = true;
        paused = false;
        
        Thread loop = new Thread("Game loop") {
            /** {@inheritDoc} */
            @Override
            public void run() {
                gameLoop();
            }
        };
        loop.start();
    }

    /** Game loop. Should be run on a dedicated thread. */
    // based on: Game Loops! by Eli Delventhal (http://www.java-gaming.org/index.php?topic=24220.0)
    private void gameLoop() {
        // The moment the game state was last updated.
        double lastUpdateTime = System.nanoTime();
        // The moment the game was last rendered.
        double lastRenderTime = System.nanoTime();

        int fps = 60;
        int frameCount = 0;

        // Simple way of finding FPS.
        int lastSecondTime = (int) (lastUpdateTime / 1_000_000_000);

        S gameState = gameEngine.initGameState();

        gameRenderer.render(gameState);

        log.info("Starting main game loop.");

        try {
            while (running && !gameState.isGameOver()) {
                double now = System.nanoTime();
                int updateCount = 0;

                if (!paused) {
                    // Do as many game updates as we need to, potentially playing catchup.
                    while (now - lastUpdateTime > TIME_BETWEEN_UPDATES && updateCount < MAX_UPDATES_BEFORE_RENDER) {
                        InputState inputState = inputController.getInputState();
                        gameState = gameEngine.computeNextState(gameState, inputState);

                        lastUpdateTime += TIME_BETWEEN_UPDATES;
                        updateCount++;
                    }

                    // If for some reason an update takes forever, we don't want to do an insane number of catchups.
                    // If you were doing some sort of game that needed to keep EXACT time, you would get rid of this.
                    if (now - lastUpdateTime > TIME_BETWEEN_UPDATES) {
                        lastUpdateTime = now - TIME_BETWEEN_UPDATES;
                    }

                    // Render.
                    gameRenderer.render(gameState);
                    frameCount++;
                    lastRenderTime = now;

                    // Update the frames we got.
                    int thisSecond = (int) (lastUpdateTime / 1_000_000_000);
                    if (thisSecond > lastSecondTime) {
                        log.info("New second: " + thisSecond + ", frame count: " + frameCount + ", fps: " + fps);
                        fps = frameCount;
                        frameCount = 0;
                        lastSecondTime = thisSecond;
                    }

                    // Yield until it has been at least the target time between renders. This saves the CPU from
                    // hogging.
                    while (now - lastRenderTime < TARGET_TIME_BETWEEN_RENDERS
                            && now - lastUpdateTime < TIME_BETWEEN_UPDATES) {
                        Thread.yield();

                        // This stops the app from consuming all your CPU. It makes this slightly less accurate, but is
                        // worth it. You can remove this line and it will still work (better), your CPU just climbs on
                        // certain OSes.
                        try {
                            Thread.sleep(1);
                        } catch (InterruptedException e) {
                            throw new IllegalStateException("Unexpected interrupt.", e);
                        }

                        now = System.nanoTime();
                    }
                }
            }
        } catch (RuntimeException e) {
            log.error("Fatal exception encountered in game loop.", e);
        }
        running = false;
        log.info("Finished main game loop. Final game state: " + gameState);
    }

    /** Stops the game loop. */
    public void stop() {
        log.info("Stopping the game.");
        running = false;
    }
    
    /** Pauses the game. */
    public void pause() {
        log.info("Pausing the game.");
        paused = true;
    }
    
    /** Unpauses the game. */
    public void unpause() {
        log.info("Unpausing the game.");
        paused = false;
    }
    
    /** Pauses or unpauses the game. */
    public void togglePaused() {
        log.info("Toggling the pause.");
        paused = !paused;
        log.info("Game paused: " + paused);
    }
}
