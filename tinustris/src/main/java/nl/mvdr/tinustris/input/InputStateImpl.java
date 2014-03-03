package nl.mvdr.tinustris.input;

import java.util.Set;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * Implementation of {@link InputState}.
 * 
 * This class is not public. Use an {@link InputController} to obtain an instance of this class.
 * 
 * @author Martijn van de Rijdt
 */
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
@EqualsAndHashCode
@ToString
class InputStateImpl implements InputState {
    /** Set that contains all pressed inputs. */
    @NonNull
    private Set<Input> pressedInputs;
    
    /** {@inheritDoc} */
    @Override
    public boolean isPressed(Input input) {
        return pressedInputs.contains(input);
    }

    /** {@inheritDoc} */
    // overridden since isEmpty is usually O(1), whereas the default implementation is O(n)
    @Override
    public boolean anyInputsPressed() {
        return !pressedInputs.isEmpty();
    }
}
