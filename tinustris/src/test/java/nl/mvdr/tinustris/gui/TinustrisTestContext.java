package nl.mvdr.tinustris.gui;

import nl.mvdr.tinustris.configuration.Configuration;
import nl.mvdr.tinustris.logging.Logging;
import javafx.application.Application;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

/**
 * Main class, whose main method simply starts Tinustris with a default configuration.
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
public class TinustrisTestContext extends Application {
    /** Tinustris instance. */
    private final Tinustris tinustris;
    
    /** Constructor. */
    public TinustrisTestContext() {
        super();
        this.tinustris = new Tinustris();
    }
    
    /** {@inheritDoc} */
    @Override
    public void start(Stage stage) {
        log.info("Starting application.");
        Logging.logVersionInfo();
        Logging.setUncaughtExceptionHandler();
        
        tinustris.start(stage, new Configuration() {});
    }
    
    /** {@inheritDoc} */
    @Override
    public void stop() throws Exception {
        log.info("Stopping the application.");
        
        tinustris.stopGameLoop();
        
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
        
        // JInput uses java.util.logging; redirect to slf4j.
        Logging.installSlf4jBridge();
        
        // Launch the application!
        launch(args);
    }
}
