package nl.mvdr.tinustris.gui;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javafx.animation.Animation;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.PointLight;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import lombok.extern.slf4j.Slf4j;
import nl.mvdr.tinustris.configuration.Configuration;
import nl.mvdr.tinustris.engine.GameEngine;
import nl.mvdr.tinustris.engine.GameLoop;
import nl.mvdr.tinustris.engine.GapGenerator;
import nl.mvdr.tinustris.engine.MultiplayerEngine;
import nl.mvdr.tinustris.engine.OnePlayerEngine;
import nl.mvdr.tinustris.engine.RandomGenerator;
import nl.mvdr.tinustris.engine.RandomTetrominoGenerator;
import nl.mvdr.tinustris.input.InputController;
import nl.mvdr.tinustris.input.JInputController;
import nl.mvdr.tinustris.model.MultiplayerGameState;
import nl.mvdr.tinustris.model.OnePlayerGameState;
import nl.mvdr.tinustris.model.Tetromino;

import org.slf4j.bridge.SLF4JBridgeHandler;

/**
 * Main class and entry point for the entire application.
 * 
 * @author Martijn van de Rijdt
 */
// When testing the application, don't run this class directly from Eclipse. Use TinustrisTestContext instead.
@Slf4j
public class Tinustris extends Application {
    /** Size of the border around the Tetris grid and other UI components. */
    private static final int BORDER_SIZE = 10;
    /** Size of the margin between windows. */
    private static final int MARGIN = 10;
    
    // TODO remove the following constant configuration and let the user input these values
    /** Game configuration.*/
    private static final Configuration CONFIGURATION = new Configuration() {};
    
    /** Game loop. */
    private GameLoop<?> gameLoop;
    
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
        // remove existing handlers attached to java.util.logging root logger
        SLF4JBridgeHandler.removeHandlersForRootLogger();

        // add SLF4JBridgeHandler to java.util.logging's root logger
        SLF4JBridgeHandler.install();
    }
    
    /** {@inheritDoc} */
    @Override
    public void start(Stage stage) {
        log.info("Starting application.");
        logVersionInfo();
        
        Thread.currentThread().setUncaughtExceptionHandler(
                (thread, throwable) -> log.error("Uncaught runtime exception on JavaFX Thread", throwable));
        
        BlockCreator blockCreator = CONFIGURATION.getGraphicsStyle().makeBlockCreator();
        
        // construct the user interface
        stage.setTitle("Tinustris");
        
        int widthInBlocks = OnePlayerGameState.DEFAULT_WIDTH;
        int heightInBlocks = OnePlayerGameState.DEFAULT_HEIGHT - OnePlayerGameState.VANISH_ZONE_HEIGHT;
        
        OnePlayerGameRenderer onePlayerRenderer = new OnePlayerGameRenderer(widthInBlocks, heightInBlocks, blockCreator);

        Scene scene = new Scene(onePlayerRenderer, 
                widthInBlocks * BlockGroupRenderer.BLOCK_SIZE + 4 * BORDER_SIZE + 3 * MARGIN + 
                    4 * GridRenderer.BLOCK_SIZE,
                heightInBlocks * BlockGroupRenderer.BLOCK_SIZE + 2 * BORDER_SIZE + 2 * MARGIN,
                Color.GRAY);
        
        if (CONFIGURATION.getGraphicsStyle() == GraphicsStyle.THREE_DIMENSIONAL) {
            setupLightsAndCamera(onePlayerRenderer, scene);
        }
        
        stage.setScene(scene);
        stage.show();
        // Default size should also be the minimum size.
        stage.setMinWidth(stage.getWidth());
        stage.setMinHeight(stage.getHeight());
        log.info("Stage shown.");
        
        // setup necessary components
        initGameLoop(onePlayerRenderer);

        log.info("Ready to start game loop: " + gameLoop);
        gameLoop.start();
        log.info("Game loop started in separate thread.");
    }

    /**
     * Initialises the game loop. This method also initialises the input controller, game engine and all other components needed by the game loop.
     * 
     * @param renderer one player game renderer
     */
    private void initGameLoop(GameRenderer<OnePlayerGameState> renderer) {
        int numPlayers = CONFIGURATION.getNumberOfPlayers();
        
        List<InputController> inputControllers = IntStream.range(0, numPlayers)
                .mapToObj(i -> CONFIGURATION.getJInputControllerConfiguration(i))
                .map(JInputController::new)
                .collect(Collectors.toList());
        RandomGenerator<Tetromino> tetrominoGenerator = new RandomTetrominoGenerator();
        RandomGenerator<Integer> gapGenerator = new GapGenerator(OnePlayerGameState.DEFAULT_WIDTH);
        GameEngine<OnePlayerGameState> onePlayerEngine = new OnePlayerEngine(tetrominoGenerator,
                CONFIGURATION.getBehavior(), CONFIGURATION.getStartLevel(), gapGenerator);
        
        if (numPlayers < 1) {
            throw new IllegalStateException("Invalid configuration. The number of players must be at least 1, was: "
                    + numPlayers);
        } else if (numPlayers == 1) {
            // single player
            gameLoop = new GameLoop<>(inputControllers, onePlayerEngine, renderer);
        } else {
            // local multiplayer
            GameEngine<MultiplayerGameState> gameEngine = new MultiplayerEngine(numPlayers, onePlayerEngine);
            MultiplayerGameRenderer multiplayerRenderer = new MultiplayerGameRenderer(renderer, 0);
            gameLoop = new GameLoop<>(inputControllers, gameEngine, multiplayerRenderer);
        }
    }

    /**
     * Adds a camera and a light source.
     * 
     * @param parent
     *            group containing all 3D shapes that should be lit by the light
     * @param scene
     *            scene
     */
    private void setupLightsAndCamera(Group parent, Scene scene) {
        scene.setCamera(new PerspectiveCamera());

        parent.getChildren().add(createLight(150, 700, -250));
    }

    /**
     * Creates a light at (around) the given location.
     * 
     * @param x
     *            x coordinate
     * @param y
     *            y coordinate
     * @param z
     *            z coordinate
     * @return light
     */
    private PointLight createLight(double x, double y, double z) {
        PointLight light = new PointLight(Color.WHITE);
        
        TranslateTransition transition = new TranslateTransition(new Duration(5_000), light);
        transition.setFromX(x - 50);
        transition.setFromY(y - 50);
        transition.setFromZ(z - 50);
        transition.setToX(x + 50);
        transition.setToY(y + 50);
        transition.setToZ(z + 50);
        transition.setAutoReverse(true);
        transition.setCycleCount(Animation.INDEFINITE);
        transition.play();
        
        return light;
    }

    /** Logs some version info. */
    // default visibility for unit tests
    void logVersionInfo() {
        if (log.isInfoEnabled()) {
            String version = retrieveVersion();
            if (version != null) {
                log.info("Application version: " + version);
            } else {
                log.info("Äpplication version unknown.");
            }

            log.info("Classpath: " + System.getProperty("java.class.path"));
            log.info("Library path: " + System.getProperty("java.library.path"));
            log.info("Java vendor: " + System.getProperty("java.vendor"));
            log.info("Java version: " + System.getProperty("java.version"));
            log.info("OS name: " + System.getProperty("os.name"));
            log.info("OS version: " + System.getProperty("os.version"));
            log.info("OS architecture: " + System.getProperty("os.arch"));
        }
    }
    
    /**
     * Returns the version number from the jar manifest file.
     * 
     * @return version number, or null if it cannot be determined
     */
    private String retrieveVersion() {
        String result;
        Package p = Tinustris.class.getPackage();
        if (p != null) {
            result = p.getImplementationVersion();
        } else {
            result = null;
        }
        return result;
    }
    
    /** {@inheritDoc} */
    @Override
    public void stop() throws Exception {
        log.info("Stopping the application.");
        
        if (gameLoop != null) {
            gameLoop.stop();
        }
        
        super.stop();
        log.info("Stopped.");
    }
}
