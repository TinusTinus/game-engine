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
 * Main class which opens the input configuration screen.
 * 
 * @author Martijn van de Rijdt
 */
@Slf4j
public class InputConfigurationTestContext extends Application {
    /** {@inheritDoc} */
    @Override
    public void start(Stage primaryStage) throws IOException {
        log.info("Starting application.");
        
        Logging.logVersionInfo();
        Logging.setUncaughtExceptionHandler();
        
        Parent parent = FXMLLoader.load(getClass().getResource("/InputConfiguration.fxml"));
        
        primaryStage.setScene(new Scene(parent));
        primaryStage.show();
    }
    
    /**
     * Main method.
     * 
     * @param args commandline arguments; these are passed on to JavaFX
     */
    public static void main(String[] args) {
        log.info("Starting Input configuration screen.");
        
        // JInput uses java.util.logging; redirect to slf4j.
        Logging.installSlf4jBridge();

        launch(args);
    }
}
