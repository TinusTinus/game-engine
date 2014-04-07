package nl.mvdr.tinustris.gui;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import nl.mvdr.tinustris.logging.Logging;

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
        log.info("Starting application.");
        
        Logging.setUncaughtExceptionHandler();
        
        Parent parent = FXMLLoader.load(getClass().getResource("/Configuration.fxml"));
        
        primaryStage.setTitle("Tinustris - configuration");
        primaryStage.setScene(new Scene(parent));
        primaryStage.show();
    }
    
    /**
     * Main method.
     * 
     * @param args commandline arguments; these are passed on to JavaFX
     */
    public static void main(String[] args) {
        log.info("Starting Tinustris configuration screen.");

        Logging.logVersionInfo();
        
        // JInput uses java.util.logging; redirect to slf4j.
        Logging.installSlf4jBridge();

        launch(args);
    }
}
