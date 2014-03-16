package nl.mvdr.tinustris.gui;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javafx.animation.Animation;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.PerspectiveCamera;
import javafx.scene.PointLight;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.Paint;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import lombok.extern.slf4j.Slf4j;
import nl.mvdr.tinustris.engine.GameEngine;
import nl.mvdr.tinustris.engine.GameLoop;
import nl.mvdr.tinustris.engine.OnePlayerEngine;
import nl.mvdr.tinustris.input.InputController;
import nl.mvdr.tinustris.input.JInputController;
import nl.mvdr.tinustris.model.OnePlayerGameState;

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
    /** Width of a text window. */
    private static final int TEXT_WINDOW_HEIGHT = 50;
    /** Width of the game over label. */
    private static final int GAME_OVER_LABEL_WIDTH = 170;
    /** Whether 3D graphics are used. */
    private static final boolean PERSPECTIVE_3D = false;
    
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
        
        // TODO configuration screen to select speed curve, level system, button configuration, number of players and 2D/3D
        
        BlockCreator blockCreator;
        if (PERSPECTIVE_3D) {
            blockCreator = new BoxBlockCreator();
        } else {
            blockCreator = new RectangleBlockCreator();
        }
        
        // create the game renderers
        GridRenderer gridGroup = new GridRenderer(blockCreator);
        NextBlockRenderer nextBlockRenderer = new NextBlockRenderer(blockCreator);
        LinesRenderer linesRenderer = new LinesRenderer();
        LevelRenderer levelRenderer = new LevelRenderer();
        GameOverRenderer gameOverRenderer = new GameOverRenderer();
        List<GameRenderer<OnePlayerGameState>> renderers = Arrays.asList(gridGroup, nextBlockRenderer, linesRenderer,
                levelRenderer, gameOverRenderer);
        CompositeRenderer<OnePlayerGameState> gameRenderer = new CompositeRenderer<>(renderers);

        // construct the user interface
        stage.setTitle("Tinustris");
        
        int widthInBlocks = OnePlayerGameState.DEFAULT_WIDTH;
        int heightInBlocks = OnePlayerGameState.DEFAULT_HEIGHT - OnePlayerGameState.VANISH_ZONE_HEIGHT;
        
        Group gridWindow = createWindow("", gridGroup, MARGIN, MARGIN, widthInBlocks * GridRenderer.BLOCK_SIZE, 
                heightInBlocks * BlockGroupRenderer.BLOCK_SIZE);
        Group nextBlockWindow = createWindow("NEXT", nextBlockRenderer,
                2 * MARGIN + widthInBlocks * BlockGroupRenderer.BLOCK_SIZE + 2 * BORDER_SIZE,
                MARGIN,
                4 * GridRenderer.BLOCK_SIZE,
                4 * GridRenderer.BLOCK_SIZE);
        Group linesWindow = createWindow("LINES", linesRenderer,
                2 * MARGIN + widthInBlocks * BlockGroupRenderer.BLOCK_SIZE + 2 * BORDER_SIZE,
                2 * MARGIN + 2 * BORDER_SIZE + 4 * GridRenderer.BLOCK_SIZE,
                4 * GridRenderer.BLOCK_SIZE,
                TEXT_WINDOW_HEIGHT);
        Group levelWindow = createWindow("LEVEL", levelRenderer,
                2 * MARGIN + widthInBlocks * BlockGroupRenderer.BLOCK_SIZE + 2 * BORDER_SIZE,
                3 * MARGIN + 4 * BORDER_SIZE + 4 * GridRenderer.BLOCK_SIZE + TEXT_WINDOW_HEIGHT,
                4 * GridRenderer.BLOCK_SIZE,
                TEXT_WINDOW_HEIGHT);
        Group gameOverWindow = createWindow("", gameOverRenderer,
                (MARGIN + widthInBlocks * BlockGroupRenderer.BLOCK_SIZE) / 2 - GAME_OVER_LABEL_WIDTH / 2,
                (MARGIN + heightInBlocks * BlockGroupRenderer.BLOCK_SIZE) / 2 - TEXT_WINDOW_HEIGHT / 2,
                GAME_OVER_LABEL_WIDTH,
                TEXT_WINDOW_HEIGHT);
        gameOverWindow.setVisible(false);
        // TODO also add a background image as the first child: new ImageView("imageurl");
        Group parent = new Group(gridWindow, nextBlockWindow, linesWindow, levelWindow, gameOverWindow);

        Scene scene = new Scene(parent, 
                widthInBlocks * BlockGroupRenderer.BLOCK_SIZE + 4 * BORDER_SIZE + 3 * MARGIN + 
                    4 * GridRenderer.BLOCK_SIZE,
                heightInBlocks * BlockGroupRenderer.BLOCK_SIZE + 2 * BORDER_SIZE + 2 * MARGIN,
                Color.GRAY);
        
        if (PERSPECTIVE_3D) {
            setupLightsAndCamera(parent, scene);
        }
        
        stage.setScene(scene);
        stage.show();
        // Default size should also be the minimum size.
        stage.setMinWidth(stage.getWidth());
        stage.setMinHeight(stage.getHeight());
        log.info("Stage shown.");
        
        // setup necessary components
        InputController inputController = new JInputController();
        GameEngine<OnePlayerGameState> gameEngine = new OnePlayerEngine();
        
        // start the game loop
        gameLoop = new GameLoop<>(Collections.singletonList(inputController), gameEngine, gameRenderer);
        log.info("Ready to start game loop: " + gameLoop);
        gameLoop.start();
        log.info("Game loop started.");
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
    
    /**
     * Creates a red-bordered window containing the given node.
     * 
     * @param title
     *            window title
     * @param contents
     *            contents of the window
     * @param x
     *            x coordinate
     * @param y
     *            coordinate
     * @param contentsWidth
     *            width of the contents
     * @param contentsHeight
     *            height of the contents
     * @return group containing the window and the contents
     */
    private Group createWindow(String title, Node contents, double x, double y, double contentsWidth,
            double contentsHeight) {
        // bounding red rectangle
        Rectangle border = new Rectangle(x + BORDER_SIZE, y + BORDER_SIZE, contentsWidth, contentsHeight);
        border.setFill(null);
        
        Paint stroke = new RadialGradient(0,
                1,
                border.getX() + border.getWidth() / 2,
                border.getY() + border.getHeight() / 2,
                border.getWidth(),
                false,
                CycleMethod.NO_CYCLE,
                new Stop(0, Color.WHITE),
                new Stop(1, Color.DARKRED));
        
        border.setStroke(stroke);
        border.setStrokeWidth(BORDER_SIZE);
        border.setStrokeType(StrokeType.OUTSIDE);
        border.setArcWidth(GridRenderer.ARC_SIZE);
        border.setArcHeight(GridRenderer.ARC_SIZE);
        
        // black, seethrough background, to dim whatever is behind the window
        Rectangle background = new Rectangle(border.getX(), border.getY(), border.getWidth(), border.getHeight());
        background.setFill(Color.BLACK);
        background.setOpacity(.5);
        background.setArcWidth(GridRenderer.ARC_SIZE);
        background.setArcHeight(GridRenderer.ARC_SIZE);
        
        contents.setTranslateX(x + BORDER_SIZE);
        contents.setTranslateY(y + BORDER_SIZE);
        
        Label label = new Label(title);
        label.setTextFill(Color.WHITE);
        label.setFont(new Font(10));
        label.setLayoutX(border.getX() + 2);
        label.setLayoutY(border.getY() - BORDER_SIZE - 2);
        
        return new Group(background, border, contents, label);
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
