package nl.mvdr.tinustris.controller;

import java.util.stream.Stream;

import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import nl.mvdr.tinustris.gui.GraphicsStyle;

/**
 * Controller for a configuration screen.
 * 
 * @author Martijn van de Rijdt
 */
@Slf4j
@ToString
@NoArgsConstructor
// All args constructor for use in unit tests.
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class ConfigurationScreenController {
    /** Radio button for 2D graphics. */
    @FXML
    private RadioButton graphics2DRadioButton;
    /** Radio button for 3D graphics. */
    @FXML
    private RadioButton graphics3DRadioButton;
    
    /** Initialisation method. */
    @FXML
    // default visibility for unit test
    void initialize() {
        log.info("Initialising.");
        
        Stream.of(graphics2DRadioButton, graphics3DRadioButton)
            .forEach(radioButton ->
                radioButton.setOnAction(event -> log.info("Activated " + radioButton.getText())));
        Stream.of(graphics2DRadioButton, graphics3DRadioButton)
            .forEach(radioButton -> radioButton.setDisable(!toGraphicsStyleValue(radioButton).isAvailable()));
        
        // TODO further initialisation
        
        log.info("Initialisation complete.");
    }

    /**
     * Returns the graphics style value corresponding to the given ui component.
     * 
     * @param toggle ui component; must be one of the radio buttons corresponding to a graphical style
     * @return graphics style
     */
    private GraphicsStyle toGraphicsStyleValue(Toggle toggle) {
        GraphicsStyle result;
        if (toggle == graphics2DRadioButton) {
            result = GraphicsStyle.TWO_DIMENSIONAL;
        } else if (toggle == graphics3DRadioButton) {
            result = GraphicsStyle.THREE_DIMENSIONAL;
        } else {
            throw new IllegalArgumentException("Unexpected parameter: " + toggle);
        }
        return result;
    }
}
