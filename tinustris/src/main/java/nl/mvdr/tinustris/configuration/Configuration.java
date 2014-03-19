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
        return GraphicsStyle.TWO_DIMENSIONAL;
    }
    
    /**
     * Configuration for JInput, used by {@link nl.mvdr.tinustris.input.JInputController}.
     * 
     * @return input controller configuration
     */
    default JInputControllerConfiguration getJInputControllerConfiguration() {
        return JInputControllerConfiguration.defaultConfiguration();
    }
    
    // TODO more configuration options
}
