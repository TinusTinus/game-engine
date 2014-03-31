package nl.mvdr.tinustris.controller;

import java.io.IOException;

import javafx.embed.swing.JFXPanel;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.control.TextField;
import nl.mvdr.tinustris.configuration.Behavior;

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

    /**
     * Test method for {@link ConfigurationScreenController#initialize()}.
     * 
     * @throws IOException unexpected exception
     */
    @Test
    public void testInitialisation() {
        ConfigurationScreenController controller = new ConfigurationScreenControllerNoJinput(new RadioButton(),
                new RadioButton(), new ComboBox<>(), new TextField("0"), new TabPane());

        controller.initialize();
    }

    /** Test method for adding a player. */
    @Test
    public void testAddPlayer() {
        TabPane playerTabPane = new TabPane();
        Tab addPlayerTab = new Tab("+");
        // note that the initialisation will add player 1
        playerTabPane.getTabs().addAll(addPlayerTab);
        Button removePlayerButton = new Button();
        ConfigurationScreenController controller = new ConfigurationScreenControllerNoJinput(new RadioButton(),
                new RadioButton(), new ComboBox<>(), new TextField("0"), playerTabPane);
        controller.initialize();

        playerTabPane.getSelectionModel().select(addPlayerTab);

        Assert.assertEquals(3, playerTabPane.getTabs().size());
        Assert.assertFalse(removePlayerButton.isDisable());
        Assert.assertEquals(TabClosingPolicy.SELECTED_TAB, playerTabPane.getTabClosingPolicy());
    }

    /** Test method for removing a player in case there are three players. */
    @Test
    public void testRemoveThreePlayers() {
        TabPane playerTabPane = new TabPane();
        // note that the initialisation will add player 1
        playerTabPane.getTabs().addAll(new Tab("Player 2"), new Tab("Player 3"), new Tab("+"));
        ConfigurationScreenController controller = new ConfigurationScreenControllerNoJinput(new RadioButton(),
                new RadioButton(), new ComboBox<>(), new TextField("0"), playerTabPane);
        controller.initialize();

        playerTabPane.getTabs().remove(0);
        
        Assert.assertEquals(3, playerTabPane.getTabs().size());
        Assert.assertEquals(TabClosingPolicy.SELECTED_TAB, playerTabPane.getTabClosingPolicy());
    }

    /** Test method for removing a player in case there are two players. */
    @Test
    public void testRemoveTwoPlayers() {
        TabPane playerTabPane = new TabPane();
        // note that the initialisation will add player 1
        playerTabPane.getTabs().addAll(new Tab("Player 2"), new Tab("+"));
        ConfigurationScreenController controller = new ConfigurationScreenControllerNoJinput(new RadioButton(),
                new RadioButton(), new ComboBox<>(), new TextField("0"), playerTabPane);
        controller.initialize();

        playerTabPane.getTabs().remove(0);

        Assert.assertEquals(2, playerTabPane.getTabs().size());
        Assert.assertEquals(TabClosingPolicy.UNAVAILABLE, playerTabPane.getTabClosingPolicy());
    }
    
    /** Test method for choosing TGM behavior. */
    @Test
    public void testChooseTGM() {
        ComboBox<Behavior> behaviorComboBox = new ComboBox<>();
        TextField startLevelTextField = new TextField("0");
        ConfigurationScreenController controller = new ConfigurationScreenControllerNoJinput(new RadioButton(),
                new RadioButton(), behaviorComboBox, startLevelTextField, new TabPane());
        controller.initialize();
        
        behaviorComboBox.getSelectionModel().select(Behavior.THE_GRANDMASTER);
        
        Assert.assertTrue(startLevelTextField.isDisable());
    }
    
    /** Test method for choosing Game Boy Tetris behavior. */
    @Test
    public void testChooseGameBoy() {
        ComboBox<Behavior> behaviorComboBox = new ComboBox<>();
        TextField startLevelTextField = new TextField("0");
        ConfigurationScreenController controller = new ConfigurationScreenControllerNoJinput(new RadioButton(),
                new RadioButton(), behaviorComboBox, startLevelTextField, new TabPane());
        controller.initialize();
        
        behaviorComboBox.getSelectionModel().select(Behavior.GAME_BOY);
        
        Assert.assertFalse(startLevelTextField.isDisable());
    }
    
    /** Test case which sets the start level. */
    @Test
    public void testSetStartLevel0() {
        TextField startLevelTextField = new TextField("10");
        ConfigurationScreenController controller = new ConfigurationScreenControllerNoJinput(new RadioButton(),
                new RadioButton(), new ComboBox<>(), startLevelTextField, new TabPane());
        controller.initialize();
        
        startLevelTextField.setText("0");
        
        Assert.assertEquals("0", startLevelTextField.getText());
    }
    
    /** Test case which sets the start level. */
    @Test
    public void testSetStartLevel10() {
        TextField startLevelTextField = new TextField("0");
        ConfigurationScreenController controller = new ConfigurationScreenControllerNoJinput(new RadioButton(),
                new RadioButton(), new ComboBox<>(), startLevelTextField, new TabPane());
        controller.initialize();
        
        startLevelTextField.setText("10");
        
        Assert.assertEquals("10", startLevelTextField.getText());
    }
    
    /** Test case which sets the start level. */
    @Test
    public void testSetStartLevelNegative() {
        TextField startLevelTextField = new TextField("10");
        ConfigurationScreenController controller = new ConfigurationScreenControllerNoJinput(new RadioButton(),
                new RadioButton(), new ComboBox<>(), startLevelTextField, new TabPane());
        controller.initialize();
        
        startLevelTextField.setText("-3");
        
        Assert.assertEquals("10", startLevelTextField.getText());
    }
    
    /** Test case which sets the start level. */
    @Test
    public void testSetStartLevelLetters() {
        TextField startLevelTextField = new TextField("10");
        ConfigurationScreenController controller = new ConfigurationScreenControllerNoJinput(new RadioButton(),
                new RadioButton(), new ComboBox<>(), startLevelTextField, new TabPane());
        controller.initialize();
        
        startLevelTextField.setText("derp");
        
        Assert.assertEquals("10", startLevelTextField.getText());
    }
}
