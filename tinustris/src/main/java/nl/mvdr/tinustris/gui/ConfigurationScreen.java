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
 * Configuration screen for selecting graphics style, player controls etc..
 * 
 * @author Martijn van de Rijdt
 */
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
}
