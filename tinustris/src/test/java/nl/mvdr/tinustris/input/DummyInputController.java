package nl.mvdr.tinustris.input;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * Dummy implementation of {@link InputController}.
 * 
 * @param <S> enum type containing all possible inputs from the user
 * 
 * @author Martijn van de Rijdt
 */
@RequiredArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class DummyInputController<S extends Enum<S>> implements InputController<S> {
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
    public InputState<S> getInputState() {
        return input -> false;
    }
}
