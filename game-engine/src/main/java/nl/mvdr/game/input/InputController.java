package nl.mvdr.game.input;

/**
 * Provides current input states.
 * 
 * @param <S>
 *            enum type containing all possible inputs from the user
 * 
 * @author Martijn van de Rijdt
 */
@FunctionalInterface
public interface InputController<S extends Enum<S>> {
    /**
     * Retrieves the current input state.
     * 
     * @return input state
     */
    InputState<S> getInputState();
}
