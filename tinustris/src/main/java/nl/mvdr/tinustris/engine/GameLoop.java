package nl.mvdr.tinustris.engine;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import nl.mvdr.tinustris.gui.GameRenderer;
import nl.mvdr.tinustris.input.InputController;
import nl.mvdr.tinustris.input.InputState;
import nl.mvdr.tinustris.model.GameState;

/**
 * Offers functionality for starting and stopping the game loop.
 * 
 * @param <S> game state type
 * @param <T> input type
 * 
 * @author Martijn van de Rijdt
 */
@Slf4j
@RequiredArgsConstructor
@ToString
public class GameLoop<S extends GameState, T extends Enum<T>> {
    /** Update rate for the game state. */
    private static final double GAME_HERTZ = 60.0;
    /** How much time each frame should take for our target update rate, in nanoseconds. */
    public static final double TIME_BETWEEN_UPDATES = 1_000_000_000 / GAME_HERTZ;
     /** At the very most we will update the game this many times before a new render. **/
    private static final int MAX_UPDATES_BEFORE_RENDER = 5;
    /** Target frame rate for rendering the game. May be lower than the update rate. */
    private static final double TARGET_FPS = GAME_HERTZ;
    /** Target time between renders, in nanoseconds. */
    private static final double TARGET_TIME_BETWEEN_RENDERS = 1_000_000_000 / TARGET_FPS;
    
    /** Input controllers. */
    @NonNull
    private final List<InputController<T>> inputControllers;
    /** Game engine. */
    @NonNull
    private final GameEngine<S, T> gameEngine;
    /** Game renderer. */
    @NonNull
    private final GameRenderer<S> gameRenderer;

    /** Indicates whether the game should be running. */
    @Getter
    private boolean running;
    /** Indicates whether the game is paused. */
    private boolean paused;
    
    /** Starts the game loop. */
    public void start() {
        running = true;
        paused = false;
        
        new Thread(this::gameLoop, "Game loop").start();
    }

    /** Game loop. Should be run on a dedicated thread. */
    // based on: Game Loops! by Eli Delventhal (http://www.java-gaming.org/index.php?topic=24220.0)
    private void gameLoop() {
        log.info("Starting game loop.");
        
        // The moment the game state was last updated.
        double lastUpdateTime = System.nanoTime();
        // The moment the game was last rendered.
        double lastRenderTime = System.nanoTime();

        // Number of frames processed in the current second.
        int framesThisSecond = 0;
        // Start of the current second.
        int lastSecondTime = (int) (lastUpdateTime / 1_000_000_000);
        
        // Total frame counter.
        int totalUpdateCount = 0;

        S state = gameEngine.initGameState();

        gameRenderer.render(state);

        log.info("Starting main game loop.");

        try {
            while (running && !state.isGameOver()) {
                double now = System.nanoTime();
                int updateCount = 0;

                if (!paused) {
                    // Do as many game updates as we need to, potentially playing catchup.
                    while (TIME_BETWEEN_UPDATES < now - lastUpdateTime && updateCount < MAX_UPDATES_BEFORE_RENDER) {
                        List<InputState<T>> inputStates = retrieveInputStates(totalUpdateCount);
                        
                        state = gameEngine.computeNextState(state, inputStates);

                        lastUpdateTime += TIME_BETWEEN_UPDATES;
                        updateCount++;
                        totalUpdateCount++;
                    }

                    // Render.
                    gameRenderer.render(state);
                    framesThisSecond++;
                    lastRenderTime = now;

                    // Log the number of frames.
                    int thisSecond = (int) (lastUpdateTime / 1_000_000_000);
                    if (lastSecondTime < thisSecond) {
                        log.info("New second: {}, frames in previous second: {}, total update count: {}.", thisSecond, framesThisSecond, totalUpdateCount);
                        framesThisSecond = 0;
                        lastSecondTime = thisSecond;
                    }

                    // Yield until it has been at least the target time between renders. This saves the CPU from
                    // hogging.
                    while (now - lastRenderTime < TARGET_TIME_BETWEEN_RENDERS
                            && now - lastUpdateTime < TIME_BETWEEN_UPDATES) {
                        Thread.yield();

                        // Stop the app from consuming all CPU.
                        Thread.sleep(1);

                        now = System.nanoTime();
                    }
                }
            }
        } catch (RuntimeException | InterruptedException e) {
            // In case of InterruptedException: no need to re-interrupt the thread, it will terminate immediately.
            log.error("Fatal exception encountered in game loop.", e);
        }
        running = false;
        log.info("Finished main game loop. Final game state: {}", state);
    }

    /**
     * Retrieves the current inputs for all players.
     * 
     * @param updateIndex index of the current frame / update
     * @return inputs
     */
    private List<InputState<T>> retrieveInputStates(int updateIndex) {
        // Get the input states.
        return inputControllers.stream()
            .map(InputController::getInputState)
            .collect(Collectors.toList());
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
