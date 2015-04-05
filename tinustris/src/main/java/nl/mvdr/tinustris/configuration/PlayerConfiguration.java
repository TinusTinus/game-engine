package nl.mvdr.tinustris.configuration;

import nl.mvdr.tinustris.input.DefaultControllerConfiguration;
import nl.mvdr.tinustris.input.Input;
import nl.mvdr.tinustris.input.JInputControllerConfiguration;
import nl.mvdr.tinustris.input.NoSuitableControllerException;

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
    default JInputControllerConfiguration<Input> getJInputControllerConfiguration() {
        JInputControllerConfiguration<Input> result;
        try {
            result = DefaultControllerConfiguration.get();
        } catch (NoSuitableControllerException e) {
            throw new IllegalStateException(e);
        }
        return result;
    }
}
