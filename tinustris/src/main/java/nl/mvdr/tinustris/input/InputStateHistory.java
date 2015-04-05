package nl.mvdr.tinustris.input;

import java.util.EnumMap;
import java.util.Map;

/**
 * Keeps administration of the number of frames each input has been pressed.
 * 
 * @author Martijn van de Rijdt
 */
@FunctionalInterface
public interface InputStateHistory {
    /** Input state history where no inputs have been pressed. */
    public static final InputStateHistory NEW = input -> 0;

    /**
     * Retrieves the number of frames the given input has been pressed.
     * 
     * @param input
     *            input
     * @return number of frames
     */
    int getNumberOfFrames(Input input);

    /**
     * Creates the input state history for the next frame.
     * 
     * @param inputState
     *            input state for the next frame
     * @return new input state history
     */
    default InputStateHistory next(InputState<Input> inputState) {
        Map<Input, Integer> frames = new EnumMap<>(Input.class);
        for (Input input : Input.values()) {
            int value;
            if (inputState.isPressed(input)) {
                value = getNumberOfFrames(input) + 1;
            } else {
                value = 0;
            }
            frames.put(input, Integer.valueOf(value));
        }

        return frames::get;
    }
}