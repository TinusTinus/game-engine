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
    public abstract boolean isPressed(Input input);

}