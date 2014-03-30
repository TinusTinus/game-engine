package nl.mvdr.tinustris.controller;

import javafx.embed.swing.JFXPanel;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

import org.junit.Assert;
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
        // Force JavaFX graphics initialisation; this is required by the RadioButton constructor.
        // When running the actual application this is performed by Application.launch().
        new JFXPanel();
    }

    /** Test method for {@link ConfigurationScreenController#initialize()}. */
    @Test
    public void testInitialisation() {
        ConfigurationScreenController controller = new ConfigurationScreenController(new RadioButton(),
                new RadioButton(), new TabPane(), new Button());

        controller.initialize();
    }

    /** Test method for {@link ConfigurationScreenController#addPlayer()}. */
    @Test
    public void testAddPlayer() {
        TabPane playerTabPane = new TabPane();
        playerTabPane.getTabs().add(new Tab());
        Button removePlayerButton = new Button();
        removePlayerButton.setDisable(true);
        ConfigurationScreenController controller = new ConfigurationScreenController(new RadioButton(),
                new RadioButton(), playerTabPane, removePlayerButton);
        controller.initialize();

        controller.addPlayer();

        Assert.assertEquals(2, playerTabPane.getTabs().size());
        Assert.assertFalse(removePlayerButton.isDisable());
    }

    /** Test method for {@link ConfigurationScreenController#removePlayer()} in case there are three players. */
    @Test
    public void testRemoveThreePlayers() {
        TabPane playerTabPane = new TabPane();
        playerTabPane.getTabs().addAll(new Tab(), new Tab(), new Tab());
        Button removePlayerButton = new Button();
        removePlayerButton.setDisable(false);
        ConfigurationScreenController controller = new ConfigurationScreenController(new RadioButton(),
                new RadioButton(), playerTabPane, removePlayerButton);
        controller.initialize();

        controller.removePlayer();

        Assert.assertEquals(2, playerTabPane.getTabs().size());
        Assert.assertFalse(removePlayerButton.isDisable());
    }

    /** Test method for {@link ConfigurationScreenController#removePlayer()} in case there are three players. */
    @Test
    public void testRemoveTwoPlayers() {
        TabPane playerTabPane = new TabPane();
        playerTabPane.getTabs().addAll(new Tab(), new Tab());
        Button removePlayerButton = new Button();
        removePlayerButton.setDisable(false);
        ConfigurationScreenController controller = new ConfigurationScreenController(new RadioButton(),
                new RadioButton(), playerTabPane, removePlayerButton);
        controller.initialize();

        controller.removePlayer();

        Assert.assertEquals(1, playerTabPane.getTabs().size());
        Assert.assertTrue(removePlayerButton.isDisable());
    }
}
