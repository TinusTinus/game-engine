package nl.mvdr.tinustris.controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Collections;
import java.util.Optional;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import nl.mvdr.tinustris.configuration.NetcodeConfiguration;
import nl.mvdr.tinustris.configuration.RemoteConfiguration;
import nl.mvdr.tinustris.gui.ConfigurationScreen;
import nl.mvdr.tinustris.gui.NetplayConfigurationScreen;

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
        
        String hostname = hostTextField.getText();
        
        log.info("Attempting to join: {}", hostname);

        // TODO don't do all this I/O on the user interface thread
        // TODO don't close the socket here?????
        try (Socket socket = new Socket("localhost", NetcodeConfiguration.PORT)) {
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            RemoteConfiguration remoteConfiguration = new RemoteConfiguration(Optional.of(out), Optional.of(in));
            NetcodeConfiguration netcodeConfiguration = () -> Collections.singletonList(remoteConfiguration);
            ConfigurationScreenController controller = createConfigurationScreenController(netcodeConfiguration, in);
            ConfigurationScreen configurationScreen = new ConfigurationScreen(controller);
            configurationScreen.start(retrieveStage());
        } catch (IOException e) {
            log.error("Unexpected exception.", e);
            // TODO show the user an error message: Unable to connect to ...
        }
    }
    
    /**
     * Creates a configuration screen controller based on the given netcode configuration. Also initialises the random
     * seed values.
     * 
     * @param netcodeConfiguration
     *            netcode configuration
     * @param hostStream
     *            input stream for the host instance of the game; configuration parameters determined by the host are
     *            read from this stream
     * @return controller
     * @throws IOException
     *             in case reading the configuration parameters fails
     */
    private ConfigurationScreenController createConfigurationScreenController(
            NetcodeConfiguration netcodeConfiguration, ObjectInputStream hostStream) throws IOException {
        log.info("Reading gap generator seed.");
        long gapSeed = hostStream.readLong();
        log.info("Read gap generator seed: {}", gapSeed);
        
        log.info("Reading tetromino generator seed.");
        long tetrominoSeed = hostStream.readLong();
        log.info("Read tetromino generator seed: {}", tetrominoSeed);
        
        return new ConfigurationScreenController(netcodeConfiguration, gapSeed, tetrominoSeed);
    }
    
    /** Gets the stage associated to this controller. */
    private Stage retrieveStage() {
        return (Stage) hostTextField.getScene().getWindow();
    }
}
