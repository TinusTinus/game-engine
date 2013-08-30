package nl.mvdr.tinustris.input;

/**
 * The different kinds of input the payer can give.
 * 
 * @author Martijn van de Rijdt
 */
public enum Input {
    /** Moves the currently active block left. */
    LEFT,
    /** Moves the currently active block right. */
    RIGHT,
    /** Accelerates the currently active block's descent. */
    FASTER_DROP,
    /** Instantly drops the currently active block. */
    INSTANT_DROP,
    /** Rotates the currently active block 90 degrees clockwise. */
    TURN_RIGHT,
    /** Rotates the currently active block 90 degrees counter-clockwise. */
    TURN_LEFT,
    /** Swaps the currently active block and the next block. */
    HOLD
}
