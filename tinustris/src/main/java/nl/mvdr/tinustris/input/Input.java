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
    LEFT(Action.MOVE_LEFT, "Move left", "Shifts the tetromino one column to the left."),
    /** Moves the currently active block right. */
    RIGHT(Action.MOVE_RIGHT, "Move right", "Shifts the tetromino one column to the right."),
    /** Accelerates the currently active block's descent. */
    SOFT_DROP(Action.MOVE_DOWN, "Soft drop", "Accelerates the tetromino's descent."),
    /** Instantly drops the currently active block. */
    HARD_DROP(Action.HARD_DROP, "Hard drop", "Instantly drops the tetromino down."),
    /** Rotates the currently active block 90 degrees counter-clockwise. */
    TURN_LEFT(Action.TURN_LEFT, "Turn left", "Rotates the tetromino 90 degrees counter-clockwise."),
    /** Rotates the currently active block 90 degrees clockwise. */
    TURN_RIGHT(Action.TURN_RIGHT, "Turn right", "Rotates the tetromino 90 degrees clockwise."),
    /** Swaps the currently active block and the next block. */
    HOLD(Action.HOLD, "Hold", "Swaps the current tetromino with the next one.");
    
    /** Action to be taken when this input is pressed. */
    private final Action action;
    /** Nice name. */
    private final String name;
    /** Description of the effect of this input. */
    private final String description;
    
    /** {@inheritDoc} */
    @Override
    public String toString() {
        return name;
    }
}
