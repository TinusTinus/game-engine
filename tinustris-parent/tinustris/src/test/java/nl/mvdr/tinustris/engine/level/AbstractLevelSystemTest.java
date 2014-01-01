package nl.mvdr.tinustris.engine.level;

import nl.mvdr.tinustris.model.GameState;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test class for {@link AbstractLevelSystem}.
 * 
 * @author Martijn van de Rijdt
 */
public class AbstractLevelSystemTest {
    /** Test method for {@link AbstractLevelSystem#fillLevel(GameState, GameState)}. */
    @Test
    public void testFillLevel0() {
        testFillLevel(0);
    }
    
    /** Test method for {@link AbstractLevelSystem#fillLevel(GameState, GameState)}. */
    @Test
    public void testFillLevel1() {
        testFillLevel(1);
    }
    
    /** Test method for {@link AbstractLevelSystem#fillLevel(GameState, GameState)}. */
    @Test
    public void testFillLevel120() {
        testFillLevel(120);
    }
    
    /**
     * Test method for {@link AbstractLevelSystem#fillLevel(GameState, GameState)}.
     * 
     * @param level level value
     */
    private void testFillLevel(int level) {
        DummyLevelSystem levelSystem = new DummyLevelSystem(level);
        
        GameState result = levelSystem.fillLevel(new GameState(), new GameState());
        
        Assert.assertEquals(level, result.getLevel());
    }
}
