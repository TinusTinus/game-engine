package nl.mvdr.tinustris.input;

import java.util.Collections;
import java.util.Set;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * Dummy implementation of {@link InputController}.
 * 
 * @author Martijn van de Rijdt
 */
@RequiredArgsConstructor
public class DummyInputController implements InputController {
    /** Pressed inputs. */
    @NonNull
    private final Set<Input> inputs;
    
    /** Constructor. */
    public DummyInputController() {
        this(Collections.<Input>emptySet());
    }
    
    /** 
     * {@inheritDoc}
     * 
     * Dummy implementation which returns an input state based on the value of the inputs field.
     */
    @Override
    public InputState getInputState() {
        return new InputStateImpl(inputs);
    }
}
