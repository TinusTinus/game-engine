package nl.mvdr.tinustris.configuration;

/**
 * Configuration parameters for the netcode.
 * 
 * @author Martijn van de Rijdt
 */
public interface NetcodeConfiguration {
    /** @return whether this game is a networked game */
    default boolean isNetworkedGame() {
        return false;
    }
}
