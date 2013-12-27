package nl.mvdr.tinustris.gui;

import javafx.application.Application;
import javafx.scene.Group;
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
        GridGroup group = new GridGroup();
        group.setTranslateX(10);
        group.setTranslateY(10);
        
        // Add a bounding red rectangle to the grid.
        // TODO clean up this code
        Rectangle rectangle = new Rectangle(10, 10, 10 * 30, 20 * 30);
        rectangle.setFill(null);
        
        Paint stroke = new RadialGradient(0,
                1,
                rectangle.getX() + rectangle.getWidth() / 2,
                rectangle.getY() + rectangle.getHeight() / 2,
                rectangle.getHeight() / 2,
                false,
                CycleMethod.NO_CYCLE,
                new Stop(0, Color.WHITE),
                new Stop(1, Color.RED));
        
        rectangle.setStroke(stroke);
        rectangle.setStrokeWidth(10);
        rectangle.setStrokeType(StrokeType.OUTSIDE);
        rectangle.setArcWidth(10);
        rectangle.setArcHeight(10);
        
        Group parentGroup = new Group();
        parentGroup.getChildren().add(rectangle);
        parentGroup.getChildren().add(group);
        
        stage.setScene(new Scene(parentGroup, 10 * 30 + 20, 20 * 30 + 20, Color.GRAY));
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
        gameLoop = new GameLoop<>(inputController, gameEngine, gameRenderer, group); 
        gameLoop.start();
        log.info("Game loop started.");
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
