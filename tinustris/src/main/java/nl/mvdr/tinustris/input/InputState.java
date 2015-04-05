package nl.mvdr.tinustris.input;

import java.io.Serializable;

/**
 * The state of all inputs at a given moment in time.
 * 
 * @param <S> enum type containing all possible inputs from the user
 * 
 * @author Martijn van de Rijdt
 */
@FunctionalInterface
public interface InputState<S extends Enum<S>> extends Serializable {

    /**
     * Indicates whether the given input is pressed.
     * 
     * @param input
     *            input
     * @return true if pressed, false if not
     */
    boolean isPressed(S input);
}