package nl.mvdr.tinustris.controller;

import javafx.fxml.FXML;
import lombok.extern.slf4j.Slf4j;

/**
 * Controller for the hosting game screen, where the user is waiting for a remote player to connect.
 * 
 * @author Martijn van de Rijdt
 */
@Slf4j
public class HostingController {
    /** Performs the initialisation. */
    @FXML
    private void initialize() {
        log.info("Initialising.");
        
        // TODO start up a server socket waiting for a remote player to connect
    }
    
    /** Handles the user activating the cancel button. */
    @FXML
    private void cancel() {
        log.info("Cancel button activated.");
        // TODO cancel the server socket; exit application or return to the first screen
    }
}
