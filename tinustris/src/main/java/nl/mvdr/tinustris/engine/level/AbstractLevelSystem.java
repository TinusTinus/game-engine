package nl.mvdr.tinustris.engine.level;

import nl.mvdr.tinustris.model.GameState;

/**
 * Abstract superclass for implementations of {@link LevelSystem}.
 * 
 * @author Martijn van de Rijdt
 */
abstract class AbstractLevelSystem implements LevelSystem {
    /** {@inheritDoc} */
    @Override
    public GameState fillLevel(GameState previousState, GameState newState) {
        int level = computeLevel(previousState, newState);
        return new GameState(newState.getGrid(), newState.getWidth(), newState.getActiveTetromino(),
                newState.getCurrentBlockLocation(), newState.getCurrentBlockOrientation(), newState.getNextBlock(),
                newState.getNumFramesSinceLastDownMove(), newState.getNumFramesSinceLastLock(),
                newState.getNumFramesSinceLastMove(), newState.getInputStateHistory(), newState.getBlockCounter(),
                newState.getLines(), newState.getNumFramesUntilLinesDisappear(), level);
    }

    /**
     * Computes the new level value.
     * 
     * @param previousState previous game state
     * @param newState next game state; all fields should be filled except for level
     * @return level value
     */
    abstract int computeLevel(GameState previousState, GameState newState);
}
