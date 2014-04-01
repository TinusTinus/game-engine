package nl.mvdr.tinustris.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import lombok.extern.slf4j.Slf4j;
import nl.mvdr.tinustris.input.Input;

/**
 * Controller for ithe input configuration screen.
 * 
 * @author Martijn van de Rijdt
 */
@Slf4j
public class InputConfigurationController {
    /** Button prompt label. */
    @FXML
    private Label buttonPromptLabel;
    /** Description label. */
    @FXML
    private Label descriptionLabel;
    
    /** Initialisation method. */
    // default visibility for unit test
    @FXML
    void initialize() {
        log.info("Initialising controller for input configuration.");
        if (log.isDebugEnabled()) {
            log.debug(this.toString());
        }

        // TODO more initialisation
        
        Input input = Input.LEFT;
        updateLabels(input);
        
        log.info("Initialisation complete.");
        if (log.isDebugEnabled()) {
            log.debug(this.toString());
        }
    }

    /**
     * Updates the label text for the given input.
     * 
     * @param input input
     */
    private void updateLabels(Input input) {
        buttonPromptLabel.setText(String.format("Please press the button for %s.", input));
        descriptionLabel.setText(input.getDescription());
    }
    
    /** Handler for the cancel button. */
    @FXML
    private void cancel() {
        log.info("Cancel button activated.");
        
        // TODO actually cancel
    }
}
