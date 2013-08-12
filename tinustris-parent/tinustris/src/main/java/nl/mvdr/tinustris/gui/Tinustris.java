package nl.mvdr.tinustris.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

import com.sun.javafx.runtime.VersionInfo;

/**
 * Main class and entry point for the entire application.
 * 
 * @author Martijn van de Rijdt
 */
@Slf4j
public class Tinustris extends Application {
    /**
     * Main method.
     * 
     * @param args
     *            command-line parameters
     */
    public static void main(String[] args) {
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
        root.getChildren().addAll(new Label("derp"));

        stage.setScene(new Scene(root));

        stage.show();

        // Default size should also be the minimum size.
        stage.setMinWidth(stage.getWidth());
        stage.setMinHeight(stage.getHeight());

        log.info("Stage shown.");
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
