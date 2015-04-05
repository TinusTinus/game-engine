package nl.mvdr.tinustris.gui;

import java.io.IOException;

import nl.mvdr.tinustris.logging.Logging;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Configuration screen for selecting graphics style, player controls etc..
 * 
 * @author Martijn van de Rijdt
 */
@Slf4j
@RequiredArgsConstructor
public class ConfigurationScreen extends Application {
    /** {@inheritDoc} */
    @Override
    public void start(Stage primaryStage) throws IOException {
        log.info("Starting application.");
        
        Logging.setUncaughtExceptionHandler();
        
        FXMLLoader fxmlLoader = new FXMLLoader(ConfigurationScreen.class.getResource("/Configuration.fxml"));

        Parent parent = fxmlLoader.load();
        
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

        Application.launch(args);
    }
}
