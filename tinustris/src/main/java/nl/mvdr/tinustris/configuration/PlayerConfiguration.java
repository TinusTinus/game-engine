package nl.mvdr.tinustris.configuration;

/**
 * Configuration for a player.
 * 
 * @author Martijn van de Rijdt
 */
@FunctionalInterface
public interface PlayerConfiguration {
    /** @return player name */
    String getName();
}
