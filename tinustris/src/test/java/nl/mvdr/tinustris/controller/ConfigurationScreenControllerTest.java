package nl.mvdr.tinustris.controller;

import javafx.embed.swing.JFXPanel;
import javafx.scene.control.RadioButton;

import org.junit.Before;
import org.junit.Test;

/**
 * Test class for {@link ConfigurationScreenController}.
 * 
 * @author Martijn van de Rijdt
 */
public class ConfigurationScreenControllerTest {
    /** Setup method. */
    @Before
    public void setUp() {
        // Force JavaFX graphics initialisation; this is required by the Image constructor.
        // When running the actual application this is performed by Application.launch().
        new JFXPanel();
    }
    
    /** Test method for {@link ConfigurationScreenController#initialize()}. */
    @Test
    public void testInitialisation() {
        RadioButton radioButton2D = new RadioButton();
        RadioButton radioButton3D = new RadioButton();
        
        ConfigurationScreenController controller = new ConfigurationScreenController(radioButton2D, radioButton3D);
        
        controller.initialize();
    }
}
