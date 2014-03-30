package nl.mvdr.tinustris.gui;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javafx.animation.Animation;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.scene.PerspectiveCamera;
import javafx.scene.PointLight;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import lombok.extern.slf4j.Slf4j;
import nl.mvdr.tinustris.configuration.Configuration;
import nl.mvdr.tinustris.configuration.PlayerConfiguration;
import nl.mvdr.tinustris.engine.GameEngine;
import nl.mvdr.tinustris.engine.GameLoop;
import nl.mvdr.tinustris.engine.GapGenerator;
import nl.mvdr.tinustris.engine.MultiplayerEngine;
import nl.mvdr.tinustris.engine.OnePlayerEngine;
import nl.mvdr.tinustris.engine.RandomGenerator;
import nl.mvdr.tinustris.engine.RandomTetrominoGenerator;
import nl.mvdr.tinustris.input.InputController;
import nl.mvdr.tinustris.input.JInputController;
import nl.mvdr.tinustris.logging.Logging;
import nl.mvdr.tinustris.model.MultiplayerGameState;
import nl.mvdr.tinustris.model.OnePlayerGameState;
import nl.mvdr.tinustris.model.Tetromino;

/**
 * Main class and entry point for the entire application.
 * 
 * @author Martijn van de Rijdt
 */
// When testing the application in Eclipse, don't run this class directly. Use TinustrisTestContext instead.
@Slf4j
public class Tinustris extends Application {
    /** Size of the margin between the display for each player in a multiplayer game. */
    private static final int MARGIN = 20;
    
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
        log.info("Starting Tinustris.");
        
        // JInput uses java.util.logging; redirect to slf4j.
        Logging.installSlf4jBridge();
        
        // Launch the application!
        launch(args);
    }
    
    /** {@inheritDoc} */
    @Override
    public void start(Stage stage) {
        log.info("Starting application.");
        Logging.logVersionInfo();
        Logging.setUncaughtExceptionHandler();
        
        BlockCreator blockCreator = CONFIGURATION.getGraphicsStyle().makeBlockCreator();
        
        int numPlayers = CONFIGURATION.getPlayerConfigurations().size();
        
        int widthInBlocks = OnePlayerGameState.DEFAULT_WIDTH;
        int heightInBlocks = OnePlayerGameState.DEFAULT_HEIGHT - OnePlayerGameState.VANISH_ZONE_HEIGHT;
        
        List<OnePlayerGameRenderer> onePlayerRenderers = IntStream.range(0, numPlayers)
            .mapToObj(i -> new OnePlayerGameRenderer(widthInBlocks, heightInBlocks, blockCreator))
            .collect(Collectors.toList());
        
        FlowPane parent = new FlowPane(MARGIN, MARGIN);
        parent.getChildren().addAll(onePlayerRenderers);
        parent.setBackground(Background.EMPTY);

        Scene scene = new Scene(parent, Color.GRAY);

        if (CONFIGURATION.getGraphicsStyle() == GraphicsStyle.THREE_DIMENSIONAL) {
            scene.setCamera(new PerspectiveCamera());
            onePlayerRenderers.get(0).getChildren().add(createLight(150, 500, -250));
        }

        stage.setTitle("Tinustris");
        stage.setScene(scene);
        stage.show();
        log.info("Stage shown.");
        
        initAndStartGameLoop(onePlayerRenderers);
    }

    /**
     * Initialises and starts the main game loop.
     * 
     * @param onePlayerRenderers
     *            renderer for each player
     */
    private void initAndStartGameLoop(List<OnePlayerGameRenderer> onePlayerRenderers) {
        
        int numPlayers = onePlayerRenderers.size();
        
        List<InputController> inputControllers = CONFIGURATION.getPlayerConfigurations()
                .stream()
                .map(PlayerConfiguration::getJInputControllerConfiguration)
                .map(JInputController::new)
                .collect(Collectors.toList());
        
        RandomGenerator<Tetromino> tetrominoGenerator = new RandomTetrominoGenerator();
        RandomGenerator<Integer> gapGenerator = new GapGenerator(OnePlayerGameState.DEFAULT_WIDTH);
        GameEngine<OnePlayerGameState> onePlayerEngine = new OnePlayerEngine(tetrominoGenerator,
                CONFIGURATION.getBehavior(), CONFIGURATION.getStartLevel(), gapGenerator);
        
        if (numPlayers == 1) {
            // single player
            gameLoop = new GameLoop<>(inputControllers, onePlayerEngine, onePlayerRenderers.get(0));
        } else {
            // local multiplayer
            GameEngine<MultiplayerGameState> gameEngine = new MultiplayerEngine(numPlayers, onePlayerEngine);
            List<GameRenderer<MultiplayerGameState>> multiplayerRenderers = IntStream.range(0, numPlayers)
                    .mapToObj(i -> new MultiplayerGameRenderer(onePlayerRenderers.get(i), i))
                    .collect(Collectors.toList());
            CompositeRenderer<MultiplayerGameState> gameRenderer = new CompositeRenderer<>(multiplayerRenderers);
            gameLoop = new GameLoop<>(inputControllers, gameEngine, gameRenderer);
        }

        log.info("Ready to start game loop: " + gameLoop);
        gameLoop.start();
        log.info("Game loop started in separate thread.");
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
