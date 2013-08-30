package nl.mvdr.tinustris.gui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import nl.mvdr.tinustris.engine.GameEngine;
import nl.mvdr.tinustris.engine.TinusTrisEngine;
import nl.mvdr.tinustris.input.InputController;
import nl.mvdr.tinustris.input.InputState;
import nl.mvdr.tinustris.input.JInputController;
import nl.mvdr.tinustris.model.GameState;
import nl.mvdr.tinustris.model.Tetromino;

import org.slf4j.bridge.SLF4JBridgeHandler;

import com.sun.javafx.runtime.VersionInfo;

/**
 * Main class and entry point for the entire application.
 * 
 * @author Martijn van de Rijdt
 */
// When testing the application, don't run this class directly from Eclipse. Use TinusTrisTestContext instead.
@Slf4j
public class Tinustris extends Application {
    /** Update rate for the game state. */
    final double GAME_HERTZ = 60.0;
    /** How much time each frame should take for our target frame rate, in ns. */
    final double TIME_BETWEEN_UPDATES = 1_000_000_000 / GAME_HERTZ;
     /** At the very most we will update the game this many times before a new render. **/
    final int MAX_UPDATES_BEFORE_RENDER = 5;
    /** Target frame rate for the game. */
    final double TARGET_FPS = 60;
    /** Target time between renders, in ns. */
    final double TARGET_TIME_BETWEEN_RENDERS = 1_000_000_000 / TARGET_FPS;
    
    /** Indicates whether the game should be running. */
    private boolean running;
    /** Indicates whether the game is paused. */
    private boolean paused;
    /** Input controller. */
    private InputController inputController;
    /** Game engine. */
    private GameEngine gameEngine;
    /** Game renderer. */
    private GameRenderer<Label> gameRenderer;
    /** Label in which the game is shown (in ASCII form). */
    private Label label;
    
    /**
     * Main method.
     * 
     * @param args
     *            command-line parameters
     */
    public static void main(String[] args) {
        // JInput uses java.util.logging; redirect to slf4j.
        installSlf4jBridge();
        
        // Launch the application!
        launch(args);
    }

    /** Installs a bridge for java.util.logging to slf4j. */
    private static void installSlf4jBridge() {
        // remove existing handlers attached to j.u.l root logger
        SLF4JBridgeHandler.removeHandlersForRootLogger();

        // add SLF4JBridgeHandler to j.u.l's root logger, should be done once during
        // the initialization phase of your application
        SLF4JBridgeHandler.install();
    }
    
    /** {@inheritDoc} */
    @Override
    public void start(Stage stage) {
        logVersionInfo();
        
        log.info("Starting application.");
        
        stage.setTitle("Tinustris");

        AnchorPane root = new AnchorPane();
        label = new Label("This will show the input state");
        label.setFont(Font.font("monospaced", 12));
        root.getChildren().add(label);

        stage.setScene(new Scene(root));

        stage.show();

//        // Default size should also be the minimum size.
//        stage.setMinWidth(stage.getWidth());
//        stage.setMinHeight(stage.getHeight());
        
        stage.setMinWidth(640);
        stage.setMinHeight(480);

        log.info("Stage shown.");
        
        startGameLoop();
        log.info("Game loop started.");
    }
    
    /** {@inheritDoc} */
    @Override
    public void stop() throws Exception {
        log.info("Stopping the application.");
        stopGameLoop();
        super.stop();
    }

    /** Logs some version info. */
    private void logVersionInfo() {
        log.info("Classpath: " + System.getProperty("java.class.path"));
        log.info("Library path: " + System.getProperty("java.library.path"));
        log.info("Java vendor: " + System.getProperty("java.vendor"));
        log.info("Java version: " + System.getProperty("java.version"));
        log.info("OS name: " + System.getProperty("os.name"));
        log.info("OS version: " + System.getProperty("os.version"));
        log.info("OS architecture: " + System.getProperty("os.arch"));
        log.info("Using JavaFX runtime version: " + VersionInfo.getRuntimeVersion());
        if (log.isDebugEnabled()) {
            log.debug("Detailed JavaFX version info: ");
            log.debug("  Version: " + VersionInfo.getVersion());
            log.debug("  Runtime version: " + VersionInfo.getRuntimeVersion());
            log.debug("  Build timestamp: " + VersionInfo.getBuildTimestamp());
        }
    }

    /**
     * Starts the game loop.
     * 
     * @param label
     *            the label onto which the game state is drawn
     */
    // TODO move this logic elsewhere
    private void startGameLoop() {
        running = true;
        paused = false;
        
        gameEngine = new TinusTrisEngine();
        gameRenderer = new LabelRenderer();
        inputController = new JInputController();
        
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
        
        // initialise game state
        List<Tetromino> grid = new ArrayList<>(GameState.DEFAULT_WIDTH * GameState.DEFAULT_HEIGHT);
        while (grid.size() != GameState.DEFAULT_WIDTH * GameState.DEFAULT_HEIGHT) {
            grid.add(null);
        }
        grid = Collections.unmodifiableList(grid);        
        GameState gameState = new GameState(grid, GameState.DEFAULT_WIDTH, Tetromino.L, Tetromino.T);

        log.info("Starting main game loop.");
        
        while (running) {
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

                // Render. To do so, we need to calculate interpolation for a smooth render.
                gameRenderer.render(label, gameState);
                frameCount++;
                lastRenderTime = now;

                // Update the frames we got.
                int thisSecond = (int) (lastUpdateTime / 1000000000);
                if (thisSecond > lastSecondTime) {
                    log.info("New second: " + thisSecond + ", frame count: " + frameCount + ", fps: " + fps);
                    fps = frameCount;
                    frameCount = 0;
                    lastSecondTime = thisSecond;
                }

                // Yield until it has been at least the target time between renders. This saves the CPU from hogging.
                while (now - lastRenderTime < TARGET_TIME_BETWEEN_RENDERS
                        && now - lastUpdateTime < TIME_BETWEEN_UPDATES) {
                    Thread.yield();

                    // This stops the app from consuming all your CPU. It makes this slightly less accurate, but is
                    // worth it.
                    // You can remove this line and it will still work (better), your CPU just climbs on certain OSes.
                    // FYI on some OS's this can cause pretty bad stuttering. Scroll down and have a look at different
                    // peoples' solutions to this.
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        throw new IllegalStateException("Unexpected interrupt.", e);
                    }

                    now = System.nanoTime();
                }
            }
        }
        log.info("Finished main game loop.");
    }

    /** Stops the game loop. */
    private void stopGameLoop() {
        running = false;
    }
}
