package nl.mvdr.tinustris.controller;

import javafx.fxml.FXML;
import lombok.extern.slf4j.Slf4j;

/**
 * Controller for the player configuration user interface component.
 * 
 * @author Martijn van de Rijdt
 */
@Slf4j
public class PlayerConfigurationController {
    /** Performs controller initialisation. */
    @FXML
    private void initialize() {
        log.info("Initialising.");
        if (log.isDebugEnabled()) {
            log.debug(this.toString());
        }
        
        // TODO
        
        log.info("Initialisation complete.");
        if (log.isDebugEnabled()) {
            log.debug(this.toString());
        }
    }

}
