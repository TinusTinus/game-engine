package nl.mvdr.tinustris.engine.speedcurve;

import nl.mvdr.tinustris.model.OnePlayerGameState;

/**
 * Determines numeric speed values for a game of Tetris.
 * 
 * @author Martijn van de Rijdt
 */
public interface SpeedCurve {
    /**
     * Determines the internal gravity based on the given game state.
     * 
     * The internal gravity is expressed in 1 / 256 G, where G is the number of cells a tetromino drops per frame of
     * animation.
     * 
     * @param state
     *            game state
     * @return internal gravity in 1 / 256 G, must be positive
     */
    int computeInternalGravity(OnePlayerGameState state);
    
    /**
     * Determines the lock delay based on the given game state. The lock delay is expressed in frames.
     * 
     * @param state
     *            game state
     * @return lock delay in frames, must be 0 or more
     */
    int computeLockDelay(OnePlayerGameState state);
    
    /**
     * Determines the ARE (also known as entry delay, appearance delay or spawn delay) in frames.
     * 
     * @param state state
     * @return ARE in frames, must be 0 or more
     */
    int computeARE(OnePlayerGameState state);
    
    /**
     * Determines the line clear delay in frames.
     * 
     * @param state state
     * @return line clear delay in frames, must be positive
     */
    int computeLineClearDelay(OnePlayerGameState state);
    
    // TODO add delayed auto shift (DAS)?
}
