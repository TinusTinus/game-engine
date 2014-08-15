package nl.mvdr.tinustris.configuration;

import java.util.List;

/**
 * Configuration parameters for the netcode.
 * 
 * @author Martijn van de Rijdt
 */
@FunctionalInterface
public interface NetcodeConfiguration {
    /** Port used for netplay. */
    // TODO make this a configuration option?
    static final int PORT = 5701;
    
    /** @return configuration for each remote game instance */
    List<RemoteConfiguration> getRemotes();
    
    /** @return whether this game is a networked game */
    default boolean isNetworkedGame() {
        return !getRemotes().isEmpty();
    }
}
