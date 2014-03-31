package nl.mvdr.tinustris.controller;

import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import nl.mvdr.tinustris.configuration.Behavior;

/**
 * Subclass of {@link ConfigurationScreenController} which does not depend on JInput.
 * 
 * @author Martijn van de Rijdt
 */
@NoArgsConstructor(access = AccessLevel.PACKAGE)
class ConfigurationScreenControllerNoJinput extends ConfigurationScreenController {
    /**
     * Constructor.
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
    ConfigurationScreenControllerNoJinput(RadioButton graphics2dRadioButton, RadioButton graphics3dRadioButton,
            ComboBox<Behavior> behaviorComboBox, TextField startLevelTextField, TabPane playerTabPane) {
        super(graphics2dRadioButton, graphics3dRadioButton, behaviorComboBox, startLevelTextField, playerTabPane);
    }
    
    /** {@inheritDoc} */
    @Override
    protected void initFirstController() {
        // do nothing
    }
}
