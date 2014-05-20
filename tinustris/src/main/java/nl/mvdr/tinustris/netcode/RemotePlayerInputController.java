package nl.mvdr.tinustris.netcode;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import nl.mvdr.tinustris.input.InputController;
import nl.mvdr.tinustris.input.InputState;

/**
 * Input controller for a remote player.
 * 
 * Note that this class is stateful.
 * 
 * @author Martijn van de Rijdt
 */
public class RemotePlayerInputController implements InputController {
    /** Map of frame index to the received input states. */
    private final Map<Integer, InputState> states;
    
    /** Constructor. */
    public RemotePlayerInputController() {
        super();
        this.states = new HashMap<>();
    }
    
    /**
     * {@inheritDoc}
     * 
     * Returns the latest known input state, or a default input state if none are known. This may (and probably will) be
     * an out of date one.
     */
    @Override
    public InputState getInputState() {
        return states.entrySet()
            .stream()
            .max((left, right) -> Integer.compare(left.getKey(), right.getKey()))
            .map(Entry<Integer, InputState>::getValue)
            .orElse(input -> false);
    }

    /** {@inheritDoc} */
    @Override
    public boolean isLocal() {
        return false;
    }
}
