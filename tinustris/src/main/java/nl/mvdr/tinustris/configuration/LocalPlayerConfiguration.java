package nl.mvdr.tinustris.configuration;

import nl.mvdr.tinustris.input.JInputControllerConfiguration;
import nl.mvdr.tinustris.input.NoSuitableControllerException;

/**
 * Configuration for a local player.
 * 
 * @author Martijn van de Rijdt
 */
@FunctionalInterface
public interface LocalPlayerConfiguration extends PlayerConfiguration {
    /** @return configuration for JInputController for this player */
    // TODO move to LocalPlayerConfiguration
    default JInputControllerConfiguration getJInputControllerConfiguration() {
        JInputControllerConfiguration result;
        try {
            result = JInputControllerConfiguration.defaultConfiguration();
        } catch (NoSuitableControllerException e) {
            throw new IllegalStateException(e);
        }
        return result;
    }
}
