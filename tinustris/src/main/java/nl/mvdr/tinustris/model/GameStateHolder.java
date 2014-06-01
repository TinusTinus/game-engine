package nl.mvdr.tinustris.model;

/**
 * Holds one or more game state values.
 * 
 * @param <S>
 *            game state type
 * 
 * @author Martijn van de Rijdt
 */
public interface GameStateHolder<S extends GameState> {
    /**
     * Adds a game state.
     * 
     * @param state
     *            state to be held
     */
    void addGameState(S state);

    /**
     * @return the latest game state; not guaranteed to be the same one as passed into the last call of addGameState
     * 
     * @throws java.util.NoSuchElementException
     *             if addGameState has not yet been called
     */
    S retrieveLatestGameState();
    
    /** @return whether the game is definitely over */
    default boolean isGameOver() {
        return retrieveLatestGameState().isGameOver();
    }
}
