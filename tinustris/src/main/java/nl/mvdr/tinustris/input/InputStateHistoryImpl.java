package nl.mvdr.tinustris.input;

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
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
@EqualsAndHashCode(callSuper = false) // super class holds no state
@ToString(includeFieldNames = false)
class InputStateHistoryImpl implements InputStateHistory {
    /** Contains, per input, the number of frames it has been pressed. */
    @NonNull
    private final Map<Input, Integer> frames;
    
    /** {@inheritDoc} */
    @Override
    public int getNumberOfFrames(Input input) {
        return this.frames.get(input).intValue();
    }
}