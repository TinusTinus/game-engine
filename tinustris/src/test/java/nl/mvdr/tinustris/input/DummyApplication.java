package nl.mvdr.tinustris.input;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * Dummy application.
 * 
 * @author Martijn van de Rijdt
 */
public class DummyApplication extends Application {
    /** {@inheritDoc} */
    @Override
    public void start(Stage primaryStage) {
        Scene scene = new Scene(new Label("This is a dummy application."));
        primaryStage.setScene(scene);
        primaryStage.setTitle("Dummy Application");
        primaryStage.show();
    }
}
