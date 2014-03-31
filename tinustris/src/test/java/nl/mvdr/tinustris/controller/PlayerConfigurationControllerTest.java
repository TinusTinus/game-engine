package nl.mvdr.tinustris.controller;

import javafx.embed.swing.JFXPanel;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import org.junit.Before;
import org.junit.Test;

/**
 * Test class for {@link PlayerConfigurationController}.
 * 
 * @author Martijn van de Rijdt
 */
public class PlayerConfigurationControllerTest {
    /** Setup method. */
    @Before
    public void setUp() {
        // Force JavaFX graphics initialisation; this is required by the TextField constructor.
        // When running the actual application this is performed by Application.launch().
        new JFXPanel();
    }
    
    /** Test method for {@link PlayerConfigurationController#initialize()}. */
    @Test
    public void testInitialize() {
        PlayerConfigurationController controller = new PlayerConfigurationController(new TextField(), new TableView<>());
        
        controller.initialize();
    }
}
