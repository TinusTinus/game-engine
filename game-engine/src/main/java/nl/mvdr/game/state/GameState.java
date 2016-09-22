package nl.mvdr.game.state;

/**
 * Overall state of the game.
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
