package nl.mvdr.tinustris.configuration;

import java.util.Collections;
import java.util.List;

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
     * Configuration for each of the players in this game. Should contain at least one value.
     * 
     * @return configurations
     */
    default List<PlayerConfiguration> getPlayerConfigurations() {
        return Collections.singletonList(() -> "");
    }
    
    /**
     * Number of players for this game. Must be at least 1.
     * 
     * @return number of players
     * @deprecated use the size of {@link #getPlayerConfigurations()}
     */
    default int getNumberOfPlayers() {
        return getPlayerConfigurations().size();
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
     * @deprecated use the values of {@link #getPlayerConfigurations()}
     */
    default JInputControllerConfiguration getJInputControllerConfiguration(int index) {
        return getPlayerConfigurations().get(index).getJInputControllerConfiguration();
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
