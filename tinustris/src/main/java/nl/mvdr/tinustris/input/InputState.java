package nl.mvdr.tinustris.input;

/**
 * The state of all inputs at a given moment in time.
 * 
 * @author Martijn van de Rijdt
 */
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
    // Note: in Java 8 this could easily be turned into a default method.
    // The default implementation can just invoke isPressed for all values of Input.
    boolean anyInputsPressed();
}