package nl.mvdr.tinustris.input;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

/**
 * Abstract superclass for {@link InputStateHistory} implementations.
 * 
 * @author Martijn van de Rijdt
 */
abstract class AbstractInputStateHistory implements InputStateHistory {
    /** {@inheritDoc} */
    @Override
    public InputStateHistory next(InputState inputState) {
        InputStateHistory result;

        if (inputState.anyInputsPressed()) {
            result = computeNext(inputState);
        } else {
            // no inputs pressed
            result = InputStateHistory.NEW;
        }
        
        return result;
    }
    
    /**
     * Computes the input state history for the next frame.
     * 
     * @param inputState input state for the next frame
     * @return new input state history
     */
    private InputStateHistory computeNext(InputState inputState) {
        InputStateHistory result;
        Map<Input, Integer> tempFrames = new EnumMap<>(Input.class);
        for (Input input : Input.values()) {
            int value;
            if (inputState.isPressed(input)) {
                value = getNumberOfFrames(input) + 1;
            } else {
                value = 0;
            }
            tempFrames.put(input, Integer.valueOf(value));
        }
        result = new InputStateHistoryImpl(Collections.unmodifiableMap(tempFrames));
        return result;
    }
}
