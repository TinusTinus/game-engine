package nl.mvdr.tinustris.model;

import java.util.Map;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import nl.mvdr.tinustris.input.InputState;

/**
 * Contains a frame index, as well as a number of input states indexed by player number.
 * 
 * @author Martijn van de Rijdt
 */
@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public class FrameAndInputStatesContainer {
    /** Frame / update index. */
    private final int frame;
    /** Input states, indexed by player index. */
    private final Map<Integer, InputState> inputStates;
}
