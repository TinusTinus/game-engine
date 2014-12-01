package nl.mvdr.tinustris.configuration;

import java.util.Optional;

import com.hazelcast.core.HazelcastInstance;

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
    
    /** @return Hazelcast instance */
    Optional<HazelcastInstance> getHazelcastInstance();
    
    /** @return whether this game is a networked game */
    default boolean isNetworkedGame() {
        return getHazelcastInstance().isPresent();
    }
}
