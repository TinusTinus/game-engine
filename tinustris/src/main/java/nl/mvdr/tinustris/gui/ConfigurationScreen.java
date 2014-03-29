package nl.mvdr.tinustris.gui;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main class which opens the configuration screen.
 * 
 * @author Martijn van de Rijdt
 */
//When testing the application in Eclipse, don't run this class directly. Use ConfigurationScreenTestContext instead.
public class ConfigurationScreen extends Application {

    /** {@inheritDoc} */
    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("/Configuration.fxml"));
        
        primaryStage.setScene(new Scene(parent));
        primaryStage.show();
    }

    /**
     * Main method.
     * 
     * @param args commandline arguments; these are passed on to JavaFX
     */
    public static void main(String[] args) {
        launch(args);
    }
}
