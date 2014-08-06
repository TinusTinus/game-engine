package nl.mvdr.tinustris.controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import nl.mvdr.tinustris.configuration.NetcodeConfiguration;
import nl.mvdr.tinustris.configuration.RemoteConfiguration;
import nl.mvdr.tinustris.gui.ConfigurationScreen;
import nl.mvdr.tinustris.gui.NetplayConfigurationScreen;

/**
 * Controller for the hosting game screen, where the user is waiting for a remote player to connect.
 * 
 * @author Martijn van de Rijdt
 */
@Slf4j
@ToString
public class HostingController {
    /** Port used for online multiplayer. */
    private static final int PORT = 8082; // TODO move this constant somewhere else for reuse
    /**
     * Timeout while waiting for a remote player. This governs how often the hosting thread checks whether the user has
     * clicked cancel.
     */
    private static final int TIMEOUT = 5000;
    
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
        RemoteConfiguration remoteConfiguration = null; 
        
        // TODO should we close the server socket?
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            serverSocket.setSoTimeout(TIMEOUT);
            log.info("Starting to listen for remote player.");
            while (remoteConfiguration == null && !cancelled) {
                try {
                    Socket socket = serverSocket.accept();
                    ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                    ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                    remoteConfiguration = new RemoteConfiguration(Optional.of(out), Optional.of(in));
                    log.info("Remote player connected!");
                } catch (SocketTimeoutException e) {
                    log.info("An expected socket timeout occurred: remote player did not connect in the last {} milliseconds.",
                        TIMEOUT);
                    log.debug("Socket timeout.", e);
                }
            }
        } catch (IOException e) {
            log.error("Unexpected exception.", e);
            // TODO show the user an error message?
        }
        
        if (remoteConfiguration != null) {
            List<RemoteConfiguration> remoteConfigurations = Collections.singletonList(remoteConfiguration);
            NetcodeConfiguration netcodeConfiguration = () -> remoteConfigurations;
            
            Random random = new Random();
            long gapSeed = random.nextLong();
            long tetrominoSeed = random.nextLong();
            
            ConfigurationScreenController controller = new ConfigurationScreenController(netcodeConfiguration, gapSeed, tetrominoSeed);
            
            Platform.runLater(() -> goToConfigurationScreen(controller));
        } else {
            // User has cancelled or an exception has occurred.
            Platform.runLater(this::returnToFirstScreen);
        }
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
    private void returnToFirstScreen() {
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
