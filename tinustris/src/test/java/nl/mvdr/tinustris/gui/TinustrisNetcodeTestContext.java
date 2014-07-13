package nl.mvdr.tinustris.gui;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;

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
    
    /** Tinustris instance. */
    private final Tinustris tinustris;
    
    /** Socket. */
    private Socket socket;
    
    /** Constructor. */
    public TinustrisNetcodeTestContext() {
        super();
        this.tinustris = new Tinustris();
    }
    
    /** {@inheritDoc} */
    @Override
    public void start(Stage stage) {
        log.info("Starting application.");
        Logging.setUncaughtExceptionHandler();
        
        // server socket: reads an object and logs it
        new Thread(() -> {
            try (Socket socket = new ServerSocket(PORT).accept()) {
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                // TODO termination!
                while (true) {
                    log.info("Read: " + in.readObject());
                }
            } catch (IOException | ClassNotFoundException e) {
                log.error("Unexpected exception.", e);
            }
        }, "server").start();

        try {
            socket = new Socket("localhost", PORT);
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            RemoteConfiguration remote = new RemoteConfiguration(out, null);
            NetcodeConfiguration netcodeConfiguration = () -> Collections.singletonList(remote);

            Configuration configuration = new ConfigurationImpl(
                    Collections.singletonList((LocalPlayerConfiguration) () -> ""), GraphicsStyle.defaultStyle(),
                    Behavior.defaultBehavior(), 0, netcodeConfiguration);
            
            tinustris.start(stage, configuration);
        } catch (IOException e) {
            log.error("Unexpected exception.", e);
        }
    }
    
    /** {@inheritDoc} */
    @Override
    public void stop() throws Exception {
        log.info("Stopping the application.");
        
        tinustris.stopGameLoop();
        
        socket.close();
        
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
