package nl.mvdr.tinustris.configuration;

import nl.mvdr.tinustris.gui.GraphicsStyle;
import nl.mvdr.tinustris.input.JInputControllerConfiguration;

/**
 * Game configuration.
 * 
 * Sensible defaults have been included for all methods, using default methods.
 * 
 * @author Martijn van de Rijdt
 */
public interface Configuration {
    
    /**
     * Number of players for this game. Must be at least 1.
     * 
     * @return number of players
     */
    default int getNumberOfPlayers() {
        return 1;
    }
    
    /**
     * Graphical style of the blocks in the grid.
     * 
     * @return style
     */
    default GraphicsStyle getGraphicsStyle() {
        return GraphicsStyle.defaultStyle();
    }
    
    /**
     * Configuration for JInput for one of the players, used by {@link nl.mvdr.tinustris.input.JInputController}.
     * 
     * @param index player number; must be at least 0 and less than the value of {@link getNumbreOfPlayers()}
     * @return input controller configuration
     */
    default JInputControllerConfiguration getJInputControllerConfiguration(int index) {
        if (index < 0 || getNumberOfPlayers() <= index) {
            throw new IndexOutOfBoundsException(String.format("Index %s, should be between 0 and %s.", index,
                    getNumberOfPlayers()));
        }
        
        return JInputControllerConfiguration.defaultConfiguration();
    }

    /**
     * Specification of the behavior of the actual gameplay.
     * 
     * @return behavior definition
     */
    default Behavior getBehavior() {
        return Behavior.defaultBehavior();
    }
    
    /**
     * Default starting level. Only relevant for certain behaviors.
     * 
     * @return starting level
     */
    default int getStartLevel() {
        return 0;
    }
}
