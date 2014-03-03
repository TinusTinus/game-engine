package nl.mvdr.tinustris.model;

/**
 * Marker interface for the state of the game.
 * 
 * @author Martijn van de Rijdt
 */
@FunctionalInterface
public interface GameState {
    /**
     * Indicates whether the game is over.
     * 
     * @return whether the game is over
     */
    boolean isGameOver();
}
