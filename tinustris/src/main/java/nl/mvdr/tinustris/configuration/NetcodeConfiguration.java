package nl.mvdr.tinustris.configuration;

/**
 * Configuration parameters for the netcode.
 * 
 * @author Martijn van de Rijdt
 */
public interface NetcodeConfiguration {
    /** Port used for netplay. */
    // TODO make this a configuration option?
    static final int PORT = 5701;
    
    /** @return whether this game is a networked game */
    default boolean isNetworkedGame() {
        // TODO
        return false;
    }
}
