package nl.mvdr.tinustris.configuration;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import nl.mvdr.tinustris.gui.GraphicsStyle;

/**
 * Game configuration.
 * 
 * Sensible defaults have been included for all configuration properties, using default methods.
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
        // default configuration with an empty player name
        return Collections.singletonList(() -> "");
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
    
    /** @return configuration for networking */
    default NetcodeConfiguration getNetcodeConfiguration() {
        return Collections::emptyList;
    }
    
    /** @return random seed for the gap generator */
    default long getGapRandomSeed() {
        return new Random().nextLong();
    }
    
    /** @return random seed for the tetromino generator */
    default long getTetrominoRandomSeed() {
        return new Random().nextLong();
    }
}
