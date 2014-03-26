package nl.mvdr.tinustris.configuration;

import nl.mvdr.tinustris.input.JInputControllerConfiguration;

/**
 * Configuration for a player.
 * 
 * @author Martijn van de Rijdt
 */
@FunctionalInterface
public interface PlayerConfiguration {
    /** @return player name */
    String getName();
    
    /** @return configuration for JInputController for this player */
    default JInputControllerConfiguration getJInputControllerConfiguration() {
        return JInputControllerConfiguration.defaultConfiguration();
    }
}
