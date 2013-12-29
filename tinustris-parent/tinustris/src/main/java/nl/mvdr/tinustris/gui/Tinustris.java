package nl.mvdr.tinustris.gui;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.Paint;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import nl.mvdr.tinustris.engine.GameEngine;
import nl.mvdr.tinustris.engine.GameLoop;
import nl.mvdr.tinustris.engine.TinusTrisEngine;
import nl.mvdr.tinustris.input.InputController;
import nl.mvdr.tinustris.input.JInputController;
import nl.mvdr.tinustris.model.GameState;

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
    /** Width of the border around the Tetris grid and other UI components. */
    private static final int BORDER_WIDTH = 10;
    
    /** Game loop. */
    private GameLoop<GridGroup> gameLoop;
    
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

        // construct the user interface
        stage.setTitle("Tinustris");
        GridGroup gridGroup = new GridGroup();
        
        Group parent = new Group();
        // TODO add background image: parent.getChildren().add(new ImageView("imageurl"));
        parent.getChildren().add(createWindow(gridGroup));
                
        Scene scene = new Scene(parent, 
                10 * GridGroup.BLOCK_SIZE + 2 * BORDER_WIDTH,
                20 * GridGroup.BLOCK_SIZE + 2 * BORDER_WIDTH,
                Color.WHITE);
        stage.setScene(scene);
        stage.show();
        // Default size should also be the minimum size.
        stage.setMinWidth(stage.getWidth());
        stage.setMinHeight(stage.getHeight());
        log.info("Stage shown.");
        
        // setup necessary components
        InputController inputController = new JInputController();
        GameEngine gameEngine = new TinusTrisEngine();
        GameRenderer<GridGroup> gameRenderer = createRenderer();
        
        // start the game loop
        gameLoop = new GameLoop<>(inputController, gameEngine, gameRenderer, gridGroup); 
        gameLoop.start();
        log.info("Game loop started.");
    }


    /** Logs some version info. */
    private void logVersionInfo() {
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
            log.info("JavaFX version: " + VersionInfo.getVersion());
            log.info("JavaFX runtime version: " + VersionInfo.getRuntimeVersion());
            log.info("JavaFX build timestamp: " + VersionInfo.getBuildTimestamp());
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
     * @param contents
     *            contents of the window
     * @return group containing the window and the contents
     */
    private Group createWindow(Node contents) {
        // bounding red rectangle
        Rectangle border = new Rectangle(BORDER_WIDTH, BORDER_WIDTH, 10 * GridGroup.BLOCK_SIZE,
                20 * GridGroup.BLOCK_SIZE);
        border.setFill(null);
        
        Paint stroke = new RadialGradient(0,
                1,
                border.getX() + border.getWidth() / 2,
                border.getY() + border.getHeight() / 2,
                border.getHeight() / 2,
                false,
                CycleMethod.NO_CYCLE,
                new Stop(0, Color.WHITE),
                new Stop(1, Color.DARKRED));
        
        border.setStroke(stroke);
        border.setStrokeWidth(BORDER_WIDTH);
        border.setStrokeType(StrokeType.OUTSIDE);
        border.setArcWidth(GridGroup.ARC_SIZE);
        border.setArcHeight(GridGroup.ARC_SIZE);
        
        // black, seethrough background, to dim whatever is behind the window
        Rectangle background = new Rectangle(border.getX(), border.getY(), border.getWidth(), border.getHeight());
        background.setFill(Color.BLACK);
        background.setOpacity(.5);
        background.setArcWidth(GridGroup.ARC_SIZE);
        background.setArcHeight(GridGroup.ARC_SIZE);
        
        contents.setTranslateX(BORDER_WIDTH);
        contents.setTranslateY(BORDER_WIDTH);
        
        Group group = new Group();
        group.getChildren().addAll(background, border, contents);
        return group;
    }
    
    /**
     * Creates the game renderer.
     * 
     * @return renderer
     */
    // If this was Java 8, this could be a lambda expression!
    private GameRenderer<GridGroup> createRenderer() {
        return new GameRenderer<GridGroup>() {
            /** {@inheritDoc} */
            @Override
            public void render(GridGroup node, GameState gameState) {
                node.render(gameState);
            }
        };
    }
    
    /** {@inheritDoc} */
    @Override
    public void stop() throws Exception {
        log.info("Stopping the application.");
        gameLoop.stop();
        super.stop();
        log.info("Stopped.");
    }
}
