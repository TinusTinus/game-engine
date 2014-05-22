package nl.mvdr.tinustris.input;

import java.util.Set;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * Serializable implementation of {@link InputState}.
 * 
 * @author Martijn van de Rijdt
 */
@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
class InputStateImpl implements InputState {
    /** Generated serial version UID. */
    private static final long serialVersionUID = 1L;
    /** Pressed inputs. */
    private final Set<Input> pressedInputs;
    
    /** {@inheritDoc} */
    @Override
    public boolean isPressed(Input input) {
        return pressedInputs.contains(input);
    }
}
