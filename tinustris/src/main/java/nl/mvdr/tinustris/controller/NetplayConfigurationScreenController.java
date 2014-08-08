package nl.mvdr.tinustris.controller;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.stage.Stage;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import nl.mvdr.tinustris.gui.ConfigurationScreen;
import nl.mvdr.tinustris.gui.HostingScreen;
import nl.mvdr.tinustris.gui.JoiningScreen;

/**
 * Controller for the netplay configuration screen.
 * 
 * @author Martijn van de Rijdt
 */
@Slf4j
@ToString
public class NetplayConfigurationScreenController {
    /** Radio button for an offline game. */
    @FXML
    private RadioButton offlineRadioButton;
    /** Radio button for hosting a netplay game.  */
    @FXML
    private RadioButton hostRadioButton;
    /** Radio button for joining a netplay game. */
    @FXML
    private RadioButton joinRadioButton;
    
    /** Performs the initialisation. */
    @FXML
    private void initialize() {
        log.info("Controller initialised: {}", this);
    }
    
    /**
     * Handles the player activating the "next" button.
     * 
     * @param event event leading to this method call; its source should be a component in the configuration screen stage
     * @throws Exception unexpected exception on loading the next screen
     */
    @FXML
    private void next(ActionEvent event) throws Exception {
        log.info("Next button activated.");
        
        Application nextScreen = createNextScreen();
        
        Node source = (Node)event.getSource();
        Stage stage = (Stage)source.getScene().getWindow();
        
        log.info("Starting the next screen: " + nextScreen);
        
        nextScreen.start(stage);
    }
    
    /**
     * Creates an application for the next screen, depending on the player's selected radio button.
     * 
     * @return application
     */
    private Application createNextScreen() {
        Application result;
        
        Toggle selectedRadioButton = offlineRadioButton.getToggleGroup().getSelectedToggle();
        if (selectedRadioButton == offlineRadioButton) {
            result = new ConfigurationScreen(new ConfigurationScreenController());
        } else if (selectedRadioButton == hostRadioButton) {
            result = new HostingScreen();
        } else if (selectedRadioButton == joinRadioButton) {
            result = new JoiningScreen();
        } else {
            // Should not occur; there is a default radio button selection and there is no way to deselect.
            throw new IllegalStateException("Unexpected selection: " + selectedRadioButton);
        }
        
        return result;
    }
}
