package nl.mvdr.tinustris.input;

import java.io.Serializable;

/**
 * The state of all inputs at a given moment in time.
 * 
 * @author Martijn van de Rijdt
 */
@FunctionalInterface
public interface InputState extends Serializable {

    /**
     * Indicates whether the given input is pressed.
     * 
     * @param input
     *            input
     * @return true if pressed, false if not
     */
    boolean isPressed(Input input);
}