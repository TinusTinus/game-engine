package nl.mvdr.tinustris.input;

import java.util.Arrays;

/**
 * The state of all inputs at a given moment in time.
 * 
 * @author Martijn van de Rijdt
 */
@FunctionalInterface
public interface InputState {

    /**
     * Indicates whether the given input is pressed.
     * 
     * @param input
     *            input
     * @return true if pressed, false if not
     */
    boolean isPressed(Input input);
    
    /**
     * Indicates whether any inputs are pressed.
     * 
     * @return true if at least one input is pressed, false otherwise
     */
    default boolean anyInputsPressed() {
        return Arrays.asList(Input.values())
                .stream()
                .anyMatch(this::isPressed);
    }
}