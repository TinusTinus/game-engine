package nl.mvdr.tinustris.controller;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import nl.mvdr.tinustris.configuration.NetcodeConfiguration;
import nl.mvdr.tinustris.gui.ConfigurationScreen;
import nl.mvdr.tinustris.gui.NetplayConfigurationScreen;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

/**
 * Controller for the hosting game screen, where the user is waiting for a remote player to connect.
 * 
 * @author Martijn van de Rijdt
 */
@Slf4j
@ToString
public class HostingController {
    /** Indicates whether the user activated the cancel button. */
    private boolean cancelled;
    
    /** The cancel button. */
    @FXML
    private Button cancelButton;
    
    /** Constructor. */
    public HostingController() {
        super();
        this.cancelled = false;
    }
    
    /** Performs the initialisation. */
    @FXML
    private void initialize() {
        log.info("Initialising.");
        new Thread(this::waitForRemotePlayer, "Hosting").start();
        log.info("Controller initialised: {}", this);
    }
    
    /** Waits for a remote player to connect. */
    private void waitForRemotePlayer() {
        Config hazelcastConfig = new Config("Tinustris");
        hazelcastConfig.getNetworkConfig().setPort(NetcodeConfiguration.PORT);
        HazelcastInstance hazelcast = Hazelcast.newHazelcastInstance(hazelcastConfig);

        Random random = new Random();
        long gapSeed = random.nextLong();
        long tetrominoSeed = random.nextLong();
        
        List<Long> seeds = hazelcast.getList("randomSeeds");
        seeds.add(gapSeed);
        seeds.add(tetrominoSeed);
        
        log.info("Offered seeds: {}, {}", gapSeed, tetrominoSeed);
        
//        // TODO move on to configuration screen if succesfully connected, or return to netcode configuration screen if cancelled
    }
    
    /**
     * Moves the user on to the configuration screen, using the given controller.
     * 
     * @param controller controller
     */
    private void goToConfigurationScreen(ConfigurationScreenController controller) {
        ConfigurationScreen configurationScreen = new ConfigurationScreen(controller);
        try {
            configurationScreen.start(retrieveStage());
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
    
    /** Returns the user to the netplay configuration screen. */
    private void returnToNetplayConfigurationScreen() {
        Stage stage = retrieveStage();
        NetplayConfigurationScreen firstScreen = new NetplayConfigurationScreen();
        try {
            firstScreen.start(stage);
        } catch (IOException e) {
            // Should not occur. In order to get to the Hosting screen the Netplay Configuration screen must
            // have already been loaded succesfully once.
            // Log the error and let the user close the wondow.
            throw new IllegalStateException(e);
        }
    }

    /** Gets the stage associated to this controller. */
    private Stage retrieveStage() {
        return (Stage) cancelButton.getScene().getWindow();
    }
    
    /**
     * Handles the user activating the cancel button.
     * 
     * @param actionEvent action event that lead to this method call
     */
    @FXML
    private void cancel(ActionEvent actionEvent) {
        log.info("Cancel button activated.");
        
        // Make sure the server socket stops waiting for a remote connection.
        // This will happen asynchronously and may take up to TIMEOUT seconds.
        this.cancelled = true;
        
        // Cancel has been succesfully completed, disable the cancel button so the user knows something has happened.
        cancelButton.setDisable(true);
    }
    
    // TODO also cancel when exiting the application through other means than the cancel button!
}
