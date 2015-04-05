package nl.mvdr.tinustris.input;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * Dummy implementation of {@link InputController}.
 * 
 * @author Martijn van de Rijdt
 */
@RequiredArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class DummyInputController implements InputController {
    /** Whether this input controller simulates a local one. */
    private final boolean local;
    
    /** Default constructor. */
    public DummyInputController() {
        this(true);
    }
    
    /** 
     * {@inheritDoc}
     * 
     * Dummy implementation which returns an input state where no input is pressed.
     */
    @Override
    public InputState<Input> getInputState() {
        return input -> false;
    }
}
