package nl.mvdr.tinustris.controller;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.BlockingQueue;

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

import com.hazelcast.config.Config;
import com.hazelcast.config.NetworkConfig;
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
        Config hazelcastConfig = new Config("Tinustris");
        hazelcastConfig.getNetworkConfig().setPort(NetcodeConfiguration.PORT);
        HazelcastInstance hazelcast = Hazelcast.newHazelcastInstance(hazelcastConfig);
        
        Random random = new Random();
        long gapSeed = random.nextLong();
        long tetrominoSeed = random.nextLong();
        BlockingQueue<Long> queue = hazelcast.getQueue("randomSeeds");
        queue.offer(gapSeed);
        queue.offer(tetrominoSeed);
        
        log.info("Offered seeds: {}, {}", gapSeed, tetrominoSeed);
        
//        Optional<RemoteConfiguration> remoteConfiguration = Optional.empty(); 
//        
//        // TODO should we close the server socket?
//        try (ServerSocket serverSocket = new ServerSocket(NetcodeConfiguration.PORT)) {
//            serverSocket.setSoTimeout(TIMEOUT);
//            log.info("Starting to listen for remote player.");
//            while (!remoteConfiguration.isPresent() && !cancelled) {
//                try {
//                    Socket socket = serverSocket.accept();
//                    ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
//                    ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
//                    remoteConfiguration = Optional.of(new RemoteConfiguration(Optional.of(out), Optional.of(in)));
//                    log.info("Remote player connected!");
//                } catch (SocketTimeoutException e) {
//                    log.info("An expected socket timeout occurred: remote player did not connect in the last {} milliseconds.",
//                        TIMEOUT);
//                    log.debug("Socket timeout.", e);
//                }
//            }
//        } catch (IOException e) {
//            log.error("Unexpected exception.", e);
//            // TODO show the user an error message?
//        }
//        
//        Runnable runnable = remoteConfiguration.filter(r -> !cancelled)
//            .map(Collections::singletonList)
//            .map(remoteConfigurations -> (NetcodeConfiguration)(() -> remoteConfigurations))
//            .map(this::createConfigurationScreenController)
//            .map(controller -> (Runnable)() -> goToConfigurationScreen(controller))
//            .orElse(this::returnToNetplayConfigurationScreen);
//        Platform.runLater(runnable);
    }

    /**
     * Creates a configuration screen controller based on the given netcode configuration. Also initialises the random seed values.
     * 
     * @param netcodeConfiguration netcode configuration
     * @return controller
     */
    private ConfigurationScreenController createConfigurationScreenController(NetcodeConfiguration netcodeConfiguration) {
        Random random = new Random();
        long gapSeed = random.nextLong();
        long tetrominoSeed = random.nextLong();
        
        // Publish the random seeds to remote players.
        netcodeConfiguration.getRemotes().stream()
            .map(RemoteConfiguration::getOutputStream)
            .filter(Optional::isPresent)
            .map(Optional::get)
            .forEach(stream -> publishSeeds(stream, gapSeed, tetrominoSeed));
        
        return new ConfigurationScreenController(netcodeConfiguration, gapSeed, tetrominoSeed);
    }
    
    /**
     * Publishes the given seeds to the given output stream.
     * 
     * @param stream stream
     * @param gapSeed random seed for gap generator
     * @param tetrominoSeed random seed for tetromino generator
     */
    private void publishSeeds(ObjectOutputStream stream, long gapSeed, long tetrominoSeed) {
        try {
            log.info("Publishing gap generator seed: {}", gapSeed);
            stream.writeLong(gapSeed);
            log.info("Publishing tetromino generator seed: {}", tetrominoSeed);
            stream.writeLong(tetrominoSeed);
        } catch (IOException e) {
            log.error("Write failed.", e); // TODO better error handling?
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
