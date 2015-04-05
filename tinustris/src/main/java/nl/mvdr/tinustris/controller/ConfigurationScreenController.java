package nl.mvdr.tinustris.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener.Change;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.stage.Stage;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import nl.mvdr.tinustris.configuration.Behavior;
import nl.mvdr.tinustris.configuration.Configuration;
import nl.mvdr.tinustris.configuration.ConfigurationImpl;
import nl.mvdr.tinustris.configuration.PlayerConfiguration;
import nl.mvdr.tinustris.gui.GraphicsStyle;
import nl.mvdr.tinustris.gui.Tinustris;
import nl.mvdr.tinustris.input.DefaultControllerConfiguration;
import nl.mvdr.tinustris.input.Input;
import nl.mvdr.tinustris.input.JInputControllerConfiguration;
import nl.mvdr.tinustris.input.NoSuitableControllerException;

/**
 * Controller for a configuration screen.
 * 
 * @author Martijn van de Rijdt
 */
@Slf4j
@ToString
public class ConfigurationScreenController {
    /** Radio button for 2D graphics. */
    @FXML
    private RadioButton graphics2DRadioButton;
    /** Radio button for 3D graphics. */
    @FXML
    private RadioButton graphics3DRadioButton;
    /** Combo box for Behavior value. */
    @FXML
    private ComboBox<Behavior> behaviorComboBox;
    /** Text field for starting level. */
    @FXML
    private TextField startLevelTextField;
    /** Tab pane for player configuration. */
    @FXML
    private TabPane playerTabPane;
    
    /** Controllers for the contents of the player tabs. */
    private final List<PlayerConfigurationController> playerConfigurationControllers;
    /** Random seed for the gap generator. */
    private final long gapRandomSeed;
    /** Random seed for the tetromino generator. */
    private final long tetrominoRandomSeed;

    /**
     * Constructor.
     * 
     * @param gapRandomSeed random seed for the gap generator
     * @param tetrominoRandomSeed random seed for the tetromino generator
     */
    public ConfigurationScreenController(long gapRandomSeed, long tetrominoRandomSeed) {
        super();
        this.playerConfigurationControllers = new ArrayList<>();
        this.gapRandomSeed = gapRandomSeed;
        this.tetrominoRandomSeed = tetrominoRandomSeed;
    }
    
    /** Constructor for an offline game. */
    public ConfigurationScreenController() {
        this(new Random().nextLong(), new Random().nextLong());
    }

    /**
     * Constructor which initialises all fields. Intended for unit tests, since at runtime the user interface components
     * will be injected after initialisation.
     * 
     * @param graphics2DRadioButton
     *            radio button for 2D graphics
     * @param graphics3DRadioButton
     *            radio button for 3D graphics
     * @param behaviorComboBox
     *            combo box for choosing game behavior
     * @param startLevelTextField
     *            text field for entering the game's starting level
     * @param playerTabPane
     *            tab pane for player configuration
     */
    ConfigurationScreenController(RadioButton graphics2DRadioButton, RadioButton graphics3DRadioButton, ComboBox<Behavior> behaviorComboBox, TextField startLevelTextField, TabPane playerTabPane) {
        this();
        
        this.graphics2DRadioButton = graphics2DRadioButton;
        this.graphics3DRadioButton = graphics3DRadioButton;
        this.behaviorComboBox = behaviorComboBox;
        this.startLevelTextField = startLevelTextField;
        this.playerTabPane = playerTabPane;
    }
    
    /** Initialisation method. */
    @FXML
    // default visibility for unit test
    void initialize() {
        log.info("Initialising.");
        if (log.isDebugEnabled()) {
            log.debug(this.toString());
        }

        initGraphicsRadioButtons();
        initBehaviorComboBox();
        initStartLevelTextField();
        initPlayerTabPane();
        initFirstController();

        log.info("Initialisation complete.");
        if (log.isDebugEnabled()) {
            log.debug(this.toString());
        }
    }

    /** Initialises the radio buttons used to select a graphical style. */
    private void initGraphicsRadioButtons() {
        Stream.of(graphics2DRadioButton, graphics3DRadioButton).forEach(
                radioButton -> radioButton.setOnAction(event -> log.info("Activated " + radioButton.getText())));
        Stream.of(graphics2DRadioButton, graphics3DRadioButton).forEach(
                radioButton -> radioButton.setDisable(!toGraphicsStyle(radioButton).isAvailable()));
    }

    /** Initialises the combo box for entering selecting game engine behavior. */
    private void initBehaviorComboBox() {
        behaviorComboBox.setOnAction(event -> updateStartLevelTextField());
        behaviorComboBox.getItems().setAll(Behavior.values());
        behaviorComboBox.getSelectionModel().select(Behavior.defaultBehavior());
    }

    /** Initialises the text field for entering the starting level. */
    private void initStartLevelTextField() {
        startLevelTextField.textProperty().addListener(this::handleStartLevelTextFieldValueChanged);
    }

    /** Initialises the tab pane containing player configurations. */
    private void initPlayerTabPane() {
        insertPlayerTab(0);
        playerTabPane.getSelectionModel().select(0);
        playerTabPane.getTabs().addListener(this::handlePlayerTabListChanged);
        playerTabPane.getSelectionModel().selectedIndexProperty().addListener(this::handlePlayerTabSelectionChanged);
    }

    /** Initialises the first player configuration controller. */
    private void initFirstController() {
        try {
            JInputControllerConfiguration<Input> configuration = DefaultControllerConfiguration.get();
            playerConfigurationControllers.get(0).updateInputConfiguration(configuration);
        } catch (NoSuitableControllerException e) {
            log.info("Unable to set default controller configuration; leaving the default.", e);
        }
    }

    /**
     * Creates a new player tab, including its contents.
     * 
     * @param index index where to insert the new tab
     */
    private void insertPlayerTab(int index) {
        log.info("Initialising player tab for index {}.", index);

        try {
            String name = "Player " + (index + 1);
        
            Tab tab = new Tab();
        
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/PlayerConfiguration.fxml"));
            Parent parent = loader.load();
            tab.setContent(parent);
            
            this.playerTabPane.getTabs().add(index, tab);
            
            // also initialise and save the controller
            PlayerConfigurationController controller = loader.getController();
            
            tab.textProperty().bind(controller.nameProperty());
            controller.nameProperty().set(name);
            
            if (index == playerConfigurationControllers.size()) {
                playerConfigurationControllers.add(controller);
            } else if (index < playerConfigurationControllers.size()) {
                playerConfigurationControllers.add(index, controller);
            } else {
                throw new IllegalStateException();
            }
        } catch (IOException e) {
            throw new IllegalStateException("Unable to load fxml definition.", e);
        }
    }

    /**
     * Returns the graphics style value corresponding to the given ui component.
     * 
     * @param toggle
     *            ui component; must be one of the radio buttons corresponding to a graphical style
     * @return graphics style
     */
    private GraphicsStyle toGraphicsStyle(Toggle toggle) {
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
    

    /** Updates the start level textfield according to the value currently selected in the behavior combo box. */
    private void updateStartLevelTextField() {
        log.info("Updating status of the start level text field.");
        
        startLevelTextField.setDisable(!behaviorComboBox.getValue().isStartLevelSupported());
    }
    
    /**
     * Handler for when the value of the start level text field changed. If the new value is not valid, this method
     * resores the old value.
     * 
     * @param observable
     *            text property
     * @param oldValue
     *            previous value
     * @param newValue
     *            new value
     */
    private void handleStartLevelTextFieldValueChanged(ObservableValue<? extends String> observable, String oldValue,
            String newValue) {
        if (!isValidStartLevel(newValue)) {
            log.info("Invalid start level value entered: {}. Restoring old value {}.", newValue, oldValue);
            startLevelTextField.setText(oldValue);
        } else {
            log.info("Start level changed from {} to {}.", oldValue, newValue);
        }
    }
    
    /**
     * Checks whether the given string represents a valid start level value.
     * 
     * @param string
     *            string
     * @return whether the given string represents a valid start level value
     */
    private boolean isValidStartLevel(String string) {
        boolean result;
        try {
            int value = Integer.parseInt(string);
            result = 0 <= value;
        } catch (NumberFormatException e) {
            result = false;
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
        
        // for any removed tabs: also remove the associated controller
        while (change.next()) {
            if (change.wasRemoved()) {
                playerConfigurationControllers.remove(change.getFrom());
            }
        }
        
        // Only allow closing a tab when there are at least two player tabs left.
        // Note that the tab containing the "+" also counts for one.
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
            
            // insert a tab
            insertPlayerTab(selectedIndex);

            // make sure the new tab is selected
            playerTabPane.getSelectionModel().select(selectedIndex);
        }
    }

    /**
     * Starts the game.
     * 
     * @param event action event leading to this method call
     */
    @FXML
    private void startGame(ActionEvent event) {
        log.info("Start game button activated.");

        Configuration configuration = buildConfiguration();
        log.info("Configuration: " + configuration);
        
        Node source = (Node)event.getSource();
        Stage stage = (Stage)source.getScene().getWindow();
        
        Tinustris tinustris = new Tinustris();
        
        stage.setOnHidden(e -> tinustris.stopGameLoop());
        
        tinustris.start(stage, configuration);
    }

    /**
     * Creates a Configuration based on the values entered by the user.
     * 
     * @return Configuration
     */
    private Configuration buildConfiguration() {
        List<PlayerConfiguration> playerConfigurations = playerConfigurationControllers.stream()
            .map(PlayerConfigurationController::buildConfiguration)
            .collect(Collectors.toList());
        
        GraphicsStyle graphicsStyle = toGraphicsStyle(graphics2DRadioButton.getToggleGroup().getSelectedToggle());
        
        Behavior behavior = behaviorComboBox.getValue();
        
        int startLevel = Integer.parseInt(startLevelTextField.getText());
        
        return new ConfigurationImpl(playerConfigurations, graphicsStyle, behavior, startLevel, gapRandomSeed,
                tetrominoRandomSeed);
    }
}
