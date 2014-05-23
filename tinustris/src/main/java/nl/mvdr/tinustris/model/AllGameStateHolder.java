package nl.mvdr.tinustris.model;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Holder class that saves a history of all game states.
 * 
 * @param <S>
 *            game state type
 * @author Martijn van de Rijdt
 */
@ToString
@EqualsAndHashCode
public class AllGameStateHolder<S extends GameState> implements GameStateHolder<S> {
    /** List of states. */
    private final List<S> states;

    /** Constructor. */
    public AllGameStateHolder() {
        super();
        states = new ArrayList<>();
    }
    
    /**
     * {@inheritDoc}
     * 
     * The given state is inserted at the end of the list. It is assumed that the index in this list is equal to the
     * main game loop's update count, that is, the game loop update count starts at 0, is incremented by 1 and all game
     * states are passed into this method.
     */
    @Override
    public void addGameState(S state) {
        states.add(state);
    }

    /** {@inheritDoc} */
    @Override
    public S retrieveLatestGameState() {
        if (states.isEmpty()) {
            throw new NoSuchElementException("No state added yet.");
        }
        
        return states.get(states.size() - 1);
    }
}
