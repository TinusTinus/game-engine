package nl.mvdr.tinustris.controller;

import java.io.IOException;

import nl.mvdr.tinustris.gui.NetplayConfigurationScreen;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
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
    
    /**
     * Handler for the cancel button.
     * 
     * @throws IOException
     *             In case the netplay configuration screen's fxml cannot be loaded. This should not occur. In order to
     *             get to the Hosting screen the Netplay Configuration screen must have already been loaded succesfully
     *             once. Let the thread's exception handler log the error and let the user close the window manually.
     */
    @FXML
    private void cancel() throws IOException {
        log.info("Cancelling.");
        returnToNetplayConfigurationScreen();
    }
    
    /**
     * Returns the user to the netplay configuration screen.
     * 
     * @throws IOException
     *             In case the netplay configuration screen's fxml cannot be loaded. This should not occur. In order to
     *             get to the Hosting screen the Netplay Configuration screen must have already been loaded succesfully
     *             once. Let the thread's exception handler log the error and let the user close the window manually.
     */
    private void returnToNetplayConfigurationScreen() throws IOException {
        Stage stage = retrieveStage();
        NetplayConfigurationScreen firstScreen = new NetplayConfigurationScreen();
        firstScreen.start(stage);
    }
    
    /** Handler for the join button. */
    @FXML
    private void join() {
        log.info("Joining game.");
        // TODO implement
    }
    
    /** Gets the stage associated to this controller. */
    private Stage retrieveStage() {
        return (Stage) hostTextField.getScene().getWindow();
    }
}
