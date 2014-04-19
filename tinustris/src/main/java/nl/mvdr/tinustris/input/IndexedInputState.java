package nl.mvdr.tinustris.input;

import java.io.Serializable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * Contains an input state, indexed by the frame number.
 * 
 * @author Martijn van de Rijdt
 */
@RequiredArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class IndexedInputState implements InputState, Serializable {
    /** Serial version UID. */
    private static final long serialVersionUID = 1L;

    /** The frame for this input state. */
    private final int frame;
    /** Input state. */
    @NonNull
    private final InputState inputState;

    /** {@inheritDoc} */
    @Override
    public boolean isPressed(Input input) {
        return inputState.isPressed(input);
    }
}
