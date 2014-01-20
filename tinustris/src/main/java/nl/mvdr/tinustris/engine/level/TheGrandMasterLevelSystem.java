package nl.mvdr.tinustris.engine.level;

import nl.mvdr.tinustris.model.OnePlayerGameState;

/**
 * Level system based on the Tetris: The Grand Master series.
 * 
 * @author Martijn van de Rijdt
 */
public class TheGrandMasterLevelSystem extends AbstractLevelSystem {
    /** {@inheritDoc} */
    @Override
    int computeLevel(OnePlayerGameState previousState, OnePlayerGameState newState) {
        int level = previousState.getLevel();
        
        if (previousState.getLevel() % 100 != 99) {
            level = level + newState.getBlockCounter() - previousState.getBlockCounter();
        }
        
        level = level + newState.getLines() - previousState.getLines();
        
        return level;
    }
}
