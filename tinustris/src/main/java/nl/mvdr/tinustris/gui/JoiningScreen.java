package nl.mvdr.tinustris.gui;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

/**
 * Configuration screen for joining a remote game.
 * 
 * @author Martijn van de Rijdt
 */
@Slf4j
public class JoiningScreen extends Application {
    /** {@inheritDoc} */
    @Override
    public void start(Stage primaryStage) throws IOException {
        log.info("Starting application.");
        
        Parent parent = FXMLLoader.load(getClass().getResource("/Joining.fxml"));
        
        primaryStage.setTitle("Tinustris - joining");
        primaryStage.setScene(new Scene(parent));
        primaryStage.show();
    }
}
