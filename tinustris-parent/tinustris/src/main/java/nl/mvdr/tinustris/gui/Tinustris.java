package nl.mvdr.tinustris.gui;

import org.slf4j.bridge.SLF4JBridgeHandler;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import lombok.extern.slf4j.Slf4j;
import nl.mvdr.tinustris.input.InputController;
import nl.mvdr.tinustris.input.InputState;
import nl.mvdr.tinustris.input.JInputController;

import com.sun.javafx.runtime.VersionInfo;

/**
 * Main class and entry point for the entire application.
 * 
 * @author Martijn van de Rijdt
 */
// When testing the application, don't run this class directly from Eclipse. Use TinusTrisTestContext instead.
@Slf4j
public class Tinustris extends Application {
    /**
     * Main method.
     * 
     * @param args
     *            command-line parameters
     */
    public static void main(String[] args) {
        // JInput uses java.util.logging; redirect to slf4j.

        // remove existing handlers attached to j.u.l root logger
        SLF4JBridgeHandler.removeHandlersForRootLogger();

        // add SLF4JBridgeHandler to j.u.l's root logger, should be done once during
        // the initialization phase of your application
        SLF4JBridgeHandler.install();
        
        // Launch the application!
        launch(args);
    }
    
    /** {@inheritDoc} */
    @Override
    public void start(Stage stage) {
        logVersionInfo();
        
        log.info("Starting application.");
        
        // TODO actually start the application in stead of this dummy
        
        stage.setTitle("Tinustris!");


        AnchorPane root = new AnchorPane();
        final Label label = new Label("This will show the input state");
        root.getChildren().add(label);

        stage.setScene(new Scene(root));

        stage.show();

        // Default size should also be the minimum size.
        stage.setMinWidth(stage.getWidth());
        stage.setMinHeight(stage.getHeight());

        log.info("Stage shown.");
        
        // Start a timeline which periodically checks for inputs and shows them in the user interface.
        final InputController inputController = new JInputController();
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(16), new EventHandler<ActionEvent>() {
            /** {@inheritDoc} */
            @Override
            public void handle(ActionEvent event) {
                InputState inputState = inputController.getInputState();
                label.setText(inputState.toString());
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
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
}
