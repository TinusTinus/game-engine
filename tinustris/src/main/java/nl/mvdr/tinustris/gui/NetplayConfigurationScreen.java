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
 * Main class which opens the netplay configuration screen.
 * 
 * @author Martijn van de Rijdt
 */
//When testing the application in Eclipse, don't run this class directly. Use NetplayConfigurationScreenTestContext instead.
@Slf4j
public class NetplayConfigurationScreen extends Application {
    /** {@inheritDoc} */
    @Override
    public void start(Stage primaryStage) throws IOException {
        log.info("Starting netplay configuration application.");
        
        Logging.setUncaughtExceptionHandler();
        
        Parent parent = FXMLLoader.load(getClass().getResource("/NetplayConfiguration.fxml"));
        
        primaryStage.setTitle("Tinustris - netplay configuration");
        primaryStage.setScene(new Scene(parent));
        primaryStage.show();
    }
    
    /**
     * Main method.
     * 
     * @param args commandline arguments; these are passed on to JavaFX
     */
    public static void main(String[] args) {
        log.info("Starting Tinustris netplay configuration screen.");

        Logging.logVersionInfo();
        
        Logging.setUpHazelcastLogging();
        
        // JInput uses java.util.logging; redirect to slf4j.
        Logging.installSlf4jBridge();

        launch(args);
    }
}
