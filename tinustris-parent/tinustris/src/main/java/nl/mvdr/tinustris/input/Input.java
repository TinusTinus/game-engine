package nl.mvdr.tinustris.input;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import nl.mvdr.tinustris.model.Action;

/**
 * The different kinds of input the payer can give.
 * 
 * @author Martijn van de Rijdt
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum Input {
    /** Moves the currently active block left. */
    LEFT(Action.MOVE_LEFT),
    /** Moves the currently active block right. */
    RIGHT(Action.MOVE_RIGHT),
    /** Accelerates the currently active block's descent. */
    FASTER_DROP(Action.MOVE_DOWN),
    /** Instantly drops the currently active block. */
    INSTANT_DROP(Action.INSTANT_DROP),
    /** Rotates the currently active block 90 degrees clockwise. */
    TURN_RIGHT(Action.TURN_RIGHT),
    /** Rotates the currently active block 90 degrees counter-clockwise. */
    TURN_LEFT(Action.TURN_LEFT),
    /** Swaps the currently active block and the next block. */
    HOLD(Action.HOLD);
    
    /** Action to be taken when this input is pressed. */
    private final Action action;
}
