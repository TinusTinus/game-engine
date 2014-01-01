package nl.mvdr.tinustris.engine.speedcurve;

import nl.mvdr.tinustris.model.GameState;

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
     * @return internal gravity in 1 / 256 G
     */
    int computeInternalGravity(GameState state);
    
    /**
     * Determines the lock delay based on the given game state. The lock delay is expressed in frames.
     * 
     * @param state
     *            game state
     * @return lock delay in frames
     */
    int computeLockDelay(GameState state);
    
    /**
     * Determines the ARE (also known as entry delay, appearance delay or spawn delay) in frames.
     * 
     * @param state state
     * @return ARE in frames
     */
    int computeARE(GameState state);
    
    /**
     * Determines the line clear delay in frames.
     * 
     * @param state state
     * @return line clear delay in frames
     */
    int computeLineClearDelay(GameState state);
    
    // TODO add delayed auto shift (DAS)?
}
