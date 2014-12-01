package nl.mvdr.tinustris.controller;

import java.io.IOException;
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
import nl.mvdr.tinustris.gui.ConfigurationScreen;
import nl.mvdr.tinustris.gui.NetplayConfigurationScreen;
import nl.mvdr.tinustris.hazelcast.ClientAddedListener;
import nl.mvdr.tinustris.hazelcast.CollectionNames;

import com.hazelcast.config.Config;
import com.hazelcast.core.Client;
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
    
    private final long gapSeed;
    
    private final long tetrominoSeed;
    
    /** The cancel button. */
    @FXML
    private Button cancelButton;
    
    /** Hazelcast instance. */
    private Optional<HazelcastInstance> hazelcast;
    
    /** Identification string of the client listener. */
    private Optional<String> listenerId;
    
    /** Constructor. */
    public HostingController() {
        super();
        
        this.hazelcast = Optional.empty();
        this.listenerId = Optional.empty();
        
        Random random = new Random();
        this.gapSeed = random.nextLong();
        this.tetrominoSeed = random.nextLong();
    }
    
    /** Performs the initialisation. */
    @FXML
    private void initialize() {
        log.info("Initialising.");
        new Thread(this::startWaitingForRemotePlayer, "InitHostingController" + hashCode()).start();
        log.info("Controller initialised: {}", this);
    }
    
    /** Initialises a Hazelcast instance and starts waiting for a remote player to connect. */
    private void startWaitingForRemotePlayer() {
        log.info("Initialising Hazelcast.");
        Config hazelcastConfig = new Config("Tinustris");
        hazelcastConfig.getNetworkConfig().setPort(NetcodeConfiguration.PORT);
        hazelcast = Optional.of(Hazelcast.newHazelcastInstance(hazelcastConfig));
        log.info("Created Hazelcast instance.");

        // Offer the random seeds.
        List<Long> seeds = hazelcast.get().getList(CollectionNames.RANDOM_SEED_LIST);
        seeds.add(gapSeed);
        seeds.add(tetrominoSeed);
        log.info("Offered seeds: {}, {}", gapSeed, tetrominoSeed);

        // Add listener which will be notified when / if a client joins.
        ClientAddedListener listener = this::handleClientAdded;
        String id = hazelcast.get().getClientService().addClientListener(listener);
        listenerId = Optional.of(id);
    }
    
    /** 
     * Handles when a second player connects to this game.
     * 
     * @param client newly added client
     */
    private void handleClientAdded(Client client) {
        log.info("Client joined: {}", client);
        listenerId.ifPresent(id -> {
            listenerId = Optional.empty();
            hazelcast.get().getClientService().removeClientListener(id);
            Platform.runLater(this::goToConfigurationScreen);
        });
    }
    
    /** Moves the user on to the configuration screen. */
    private void goToConfigurationScreen() {
        ConfigurationScreenController controller = new ConfigurationScreenController(() -> hazelcast, gapSeed, tetrominoSeed);
        
        ConfigurationScreen configurationScreen = new ConfigurationScreen(controller);
        try {
            configurationScreen.start(retrieveStage());
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
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

    /** Gets the stage associated to this controller. */
    private Stage retrieveStage() {
        return (Stage) cancelButton.getScene().getWindow();
    }
    
    /**
     * Handles the user activating the cancel button.
     * 
     * @param actionEvent action event that lead to this method call
     * @throws IOException
     *             In case the netplay configuration screen's fxml cannot be loaded. This should not occur. In order to
     *             get to the Hosting screen the Netplay Configuration screen must have already been loaded succesfully
     *             once. Let the thread's exception handler log the error and let the user close the window manually.
     */
    @FXML
    private void cancel(ActionEvent actionEvent) throws IOException {
        log.info("Cancel button activated.");
        
        cancelButton.setDisable(true);
        
        hazelcast.ifPresent(HazelcastInstance::shutdown);
        hazelcast = Optional.empty();
        
        returnToNetplayConfigurationScreen();
    }
    
    // TODO also cancel when exiting the application through other means than the cancel button!
}
