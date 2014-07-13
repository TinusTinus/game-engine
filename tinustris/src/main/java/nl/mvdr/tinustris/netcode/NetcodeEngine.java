package nl.mvdr.tinustris.netcode;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.OptionalInt;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import nl.mvdr.tinustris.engine.GameEngine;
import nl.mvdr.tinustris.input.InputState;
import nl.mvdr.tinustris.input.InputStateHolder;
import nl.mvdr.tinustris.model.FrameAndInputStatesContainer;
import nl.mvdr.tinustris.model.GameState;
import nl.mvdr.tinustris.model.GameStateHolder;

/**
 * Main netcode engine.
 * 
 * Usage: make sure an instance of this class is registered with the main GameLoop as both the game state holder and a
 * local input state listener.
 * 
 * TODO improve the usage notes
 * 
 * @param <S>
 *            game state type
 * @author Martijn van de Rijdt
 */
@ToString
@EqualsAndHashCode
public class NetcodeEngine<S extends GameState> implements GameStateHolder<S>, Consumer<FrameAndInputStatesContainer> {
    /** List of states. */
    private final List<S> states;
    /** List of input state holders. Index in the list corresponds to the player number. */
    private final List<InputStateHolder> inputStateHolders;
    /** Game engine. Should be the same game engine used by the main game loop. */
    private final GameEngine<S> gameEngine;

    /**
     * Constructor.
     * 
     * @param inputStateHolders
     *            input state holders for each player; index in the list corresponds to the player number
     * @param gameEngine
     *            game engine: should be the same game engine used by the main game loop
     */
    public NetcodeEngine(List<InputStateHolder> inputStateHolders, GameEngine<S> gameEngine) {
        super();
        this.states = new ArrayList<>();
        this.inputStateHolders = inputStateHolders;
        this.gameEngine = gameEngine;
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
        // Note that states can only grow, never shrink. There is no need for addition synchronisation for this method.
        if (states.isEmpty()) {
            throw new NoSuchElementException("No state added yet.");
        }
        
        return states.get(states.size() - 1);
    }

    /** {@inheritDoc} */
    @Override
    public void accept(FrameAndInputStatesContainer t) {
        t.getInputStates().entrySet()
            .forEach(entry -> inputStateHolders.get(entry.getKey()).putState(t.getFrame(), entry.getValue()));
        
        // If this is an old input (that is to say, a remote input for an older frame):
        // recompute the game states after it based on the new input value.
        IntStream.range(t.getFrame() + 1, states.size())
            .forEach(frame -> {
                S previousState = states.get(frame - 1);
                List<InputState> inputStates = inputStateHolders.stream()
                    .map(holder -> holder.getInputState(frame - 1))
                    .collect(Collectors.toList());
                S newState = gameEngine.computeNextState(previousState, inputStates);
                states.set(frame, newState);
            });
    }
    
    /**
     * {@inheritDoc}
     * 
     * The game is only certainly over, if there is a game over state where all inputs leading up to that state are
     * known.
     */
    @Override
    public boolean isGameOver() {
        boolean result = retrieveLatestGameState().isGameOver();
        
        if (result) {
            // Check if all inputs are known, up until the first game over frame.
            
            // Note: if the following turns into a performance problem, it can also be implemented using a binary
            // search, or a linear search starting at the end, since all game over states should be at the end of the
            // list.
            OptionalInt frame = IntStream.range(0, states.size())
                    .filter(i -> states.get(i).isGameOver())
                    .findFirst();
            result = frame.isPresent() && inputStateHolders.stream().allMatch(holder -> holder.allInputsKnownUntil(frame.getAsInt()));    
        }
        
        return result;
    }
}
