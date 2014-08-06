package nl.mvdr.tinustris.gui;

import java.io.IOException;

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
    /** Controller to be injected into the stage. */
    private final Object controller;
    
    /** {@inheritDoc} */
    @Override
    public void start(Stage primaryStage) throws IOException {
        log.info("Starting application.");
        
        FXMLLoader fxmlLoader = new FXMLLoader(ConfigurationScreen.class.getResource("/Configuration.fxml"));
        fxmlLoader.setController(controller);

        Parent parent = fxmlLoader.load();
        
        primaryStage.setTitle("Tinustris - configuration");
        primaryStage.setScene(new Scene(parent));
        primaryStage.show();
    }
}
