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
    // TODO choose a more appropriate port? see http://en.wikipedia.org/wiki/List_of_TCP_and_UDP_port_numbers
    public static final int PORT = 8082;
    
    /** @return configuration for each remote game instance */
    List<RemoteConfiguration> getRemotes();
    
    /** @return whether this game is a networked game */
    default boolean isNetworkedGame() {
        return !getRemotes().isEmpty();
    }
}
