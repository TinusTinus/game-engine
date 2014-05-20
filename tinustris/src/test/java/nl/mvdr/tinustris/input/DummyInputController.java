package nl.mvdr.tinustris.input;

import lombok.RequiredArgsConstructor;

/**
 * Dummy implementation of {@link InputController}.
 * 
 * @author Martijn van de Rijdt
 */
@RequiredArgsConstructor
public class DummyInputController implements InputController {
    /** 
     * {@inheritDoc}
     * 
     * Dummy implementation which returns an input state where no input is pressed.
     */
    @Override
    public InputState getInputState() {
        return input -> false;
    }

    /** {@inheritDoc} */
    @Override
    public boolean isLocal() {
        return true;
    }
}
