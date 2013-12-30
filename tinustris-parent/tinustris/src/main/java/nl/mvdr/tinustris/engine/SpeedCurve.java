package nl.mvdr.tinustris.engine;

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
    
    // TODO add ARE, DAS and line clear delay?
}
