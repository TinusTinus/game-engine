package nl.mvdr.tinustris.input;

import java.util.Set;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * Serializable implementation of {@link InputState}.
 * 
 * @param <S> enum type containing all possible inputs from the user
 * 
 * @author Martijn van de Rijdt
 */
@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
class InputStateImpl<S extends Enum<S>> implements InputState<S> {
    /** Generated serial version UID. */
    private static final long serialVersionUID = 1L;
    /** Pressed inputs. */
    private final Set<S> pressedInputs;
    
    /** {@inheritDoc} */
    @Override
    public boolean isPressed(S input) {
        return pressedInputs.contains(input);
    }
}
