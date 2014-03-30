package nl.mvdr.tinustris.controller;

import java.util.stream.Stream;

import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener.Change;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
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
        playerTabPane.getSelectionModel().selectedIndexProperty().addListener(this::handlePlayerTabSelectionChanged);
        
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
    
    /**
     * Handler for when the list of player tabs has been changed.
     * 
     * @param change
     *            the change in the list
     */
    private void handlePlayerTabListChanged(Change<? extends Tab> change) {
        log.info("Player tab list changed: " + change);
        
        // only allow closing a tab when there are at least two
        TabClosingPolicy policy;
        if (2 < change.getList().size()) {
            policy = TabClosingPolicy.SELECTED_TAB;
        } else {
            policy = TabClosingPolicy.UNAVAILABLE;
        }
        playerTabPane.setTabClosingPolicy(policy);
    }
    
    /**
     * Handles changes in the player tab selection.
     * 
     * @param observable
     *            index property
     * @param oldValue
     *            old value
     * @param newValue
     *            new value
     */
    private void handlePlayerTabSelectionChanged(ObservableValue<? extends Number> observable, Number oldValue,
            Number newValue) {
        log.info("Player tab selection changed from {} ({}) to {} ({})", oldValue,
                playerTabPane.getTabs().get(oldValue.intValue()).getText(), newValue,
                playerTabPane.getTabs().get(newValue.intValue()).getText());
        
        int selectedIndex = newValue.intValue();
        if (selectedIndex == playerTabPane.getTabs().size() - 1) {
            // add player tab clicked

            String defaultPlayerName = "Player " + playerTabPane.getTabs().size();
            Tab tab = new Tab(defaultPlayerName);
            // TODO set the contents of the new tab
            playerTabPane.getTabs().add(playerTabPane.getTabs().size() - 1, tab);
            
            // make sure the new tab is selected
            playerTabPane.getSelectionModel().select(tab);
        }
    }
    
    /** Action handler for the add player button. */
    @FXML
    // default visibility for unit test
    void addPlayer() {
        log.info("Add player button activated.");
        
        String defaultPlayerName = "Player " + (playerTabPane.getTabs().size() + 1);
        Tab tab = new Tab(defaultPlayerName);
        // TODO set the contents of the new tab
        playerTabPane.getTabs().add(playerTabPane.getTabs().size() - 1, tab);
    }
    
    /** Starts the game. */
    @FXML
    private void startGame() {
        log.info("Start game button activated.");
        
        // TODO close the dialog and start the game
    }
}
