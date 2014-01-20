package nl.mvdr.tinustris.engine.level;

import nl.mvdr.tinustris.model.OnePlayerGameState;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test class for {@link AbstractLevelSystem}.
 * 
 * @author Martijn van de Rijdt
 */
public class AbstractLevelSystemTest {
    /** Test method for {@link AbstractLevelSystem#fillLevel(OnePlayerGameState, OnePlayerGameState)}. */
    @Test
    public void testFillLevel0() {
        testFillLevel(0);
    }
    
    /** Test method for {@link AbstractLevelSystem#fillLevel(OnePlayerGameState, OnePlayerGameState)}. */
    @Test
    public void testFillLevel1() {
        testFillLevel(1);
    }
    
    /** Test method for {@link AbstractLevelSystem#fillLevel(OnePlayerGameState, OnePlayerGameState)}. */
    @Test
    public void testFillLevel120() {
        testFillLevel(120);
    }
    
    /**
     * Test method for {@link AbstractLevelSystem#fillLevel(OnePlayerGameState, OnePlayerGameState)}.
     * 
     * @param level level value
     */
    private void testFillLevel(int level) {
        DummyLevelSystem levelSystem = new DummyLevelSystem(level);
        
        OnePlayerGameState result = levelSystem.fillLevel(new OnePlayerGameState(), new OnePlayerGameState());
        
        Assert.assertEquals(level, result.getLevel());
    }
}
