package nl.mvdr.tinustris.model;

/**
 * Game configuration.
 * 
 * Note that these options do not include graphical options. See the gui package for that.
 * 
 * Sensible defaults have been included for all methods, using default methods.
 * 
 * @author Martijn van de Rijdt
 */
public interface Configuration {
    
    /**
     * Number of players for this game. Must be at least 1.
     * 
     * @return number of players
     */
    default int getNumberOfPlayers() {
        return 1;
    }
    
    // TODO more configuration options
}
