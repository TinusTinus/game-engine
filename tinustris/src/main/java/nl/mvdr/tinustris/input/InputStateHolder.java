package nl.mvdr.tinustris.input;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.OptionalInt;
import java.util.stream.IntStream;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
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
        synchronized(states) {
            return states.entrySet()
                .stream()
                .filter(entry -> !frame.isPresent() || entry.getKey().intValue() <= frame.getAsInt())
                .max((left, right) -> Integer.compare(left.getKey().intValue(), right.getKey().intValue()))
                .map(Entry<Integer, InputState>::getValue)
                .orElse(input -> false);
        }
    }

    /**
     * Saves an additional input state.
     * 
     * @param frame frame / update index
     * @param state state
     * @return previous state value, or null if unavailable
     */
    public InputState putState(int frame, @NonNull InputState state) {
        synchronized(states) {
            return states.put(Integer.valueOf(frame), state);
        }
    }
    
    /**
     * Indicates whether this holder contains all input states for frames up to, but not including, the given frame
     * index.
     * 
     * @param frame
     *            frame index
     * @return whether all input states are known
     */
    public boolean allInputsKnownUntil(int frame) {
        synchronized(states) {
            return IntStream.range(0, frame)
                .boxed()
                .allMatch(states.keySet()::contains);
        }
    }
}    
