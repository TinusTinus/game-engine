package nl.mvdr.tinustris.gui;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;
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
import nl.mvdr.tinustris.configuration.NetcodeConfiguration;
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
import nl.mvdr.tinustris.model.GameState;
import nl.mvdr.tinustris.model.GameStateHolder;
import nl.mvdr.tinustris.model.MultiplayerGameState;
import nl.mvdr.tinustris.model.OnePlayerGameState;
import nl.mvdr.tinustris.model.SingleGameStateHolder;
import nl.mvdr.tinustris.model.Tetromino;
import nl.mvdr.tinustris.netcode.NetcodeEngine;
import nl.mvdr.tinustris.netcode.ObjectOutputStreamsInputPublisher;

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

    /** Indicates whether netcode components should simulate lag. */
    private final boolean lagSimulation;
    
    /** Game loop. */
    private GameLoop<?> gameLoop;
    
    /** Constructor. */
    public Tinustris() {
        this(false);
    }

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
                // ...with spectators!
                NetcodeEngine<OnePlayerGameState> netcodeEngine = createNetcodeEngineAndStartRemoteInputListeners(
                        configuration.getNetcodeConfiguration(), inputControllers, onePlayerEngine);
                holder = netcodeEngine;
                localInputListeners = Arrays.asList(netcodeEngine, createOutputPublisher(configuration));
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
                NetcodeEngine<MultiplayerGameState> netcodeEngine = createNetcodeEngineAndStartRemoteInputListeners(
                        configuration.getNetcodeConfiguration(), inputControllers, gameEngine);
                holder = netcodeEngine;
                localInputListeners = Arrays.asList(netcodeEngine, createOutputPublisher(configuration));
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
     * Creates an output publisher based on the given configuration.
     * 
     * @param configuration configuration
     * @return output publisher
     */
    private ObjectOutputStreamsInputPublisher createOutputPublisher(Configuration configuration) {
        List<ObjectOutputStream> outputStreams = configuration.getNetcodeConfiguration().getRemotes().stream()
                .map(remoteConfiguration -> remoteConfiguration.getOutputStream())
                .filter(Optional<ObjectOutputStream>::isPresent)
                .map(Optional<ObjectOutputStream>::get)
                .collect(Collectors.toList());
        return new ObjectOutputStreamsInputPublisher(outputStreams);
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
     * Returns an input state holder for the given input controller.
     * 
     * @param controller controller to be converted
     * @return holder
     */
    private InputStateHolder convertToInputStateHolder(InputController controller) {
        InputStateHolder result;
        if (controller instanceof InputStateHolder) {
            result = (InputStateHolder) controller;
        } else if (controller.isLocal()) {
            result = new InputStateHolder(true);
        } else {
            throw new IllegalArgumentException("Unexpected type of remote input controller: " + controller);
        }
        return result;
    }

    /**
     * Creates a netcode engine, and starts a thread for each remote game instance to listen for remote inputs.
     * 
     * @param NetcodeConfiguration netcode configuration
     * @param inputControllers all input controllers for this game; all remote input controllers are expected to be InputStateHolders as well
     * @param gameEngine game engine
     * @return new netcode engine
     */
    private <S extends GameState> NetcodeEngine<S> createNetcodeEngineAndStartRemoteInputListeners(
            NetcodeConfiguration configuration, List<InputController> inputControllers, GameEngine<S> gameEngine) {
        NetcodeEngine<S> netcodeEngine = createNetcodeEngine(inputControllers, gameEngine);
        startRemoteInputListeners(configuration, netcodeEngine);
        return netcodeEngine;
    }

    /**
     * Creates a netcode engine.
     * 
     * @param inputControllers all input controllers for this game; all remote input controllers are expected to be InputStateHolders as well
     * @param gameEngine game engine
     * @return new netcode engine
     */
    private <S extends GameState> NetcodeEngine<S> createNetcodeEngine(List<InputController> inputControllers,
            GameEngine<S> gameEngine) {
        List<InputStateHolder> inputStateHolders = inputControllers.stream()
                .map(this::convertToInputStateHolder)
                .collect(Collectors.toList());
        return new NetcodeEngine<>(inputStateHolders, gameEngine);
    }

    /**
     * Starts a thread for each remote input provider. Whenever a new remote input is received, it is passed into the
     * netcode engine.
     * 
     * @param configuration
     */
    private <S extends GameState> void startRemoteInputListeners(NetcodeConfiguration configuration, NetcodeEngine<S> netcodeEngine) {
        configuration.getRemotes().stream()
            .map(remoteConfiguration -> remoteConfiguration.getInputStream())
            .filter(Optional<ObjectInputStream>::isPresent)
            .map(Optional<ObjectInputStream>::get)
            .map(in -> (Runnable) (() -> {
                try {
                    while (true) {
                        if (lagSimulation) {
                            simulateLag();
                        }
                        
                        FrameAndInputStatesContainer container = (FrameAndInputStatesContainer) in.readObject();
                        netcodeEngine.accept(container);
                    }
                } catch (IOException | ClassNotFoundException e) {
                    // TODO actual error handling
                    log.error("Unexpected exception!", e);
                }
            }))
            .map(runnable -> new Thread(runnable, "Input reader " + runnable.hashCode()))
            .forEach(Thread::start);
    }

    /** Simulates lag. Can be used for local netcode tests. */
    private void simulateLag() {
        Random random = new Random();
        // 10% of the time...
        if (random.nextInt(100) < 10) {
            // ... instert a delay between 1 and 100 milliseconds
            int delay = 1 + random.nextInt(99);
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                log.warn("Unexpected interrupt while trying to simulate lag.", e);
            }
        }
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
