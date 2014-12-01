package nl.mvdr.tinustris.gui;

import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javafx.animation.Animation;
import javafx.animation.TranslateTransition;
import javafx.scene.PerspectiveCamera;
import javafx.scene.PointLight;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.mvdr.tinustris.configuration.Configuration;
import nl.mvdr.tinustris.configuration.LocalPlayerConfiguration;
import nl.mvdr.tinustris.configuration.PlayerConfiguration;
import nl.mvdr.tinustris.engine.GameEngine;
import nl.mvdr.tinustris.engine.GameLoop;
import nl.mvdr.tinustris.engine.GapGenerator;
import nl.mvdr.tinustris.engine.Generator;
import nl.mvdr.tinustris.engine.MultiplayerEngine;
import nl.mvdr.tinustris.engine.OnePlayerEngine;
import nl.mvdr.tinustris.engine.RandomTetrominoGenerator;
import nl.mvdr.tinustris.input.InputController;
import nl.mvdr.tinustris.input.InputStateHolder;
import nl.mvdr.tinustris.input.JInputController;
import nl.mvdr.tinustris.input.JInputControllerConfiguration;
import nl.mvdr.tinustris.model.FrameAndInputStatesContainer;
import nl.mvdr.tinustris.model.GameStateHolder;
import nl.mvdr.tinustris.model.MultiplayerGameState;
import nl.mvdr.tinustris.model.OnePlayerGameState;
import nl.mvdr.tinustris.model.SingleGameStateHolder;
import nl.mvdr.tinustris.model.Tetromino;

/**
 * Class which can start a game of Tinustris.
 * 
 * @author Martijn van de Rijdt
 */
@Slf4j
@RequiredArgsConstructor
public class Tinustris {
    /** Size of the margin between the display for each player in a multiplayer game. */
    private static final int MARGIN = 20;

    /** Game loop. */
    private GameLoop<?> gameLoop;

    /**
     * Starts the game.
     * 
     * @param stage stage in which the game should be shown
     * @param configuration game configuration
     */
    public void start(Stage stage, Configuration configuration) {
        log.info("Starting game with configuration: " + configuration);
        
        BlockCreator blockCreator = configuration.getGraphicsStyle().makeBlockCreator();
        
        int widthInBlocks = OnePlayerGameState.DEFAULT_WIDTH;
        int heightInBlocks = OnePlayerGameState.DEFAULT_HEIGHT - OnePlayerGameState.VANISH_ZONE_HEIGHT;
        
        List<OnePlayerGameRenderer> onePlayerRenderers = configuration.getPlayerConfigurations().stream()
            .map(PlayerConfiguration::getName)
            .map(name -> new OnePlayerGameRenderer(name, widthInBlocks, heightInBlocks, blockCreator))
            .collect(Collectors.toList());
        
        FlowPane parent = new FlowPane(MARGIN, MARGIN);
        parent.getChildren().addAll(onePlayerRenderers);
        parent.setBackground(Background.EMPTY);

        Scene scene = new Scene(parent, Color.GRAY);

        if (configuration.getGraphicsStyle() == GraphicsStyle.THREE_DIMENSIONAL) {
            scene.setCamera(new PerspectiveCamera());
            onePlayerRenderers.get(0).getChildren().add(createLight(150, 500, -250));
        }

        stage.setTitle("Tinustris");
        stage.setScene(scene);
        stage.show();
        log.info("Stage shown.");
        
        initAndStartGameLoop(onePlayerRenderers, configuration);
    }

    /**
     * Initialises and starts the main game loop.
     * 
     * @param onePlayerRenderers
     *            renderer for each player
     */
    private void initAndStartGameLoop(List<OnePlayerGameRenderer> onePlayerRenderers, Configuration configuration) {
        
        int numPlayers = onePlayerRenderers.size();
        
        List<InputController> inputControllers = configuration.getPlayerConfigurations()
                .stream()
                .map(this::createInputController)
                .collect(Collectors.toList());
        
        Generator<Tetromino> tetrominoGenerator = new RandomTetrominoGenerator(configuration.getTetrominoRandomSeed());
        Generator<Integer> gapGenerator = new GapGenerator(configuration.getGapRandomSeed(), OnePlayerGameState.DEFAULT_WIDTH);
        GameEngine<OnePlayerGameState> onePlayerEngine = new OnePlayerEngine(tetrominoGenerator,
                configuration.getBehavior(), configuration.getStartLevel(), gapGenerator);
        
        if (numPlayers == 1) {
            // single player game
            List<Consumer<FrameAndInputStatesContainer>> localInputListeners;
            GameStateHolder<OnePlayerGameState> holder;
            if (configuration.getNetcodeConfiguration().isNetworkedGame()) {
                // ... with spectators
                // TODO initiate networked game!
                holder = new SingleGameStateHolder<>();
                localInputListeners = Collections.<Consumer<FrameAndInputStatesContainer>> emptyList();
            } else {
                holder = new SingleGameStateHolder<>();
                localInputListeners = Collections.<Consumer<FrameAndInputStatesContainer>> emptyList();
            }
            
            gameLoop = new GameLoop<>(inputControllers, onePlayerEngine, onePlayerRenderers.get(0),
                    holder, localInputListeners);
        } else {
            // multiplayer game
            List<Consumer<FrameAndInputStatesContainer>> localInputListeners;
            GameStateHolder<MultiplayerGameState> holder;
            GameEngine<MultiplayerGameState> gameEngine = new MultiplayerEngine(numPlayers, onePlayerEngine);
            List<GameRenderer<MultiplayerGameState>> multiplayerRenderers = IntStream.range(0, numPlayers)
                    .mapToObj(i -> new MultiplayerGameRenderer(onePlayerRenderers.get(i), i))
                    .collect(Collectors.toList());
            GameRenderer<MultiplayerGameState> gameRenderer = new CompositeRenderer<>(multiplayerRenderers);
            if (configuration.getNetcodeConfiguration().isNetworkedGame()) {
                // TODO initiate networked game
                holder = new SingleGameStateHolder<>();
                localInputListeners = Collections.<Consumer<FrameAndInputStatesContainer>> emptyList();
            } else {
                // local multiplayer, no spectators
                holder = new SingleGameStateHolder<>();
                localInputListeners = Collections.<Consumer<FrameAndInputStatesContainer>> emptyList();
            }
            gameLoop = new GameLoop<>(inputControllers, gameEngine, gameRenderer, holder,
                    localInputListeners);
        }

        log.info("Ready to start game loop: " + gameLoop);
        gameLoop.start();
        log.info("Game loop started in separate thread.");
    }

    /**
     * Creates an input controller based on the given player configuration.
     * 
     * @param playerConfiguration player config
     * @return input controller
     */
    private InputController createInputController(PlayerConfiguration playerConfiguration) {
        InputController result;
        if (playerConfiguration instanceof LocalPlayerConfiguration) {
            JInputControllerConfiguration inputControllerConfiguration = ((LocalPlayerConfiguration) playerConfiguration)
                    .getJInputControllerConfiguration();
            result = new JInputController(inputControllerConfiguration);
        } else {
            // remote player
            result = new InputStateHolder(false);
        }
        return result;
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

    /** Stops the game loop. */
    public void stopGameLoop() {
        if (gameLoop != null) {
            log.info("Stopping the game loop.");
            gameLoop.stop();
        } else {
            log.info("No game loop to stop.");
        }
    }
}
