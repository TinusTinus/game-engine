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
    
    // TODO more configuration options
}
