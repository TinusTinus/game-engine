package nl.mvdr.tinustris.gui;

import java.io.IOException;

import javafx.application.Application;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import nl.mvdr.tinustris.logging.Logging;

/**
 * Main class that starts a {@link ConfigurationScreen}.
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
public class ConfigurationScreenTestContext extends ConfigurationScreen {
    /** {@inheritDoc} */
    @Override
    public void start(Stage primaryStage) throws IOException {
        Logging.setUncaughtExceptionHandler();
        super.start(primaryStage);
    }
    
    /**
     * Main method.
     * 
     * @param args commandline arguments; these are passed on to JavaFX
     */
    public static void main(String[] args) {
        log.info("Starting Tinustris configuration screen.");

        Logging.logVersionInfo();
        
        // JInput uses java.util.logging; redirect to slf4j.
        Logging.installSlf4jBridge();

        Application.launch(args);
    }
}
