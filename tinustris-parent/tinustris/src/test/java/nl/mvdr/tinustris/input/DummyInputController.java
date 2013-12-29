package nl.mvdr.tinustris.input;

import java.util.Collections;

/**
 * Dummy implementation of {@link InputController}.
 * 
 * @author Martijn van de Rijdt
 */
public class DummyInputController implements InputController {
    /** 
     * {@inheritDoc}
     * 
     * Dummy implementation which always returns an input state without any pressed buttons.
     */
    @Override
    public InputState getInputState() {
        return new InputStateImpl(Collections.<Input>emptySet());
    }
}
