package nl.mvdr.tinustris.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import nl.mvdr.tinustris.configuration.NetcodeConfiguration;
import nl.mvdr.tinustris.gui.ConfigurationScreen;
import nl.mvdr.tinustris.gui.NetplayConfigurationScreen;
import nl.mvdr.tinustris.hazelcast.CollectionNames;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.client.config.ClientNetworkConfig;
import com.hazelcast.core.HazelcastInstance;

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
    
    /**
     * Handler for the join button.
     * 
     * @throws IOException in case the configuration screen cannot be loaded
     */
    @FXML
    private void join() throws IOException {
        log.info("Joining game.");
        ClientNetworkConfig networkConfig = new ClientNetworkConfig();
        networkConfig.addAddress(hostTextField.getText() + ":" + NetcodeConfiguration.PORT);
        ClientConfig hazelcastConfiguration = new ClientConfig();
        hazelcastConfiguration.setNetworkConfig(networkConfig);
        HazelcastInstance hazelcast = HazelcastClient.newHazelcastClient(hazelcastConfiguration);
        log.info("Succesfully joined the Hazelcast cluster.");
        // TODO catch IllegalStateException (?) and show the user an error message if connecting fails
        
        List<Long> seeds = hazelcast.getList(CollectionNames.RANDOM_SEED_LIST.toString());
        long gapSeed = seeds.get(0);
        long tetrominoSeed = seeds.get(1);
        log.info("Received seeds: {}, {}", gapSeed, tetrominoSeed);
        
        goToConfigurationScreen(hazelcast, gapSeed, tetrominoSeed);
    }
    
    /** 
     * Moves the user on to the configuration screen.
     * @throws IOException in case the configuration screen cannot be loaded
     */
    private void goToConfigurationScreen(HazelcastInstance hazelcast, long gapSeed, long tetrominoSeed) throws IOException {
        ConfigurationScreenController controller = new ConfigurationScreenController(() -> Optional.of(hazelcast), gapSeed, tetrominoSeed);
        ConfigurationScreen configurationScreen = new ConfigurationScreen(controller);
        configurationScreen.start(retrieveStage());
    }
    
    /** Gets the stage associated to this controller. */
    private Stage retrieveStage() {
        return (Stage) hostTextField.getScene().getWindow();
    }
}
