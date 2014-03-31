package nl.mvdr.tinustris.controller;

import java.util.Map.Entry;
import java.util.Set;

import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import nl.mvdr.tinustris.input.Input;
import nl.mvdr.tinustris.input.InputMapping;

/**
 * Controller for the player configuration user interface component.
 * 
 * @author Martijn van de Rijdt
 */
@Slf4j
@ToString
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class PlayerConfigurationController {
    /** Text field for player name. */
    @FXML
    private TextField nameTextField;
    /** Table view showing off all of the inputs. */
    @FXML
    private TableView<Entry<Input, Set<InputMapping>>> inputTable; 
    
    /** Performs controller initialisation. */
    @FXML
    void initialize() {
        log.info("Initialising.");
        if (log.isDebugEnabled()) {
            log.debug(this.toString());
        }
        
        // TODO initialisation
        
        log.info("Initialisation complete.");
        if (log.isDebugEnabled()) {
            log.debug(this.toString());
        }
    }
    
    /** @return player name property */
    StringProperty nameProperty() {
        return this.nameTextField.textProperty();
    }
}
