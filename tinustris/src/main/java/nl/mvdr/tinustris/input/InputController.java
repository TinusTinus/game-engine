package nl.mvdr.tinustris.input;

/**
 * Provides current input states.
 * 
 * @param <S> enum type containing all possible inputs from the user
 * 
 * @author Martijn van de Rijdt
 */
public interface InputController<S extends Enum<S>> {
    /**
     * Retrieves the current input state.
     * 
     * @return input state
     */
    InputState<S> getInputState();

    /** @return whether this input controller is local */
    boolean isLocal();
}
