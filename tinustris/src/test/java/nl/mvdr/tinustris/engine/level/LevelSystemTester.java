package nl.mvdr.tinustris.engine.level;

import java.util.ArrayList;
import java.util.List;

import nl.mvdr.tinustris.input.InputStateHistory;
import nl.mvdr.tinustris.model.Block;
import nl.mvdr.tinustris.model.OnePlayerGameState;
import nl.mvdr.tinustris.model.Tetromino;

import org.junit.Test;

/**
 * Abstract superclass for test cases for implementations of {@link LevelSystem}.
 * 
 * @author Martijn van de Rijdt
 */
public abstract class LevelSystemTester {
    /** Test method that simply invokes {@link LevelSystem#computeLevel(OnePlayerGameState, OnePlayerGameState)}. */
    @Test
    public void testSameState() {
        OnePlayerGameState state = createGameState(0, 0, 0);
        LevelSystem levelSystem = createLevelSystem();
        
        levelSystem.computeLevel(state, state);
    }
    
    /** Test method that simply invokes {@link LevelSystem#computeLevel(OnePlayerGameState, OnePlayerGameState)}. */
    @Test
    public void testDifferentStates() {
        OnePlayerGameState previousState = createGameState(0, 0, 0);
        OnePlayerGameState newState = createGameState(0, 0, 0);
        LevelSystem levelSystem = createLevelSystem();
        
        levelSystem.computeLevel(previousState, newState);
    }
    
    /**
     * Creates a game state with the given parameters.
     * 
     * @param blockCounter block counter
     * @param lines lines
     * @param level level
     * @return game state
     */
    OnePlayerGameState createGameState(int blockCounter, int lines, int level) {
        List<Block> grid = new ArrayList<>(220);
        for (int i = 0; i != 220; i++) {
            grid.add(null);
        }
        OnePlayerGameState state = new OnePlayerGameState(grid, 10, null, null, null, Tetromino.Z, 0, 0, 0,
                InputStateHistory.NEW, blockCounter, lines, level);
        return state;
    }
    
    /**
     * Creates the level system to be tested.
     * 
     * @return level system
     */
    abstract LevelSystem createLevelSystem();
}
