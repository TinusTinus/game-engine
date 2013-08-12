package nl.mvdr.tinustris.input;

import java.util.Set;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * Implementation of {@link InputState}.
 * 
 * @author Martijn van de Rijdt
 */
@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
public class InputStateImpl implements InputState {
    /** Set that contains all pressed inputs. */
    @NonNull
    private Set<Input> pressedInputs;
    
    /** {@inheritDoc} */
    @Override
    public boolean isPressed(Input input) {
        return pressedInputs.contains(input);
    }
}
