package nl.mvdr.tinustris.controller;

import javafx.fxml.FXML;
import lombok.extern.slf4j.Slf4j;

/**
 * Controller for ithe input configuration screen.
 * 
 * @author Martijn van de Rijdt
 */
@Slf4j
public class InputConfigurationController {
    /** Handler for the cancel button. */
    @FXML
    private void cancel() {
        log.info("Cancel button activated.");
        
        // TODO actually cancel
    }
}
