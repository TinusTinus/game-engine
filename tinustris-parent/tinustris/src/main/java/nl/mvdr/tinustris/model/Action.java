package nl.mvdr.tinustris.model;

/**
 * An action that can be performed in a game of Tetris.
 * 
 * @author Martijn van de Rijdt
 */
public enum Action {
    /** Moves the currently active block left. */
    MOVE_LEFT,
    /** Moves the currently active block right. */
    MOVE_RIGHT,
    /** Moves the currently active block down, or locks it in place if that is impossible. */
    MOVE_DOWN,
    /** Moves the currently active block down. */
    GRAVITY_DROP,
    /** Locks the currently active block in place. */
    LOCK,
    /** Instantly drops the currently active block. */
    HARD_DROP,
    /** Rotates the currently active block 90 degrees clockwise. */
    TURN_RIGHT,
    /** Rotates the currently active block 90 degrees counter-clockwise. */
    TURN_LEFT,
    /** Swaps the currently active block and the next block. */
    HOLD
}
