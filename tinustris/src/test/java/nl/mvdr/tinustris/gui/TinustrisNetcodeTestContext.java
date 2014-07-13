package nl.mvdr.tinustris.gui;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javafx.application.Application;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import nl.mvdr.tinustris.configuration.Behavior;
import nl.mvdr.tinustris.configuration.Configuration;
import nl.mvdr.tinustris.configuration.ConfigurationImpl;
import nl.mvdr.tinustris.configuration.LocalPlayerConfiguration;
import nl.mvdr.tinustris.configuration.NetcodeConfiguration;
import nl.mvdr.tinustris.configuration.RemoteConfiguration;
import nl.mvdr.tinustris.logging.Logging;

/**
 * Main class, whose main method starts Tinustris with a netcode configuration.
 * 
 * Workaround for the fact that the test classpath (which contains useful things such as a log4j configuration) does not
 * seem to be available when running {@link Tinustris#main(String[])} directly in Eclipse Kepler.
 * 
 * This class relies on JInput's native libraries. These are not available by default. If you want to run this
 * class, make sure that the java.library.path system property contains target/natives in this project directory.
 * 
 * In Eclipse, you can do this by opening the Run Configuration, opening the arguments tab and pasting the following
 * into the "VM Arguments" text area:
 * 
 * <pre>
 * -Djava.library.path=${project_loc}/target/natives
 * </pre>
 * 
 * @author Martijn van de Rijdt
 */
@Slf4j
public class TinustrisNetcodeTestContext extends Application {
    /** Port number. */
    private static final int PORT = 8080;
    
    /** Tinustris instances. */
    private final List<Tinustris> gameInstances;
    
    /** Socket. */
    private final List<Socket> sockets;
    
    /** Constructor. */
    public TinustrisNetcodeTestContext() {
        super();
        
        this.gameInstances = Arrays.asList(new Tinustris(), new Tinustris());
        
        try {
            Future<Socket> serverSocketFuture = Executors.newSingleThreadExecutor().submit(() -> new ServerSocket(PORT).accept());
            Socket clientSocket = new Socket("localhost", PORT);
            
            this.sockets = Arrays.asList(clientSocket, serverSocketFuture.get());
        } catch (InterruptedException | ExecutionException e) {
            throw new IllegalStateException("Unable to open server socket.", e);
        } catch (IOException e) {
            throw new IllegalStateException("Unable to open client socket.", e);
        }
    }
    
    /** {@inheritDoc} */
    @Override
    public void start(Stage stage) {
        log.info("Starting application.");
        Logging.setUncaughtExceptionHandler();
        
        try {
            ObjectOutputStream out = new ObjectOutputStream(sockets.get(0).getOutputStream());
            RemoteConfiguration remote0 = new RemoteConfiguration(Optional.of(out), Optional.empty());
            NetcodeConfiguration netcodeConfiguration0 = () -> Collections.singletonList(remote0);
            Configuration configuration0 = new ConfigurationImpl(
                    Collections.singletonList((LocalPlayerConfiguration) () -> ""), GraphicsStyle.defaultStyle(),
                    Behavior.defaultBehavior(), 0, netcodeConfiguration0, new Random().nextLong(), 
                    new Random().nextLong());
            gameInstances.get(0).start(stage, configuration0);
            
            ObjectInputStream in = new ObjectInputStream(sockets.get(1).getInputStream());
            RemoteConfiguration remote1 = new RemoteConfiguration(Optional.empty(), Optional.of(in));
            NetcodeConfiguration netcodeConfiguration1 = () -> Collections.singletonList(remote1);
            Configuration configuration1 = new ConfigurationImpl(
                    Collections.singletonList(() -> ""), GraphicsStyle.defaultStyle(),
                    Behavior.defaultBehavior(), 0, netcodeConfiguration1, configuration0.getGapRandomSeed(),
                    configuration0.getTetrominoRandomSeed());
            gameInstances.get(1).start(new Stage(), configuration1);
        } catch (IOException e) {
            log.error("Unexpected exception.", e);
        }
    }
    
    /** {@inheritDoc} */
    @Override
    public void stop() throws Exception {
        log.info("Stopping the application.");
        
        gameInstances.forEach(Tinustris::stopGameLoop);
        
        sockets.forEach(socket -> {
            try {
                socket.close();
            } catch (Exception e) {
                log.error("Failed to close socket.", e);
            }
        });

        super.stop();
        log.info("Stopped.");
    }
    
    /**
     * Main method.
     * 
     * @param args
     *            command-line parameters
     */
    public static void main(String[] args) {
        log.info("Starting Tinustris.");

        Logging.logVersionInfo();
        
        // JInput uses java.util.logging; redirect to slf4j.
        Logging.installSlf4jBridge();
        
        // Launch the application!
        launch(args);
    }
}
