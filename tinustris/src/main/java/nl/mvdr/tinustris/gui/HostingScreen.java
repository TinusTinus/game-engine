package nl.mvdr.tinustris.gui;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

/**
 * Configuration screen for selecting graphics style, player controls etc..
 * 
 * @author Martijn van de Rijdt
 */
@Slf4j
public class HostingScreen extends Application {
    /** {@inheritDoc} */
    @Override
    public void start(Stage primaryStage) throws IOException {
        log.info("Starting application.");
        
        Parent parent = FXMLLoader.load(getClass().getResource("/Hosting.fxml"));
        
        primaryStage.setTitle("Tinustris - hosting");
        primaryStage.setScene(new Scene(parent));
        primaryStage.show();
    }
}
