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
    LEFT(Action.MOVE_LEFT, "Move left"),
    /** Moves the currently active block right. */
    RIGHT(Action.MOVE_RIGHT, "Move right"),
    /** Accelerates the currently active block's descent. */
    SOFT_DROP(Action.MOVE_DOWN, "Soft drop"),
    /** Instantly drops the currently active block. */
    HARD_DROP(Action.HARD_DROP, "Hard drop"),
    /** Rotates the currently active block 90 degrees clockwise. */
    TURN_RIGHT(Action.TURN_RIGHT, "Turn right"),
    /** Rotates the currently active block 90 degrees counter-clockwise. */
    TURN_LEFT(Action.TURN_LEFT, "Turn left"),
    /** Swaps the currently active block and the next block. */
    HOLD(Action.HOLD, "Hold");
    
    /** Action to be taken when this input is pressed. */
    private final Action action;
    /** Nice name. */
    private final String name;
    
    /** {@inheritDoc} */
    @Override
    public String toString() {
        return name;
    }
}
