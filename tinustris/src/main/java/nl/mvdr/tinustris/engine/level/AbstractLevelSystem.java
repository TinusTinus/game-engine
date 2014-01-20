package nl.mvdr.tinustris.engine.level;

import nl.mvdr.tinustris.model.OnePlayerGameState;

/**
 * Abstract superclass for implementations of {@link LevelSystem}.
 * 
 * @author Martijn van de Rijdt
 */
abstract class AbstractLevelSystem implements LevelSystem {
    /** {@inheritDoc} */
    @Override
    public OnePlayerGameState fillLevel(OnePlayerGameState previousState, OnePlayerGameState newState) {
        return newState.withLevel(computeLevel(previousState, newState));
    }

    /**
     * Computes the new level value.
     * 
     * @param previousState previous game state
     * @param newState next game state; all fields should be filled except for level
     * @return level value
     */
    abstract int computeLevel(OnePlayerGameState previousState, OnePlayerGameState newState);
}
