package nl.mvdr.tinustris.engine.level;

import java.util.ArrayList;
import java.util.List;

import nl.mvdr.tinustris.input.InputStateHistory;
import nl.mvdr.tinustris.model.Block;
import nl.mvdr.tinustris.model.GameState;
import nl.mvdr.tinustris.model.Tetromino;

import org.junit.Test;

/**
 * Abstract superclass for test cases for implementations of {@link LevelSystem}.
 * 
 * @author Martijn van de Rijdt
 */
public abstract class LevelSystemTester {
    /** Test method that symply invokes {@link AbstractLevelSystem#computeLevel(GameState, GameState)}. */
    @Test
    public void testSameState() {
        GameState state = createGameState(0, 0, 0);
        AbstractLevelSystem levelSystem = createLevelSystem();
        
        levelSystem.computeLevel(state, state);
    }
    
    /** Test method that symply invokes {@link AbstractLevelSystem#computeLevel(GameState, GameState)}. */
    @Test
    public void testDifferentStates() {
        GameState previousState = createGameState(0, 0, 0);
        GameState newState = createGameState(0, 0, 0);
        AbstractLevelSystem levelSystem = createLevelSystem();
        
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
    GameState createGameState(int blockCounter, int lines, int level) {
        List<Block> grid = new ArrayList<>(220);
        for (int i = 0; i != 220; i++) {
            grid.add(null);
        }
        GameState state = new GameState(grid, 10, null, null, null, Tetromino.Z, 0, 0, 0, new InputStateHistory(),
                blockCounter, lines, level);
        return state;
    }
    
    /**
     * Creates the level system to be tested.
     * 
     * @return level system
     */
    abstract AbstractLevelSystem createLevelSystem();
}
