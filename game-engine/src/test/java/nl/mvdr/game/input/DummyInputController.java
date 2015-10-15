package nl.mvdr.game.input;

/**
 * Dummy implementation of {@link InputController}.
 * 
 * @param <S> enum type containing all possible inputs from the user
 * 
 * @author Martijn van de Rijdt
 */
public class DummyInputController<S extends Enum<S>> implements InputController<S> {
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
