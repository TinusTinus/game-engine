package nl.mvdr.tinustris.controller;

import java.util.stream.Stream;

import javafx.collections.ListChangeListener.Change;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.Toggle;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import nl.mvdr.tinustris.gui.GraphicsStyle;

/**
 * Controller for a configuration screen.
 * 
 * @author Martijn van de Rijdt
 */
@Slf4j
@ToString
@NoArgsConstructor
// All args constructor for use in unit tests.
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class ConfigurationScreenController {
    /** Radio button for 2D graphics. */
    @FXML
    private RadioButton graphics2DRadioButton;
    /** Radio button for 3D graphics. */
    @FXML
    private RadioButton graphics3DRadioButton;
    /** Tab pane for player configuration. */
    @FXML
    private TabPane playerTabPane;
    /** Button for removing a player. */
    @FXML
    private Button removePlayerButton;
    
    /** Initialisation method. */
    @FXML
    // default visibility for unit test
    void initialize() {
        log.info("Initialising.");
        if (log.isDebugEnabled()) {
            log.debug(this.toString());
        }
        
        Stream.of(graphics2DRadioButton, graphics3DRadioButton)
            .forEach(radioButton ->
                radioButton.setOnAction(event -> log.info("Activated " + radioButton.getText())));
        Stream.of(graphics2DRadioButton, graphics3DRadioButton)
            .forEach(radioButton -> radioButton.setDisable(!toGraphicsStyleValue(radioButton).isAvailable()));
        
        playerTabPane.getTabs().addListener(this::handlePlayerTabListChanged);
        
        // TODO further initialisation
        
        log.info("Initialisation complete.");
        if (log.isDebugEnabled()) {
            log.debug(this.toString());
        }
    }

    /**
     * Returns the graphics style value corresponding to the given ui component.
     * 
     * @param toggle ui component; must be one of the radio buttons corresponding to a graphical style
     * @return graphics style
     */
    private GraphicsStyle toGraphicsStyleValue(Toggle toggle) {
        GraphicsStyle result;
        if (toggle == graphics2DRadioButton) {
            result = GraphicsStyle.TWO_DIMENSIONAL;
        } else if (toggle == graphics3DRadioButton) {
            result = GraphicsStyle.THREE_DIMENSIONAL;
        } else {
            throw new IllegalArgumentException("Unexpected parameter: " + toggle);
        }
        return result;
    }
    
    /** Handler for when the list of player tabs has been changed. */
    private void handlePlayerTabListChanged(Change<? extends Tab> change) {
        log.info("Player tab list changed: " + change);
        
        removePlayerButton.setDisable(playerTabPane.getTabs().size() <= 1);
    }
    
    /** Action handler for the remove player button. */
    @FXML
    // default visibility for unit test
    void removePlayer() {
        log.info("Remove player button activated.");
        
        int selectedIndex = playerTabPane.getSelectionModel().getSelectedIndex();
        playerTabPane.getTabs().remove(selectedIndex);
    }
    
    /** Action handler for the add player button. */
    @FXML
    // default visibility for unit test
    void addPlayer() {
        log.info("Add player button activated.");
        
        String defaultPlayerName = "Player " + (playerTabPane.getTabs().size() + 1);
        Tab tab = new Tab(defaultPlayerName);
        // TODO set the contents of the new tab
        playerTabPane.getTabs().add(tab);
    }
    
    /** Starts the game. */
    @FXML
    private void startGame() {
        log.info("Start game button activated.");
        
        // TODO close the dialog and start the game
    }
}
