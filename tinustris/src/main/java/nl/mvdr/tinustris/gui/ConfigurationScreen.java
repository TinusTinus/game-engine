package nl.mvdr.tinustris.gui;

import java.io.IOException;

import org.slf4j.bridge.SLF4JBridgeHandler;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

/**
 * Main class which opens the configuration screen.
 * 
 * @author Martijn van de Rijdt
 */
//When testing the application in Eclipse, don't run this class directly. Use ConfigurationScreenTestContext instead.
@Slf4j
public class ConfigurationScreen extends Application {
    /** {@inheritDoc} */
    @Override
    public void start(Stage primaryStage) throws IOException {
        logVersionInfo();
        
        Thread.currentThread().setUncaughtExceptionHandler(
                (thread, throwable) -> log.error("Uncaught runtime exception", throwable));
        
        Parent parent = FXMLLoader.load(getClass().getResource("/Configuration.fxml"));
        
        primaryStage.setScene(new Scene(parent));
        primaryStage.show();
    }
    
    /** Logs some version info. */
    // default visibility for unit tests
    void logVersionInfo() {
        if (log.isInfoEnabled()) {
            String version = retrieveVersion();
            if (version != null) {
                log.info("Application version: " + version);
            } else {
                log.info("Application version unknown.");
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
     * Main method.
     * 
     * @param args commandline arguments; these are passed on to JavaFX
     */
    public static void main(String[] args) {
        // JInput uses java.util.logging; redirect to slf4j.
        installSlf4jBridge();

        launch(args);
    }
    
    /** Installs a bridge for java.util.logging to slf4j. */
    private static void installSlf4jBridge() {
        // remove existing handlers attached to java.util.logging root logger
        SLF4JBridgeHandler.removeHandlersForRootLogger();

        // add SLF4JBridgeHandler to java.util.logging's root logger
        SLF4JBridgeHandler.install();
    }
}
