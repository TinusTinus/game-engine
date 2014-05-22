package nl.mvdr.tinustris.model;

import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * Simple implementation of {@link GameStateHolder}, which stores a single state.
 * 
 * @param <S>
 *            game state type
 * 
 * @author Martijn van de Rijdt
 */
public class SingleGameStateHolder<S extends GameState> implements GameStateHolder<S> {
    /** State. */
    private Optional<S> gameState;

    /** Constructor. */
    public SingleGameStateHolder() {
        super();
        this.gameState = Optional.empty();
    }

    /** {@inheritDoc} */
    @Override
    public void addGameState(S state) {
        this.gameState = Optional.of(state);
    }

    /**
     * {@inheritDoc}
     * 
     * @throws NoSuchElementException
     *             if addGameState has not yet been called
     */
    @Override
    public S retrieveLatestGameState() {
        return gameState.get();
    }

}
