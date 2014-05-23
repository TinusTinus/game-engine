package nl.mvdr.tinustris.input;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.function.Consumer;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import nl.mvdr.tinustris.model.FrameAndInputStatesContainer;

/**
 * Contains all known inputs for a given player up until this point.
 * 
 * @author Martijn van de Rijdt
 */
@ToString
@EqualsAndHashCode
public class InputStateHolder implements InputController, Consumer<FrameAndInputStatesContainer> {
    /** Whether these inputs are for a local player. */
    @Getter
    private final boolean local;
    /** Index of the player. */
    @Getter
    private final int playerNumber;
    
    /** Map of frame index to the received input states. */
    private final Map<Integer, InputState> states;
    
    /**
     * Constructor.
     * 
     * @param local
     *            whether these inputs are for a local player
     * @param playerNumber
     *            index of the player
     */
    public InputStateHolder(boolean local, int playerNumber) {
        super();
        this.states = new HashMap<>();
        this.local = local;
        this.playerNumber = playerNumber;
    }
    
    /**
     * {@inheritDoc}
     * 
     * Returns the latest known input state, or a default input state if none are known.
     */
    @Override
    public InputState getInputState() {
        return getInputState(Optional.empty());
    }
    
    /**
     * Returns the latest known input state at the given frame / update index, or a default input state if none are known.
     * 
     * @param frame frame / update index
     * @return input state
     */
    public InputState getInputState(int frame) {
        return getInputState(Optional.of(Integer.valueOf(frame)));
    }
    
    /**
     * Returns the latest known input state at the given frame / update index, or a default input state if none are known.
     * 
     * @param frame frame / update index; if empty, the very latest value is returned
     * @return input state
     */
    private InputState getInputState(Optional<Integer> frame) {
        return states.entrySet()
                .stream()
                .filter(entry -> !frame.isPresent() || entry.getKey().intValue() <= frame.get())
                .max((left, right) -> Integer.compare(left.getKey(), right.getKey()))
                .map(Entry<Integer, InputState>::getValue)
                .orElse(input -> false);
    }

    /** {@inheritDoc} */
    @Override
    public void accept(FrameAndInputStatesContainer t) {
        InputState state = t.getInputStates().get(Integer.valueOf(playerNumber));
        putState(t.getFrame(), state);
    }

    /**
     * Saves an additional input state.
     * 
     * @param frame frame / update index
     * @param state state
     * @return state
     */
    public InputState putState(int frame, InputState state) {
        return states.put(Integer.valueOf(frame), state);
    }
}    
