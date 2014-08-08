package nl.mvdr.tinustris.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

/**
 * Controller for the joining screen.
 * 
 * @author Martijn van de Rijdt
 */
@Slf4j
@ToString
public class JoiningController {
    /** Text field containing the hostname / IP address of the host to connect to. */
    @FXML
    private TextField hostTextField;
    
    /** Initialisation method. */
    @FXML
    private void initialize() {
        log.info("Controller initialised: {}", this);
    }
    
    /** Handler for the cancel button. */
    @FXML
    private void cancel() {
        log.info("Cancelling.");
        // TODO implement
    }
    
    /** Handler for the join button. */
    @FXML
    private void join() {
        log.info("Joining game.");
        // TODO implement
    }
}
