package nl.mvdr.tinustris.input;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * Keeps administration of the number of frames each input has been pressed.
 * 
 * @author Martijn van de Rijdt
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
@ToString(includeFieldNames = false)
public class InputStateHistory {
    /** Input state history where no button has been pressed. */
    public static final InputStateHistory NEW = new InputStateHistory();
    
    /** Contains, per input, the number of frames it has been pressed. */
    @NonNull
    private final Map<Input, Integer> frames;
    
    /** Constructor. */
    private InputStateHistory() {
        Map<Input, Integer> tempFrames = new EnumMap<>(Input.class);
        for (Input input: Input.values()) {
            tempFrames.put(input, Integer.valueOf(0));
        }
        this.frames = Collections.unmodifiableMap(tempFrames);
    }
    
    /**
     * Creates the input state history for the next frame.
     * 
     * @param inputState input state for the next frame
     * @return new input state history
     */
    public InputStateHistory next(InputState inputState) {
        InputStateHistory result;

        if (inputState.anyInputsPressed()) {
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
            result = new InputStateHistory(Collections.unmodifiableMap(tempFrames));
        } else {
            // no inputs pressed
            result = NEW;
        }
        
        return result;
    }
    
    /**
     * Retrieves the number of frames the given input has been pressed.
     * 
     * @param input input
     * @return number of frames
     */
    public int getNumberOfFrames(Input input) {
        return this.frames.get(input).intValue();
    }
}