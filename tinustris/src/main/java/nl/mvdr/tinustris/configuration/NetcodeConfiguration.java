package nl.mvdr.tinustris.configuration;

import java.util.Collections;
import java.util.List;

/**
 * Configuration parameters for the netcode.
 * 
 * @author Martijn van de Rijdt
 */
public interface NetcodeConfiguration {
    /** @return whether this game is a networked game */
    default boolean isNetworkedGame() {
        return !getRemotes().isEmpty();
    }
    
    /** @return configuration for each remote game instance */
    default List<RemoteConfiguration> getRemotes() {
        return Collections.emptyList();
    }
}
