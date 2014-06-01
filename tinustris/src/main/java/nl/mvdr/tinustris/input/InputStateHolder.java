package nl.mvdr.tinustris.input;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.OptionalInt;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Contains all known inputs for a given player up until this point.
 * 
 * @author Martijn van de Rijdt
 */
@ToString
@EqualsAndHashCode
public class InputStateHolder implements InputController {
    /** Whether these inputs are for a local player. */
    @Getter
    private final boolean local;
    
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
    public InputStateHolder(boolean local) {
        super();
        this.states = new HashMap<>();
        this.local = local;
    }
    
    /**
     * {@inheritDoc}
     * 
     * Returns the latest known input state, or a default input state if none are known.
     */
    @Override
    public InputState getInputState() {
        return getInputState(OptionalInt.empty());
    }
    
    /**
     * Returns the latest known input state at the given frame / update index, or a default input state if none are known.
     * 
     * @param frame frame / update index
     * @return input state
     */
    public InputState getInputState(int frame) {
        return getInputState(OptionalInt.of(frame));
    }
    
    /**
     * Returns the latest known input state at the given frame / update index, or a default input state if none are known.
     * 
     * @param frame frame / update index; if empty, the very latest value is returned
     * @return input state
     */
    private InputState getInputState(OptionalInt frame) {
        return states.entrySet()
            .stream()
            .filter(entry -> !frame.isPresent() || entry.getKey().intValue() <= frame.getAsInt())
            .max((left, right) -> Integer.compare(left.getKey(), right.getKey()))
            .map(Entry<Integer, InputState>::getValue)
            .orElse(input -> false);
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
