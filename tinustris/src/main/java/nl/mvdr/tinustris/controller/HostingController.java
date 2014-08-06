package nl.mvdr.tinustris.controller;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import lombok.extern.slf4j.Slf4j;

/**
 * Controller for the hosting game screen, where the user is waiting for a remote player to connect.
 * 
 * @author Martijn van de Rijdt
 */
@Slf4j
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
    /** Socket. Null initially; gets a value when / if the remote player connects. */
    private Socket socket;
    
    public HostingController() {
        super();
        this.cancelled = false;
    }
    
    /** Performs the initialisation. */
    @FXML
    private void initialize() {
        log.info("Initialising.");
        new Thread(this::waitForRemotePlayer, "Hosting").start();
    }
    
    /** Waits for a remote player to connect. */
    private void waitForRemotePlayer() {
        // TODO should we close the server socket?
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            serverSocket.setSoTimeout(TIMEOUT);
            log.info("Starting to listen for remote player.");
            while (socket == null && !cancelled) {
                try {
                    socket = serverSocket.accept();
                    log.info("Remote player connected!");
                } catch (SocketTimeoutException e) {
                    log.info("An expected socket timeout occurred: remote player did not connect in the last {} milliseconds.", TIMEOUT);
                    log.debug("Socket timeout.", e);
                }
            }
        } catch (IOException e) {
            log.error("Unexpected exception.", e);
        }
        
        if (socket != null) {
            // TODO offload to the JavaFX thread: move onto the ConfigurationScreen with the newly configured Socket
        } else {
            // TODO exit the application, or return to the first screen
        }
    }
    
    /**
     * Handles the user activating the cancel button.
     * 
     * @param actionEvent action event that lead to this method call
     */
    @FXML
    private void cancel(ActionEvent actionEvent) {
        log.info("Cancel button activated.");
        
        this.cancelled = true;
        
        Node node = (Node)actionEvent.getSource();
        node.setDisable(true);
    }
    
    // TODO also cancel when exiting the application through other means than the cancel button!
}
