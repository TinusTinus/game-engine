package nl.mvdr.tinustris.controller;

import java.util.stream.Stream;

import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import lombok.extern.slf4j.Slf4j;

/**
 * Controller for a configuration screen.
 * 
 * @author Martijn van de Rijdt
 */
@Slf4j
public class ConfigurationScreenController {
    /** Radio button for 2D graphics. */
    @FXML
    private RadioButton graphics2DRadioButton;
    /** Radio button for 3D graphics. */
    @FXML
    private RadioButton graphics3DRadioButton;
    
    /** Initialisation method. */
    @FXML
    private void initialize() {
        log.info("Initialising.");
        
        Stream.of(graphics2DRadioButton, graphics3DRadioButton)
            .forEach(radioButton ->
                radioButton.setOnAction(event -> log.info("Activated " + radioButton.getText())));
        
        // TODO further initialisation
        
        log.info("Initialisation complete.");
    }
}
