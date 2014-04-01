package nl.mvdr.tinustris.model;

/**
 * An action that can be performed in a game of Tetris.
 * 
 * Note that the actions are orderes in such a way that only the last three values are able to cause a tetromino to be
 * locked in place.
 * 
 * @author Martijn van de Rijdt
 */
public enum Action {
    /** Moves the currently active block left. */
    MOVE_LEFT,
    /** Moves the currently active block right. */
    MOVE_RIGHT,
    /** Moves the currently active block down. */
    GRAVITY_DROP,
    /** Rotates the currently active block 90 degrees clockwise. */
    TURN_RIGHT,
    /** Rotates the currently active block 90 degrees counter-clockwise. */
    TURN_LEFT,
    /** Swaps the currently active block and the next block. */
    HOLD,
    /** Moves the currently active block down, or locks it in place if that is impossible. */
    MOVE_DOWN,
    /** Locks the currently active block in place. */
    LOCK,
    /** Instantly drops the currently active block. */
    HARD_DROP;
}
