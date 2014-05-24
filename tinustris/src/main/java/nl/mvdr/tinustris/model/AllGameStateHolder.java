package nl.mvdr.tinustris.model;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Consumer;
import java.util.stream.IntStream;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import nl.mvdr.tinustris.input.InputStateHolder;

/**
 * Holder class that saves a history of all game states.
 * 
 * @param <S>
 *            game state type
 * @author Martijn van de Rijdt
 */
@ToString
@EqualsAndHashCode
public class AllGameStateHolder<S extends GameState> implements GameStateHolder<S>, Consumer<FrameAndInputStatesContainer> {
    /** List of states. */
    private final List<S> states;
    /** List of input state holders. Index in the liust corresponds to the player number. */
    private final List<InputStateHolder> inputStateHolders;

    /** Constructor. */
    public AllGameStateHolder(List<InputStateHolder> inputStateHolders) {
        super();
        this.states = new ArrayList<>();
        this.inputStateHolders = inputStateHolders;
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

    /** {@inheritDoc} */
    @Override
    public void accept(FrameAndInputStatesContainer t) {
        IntStream.range(0, inputStateHolders.size())
            .filter(i -> inputStateHolders.get(i).isLocal())
            .filter(i -> t.getInputStates().keySet().contains(Integer.valueOf(i)))
            .forEach(i -> inputStateHolders.get(i).putState(t.getFrame(), t.getInputStates().get(Integer.valueOf(i))));
    }
}
