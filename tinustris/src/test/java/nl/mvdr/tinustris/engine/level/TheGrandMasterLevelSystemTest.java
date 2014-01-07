package nl.mvdr.tinustris.engine.level;

import nl.mvdr.tinustris.model.GameState;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test class for {@link TheGrandMasterLevelSystem}.
 * 
 * @author Martijn van de Rijdt
 */
public class TheGrandMasterLevelSystemTest extends LevelSystemTester {
    /** Tests {@link TheGrandMasterLevelSystem#computeLevel(GameState, GameState)}. */
    @Test
    public void test000000() {
        TheGrandMasterLevelSystem levelSystem = new TheGrandMasterLevelSystem();
        GameState state = createGameState(0, 0, 0);
        
        int level = levelSystem.computeLevel(state, state);
        
        Assert.assertEquals(0, level);
    }
    
    /** Tests {@link TheGrandMasterLevelSystem#computeLevel(GameState, GameState)}. */
    @Test
    public void test000100() {
        TheGrandMasterLevelSystem levelSystem = new TheGrandMasterLevelSystem();
        GameState previousState = createGameState(0, 0, 0);
        GameState newState = createGameState(1, 0, 0);
        
        int level = levelSystem.computeLevel(previousState, newState);
        
        Assert.assertEquals(1, level);
    }
    
    /** Tests {@link TheGrandMasterLevelSystem#computeLevel(GameState, GameState)}. */
    @Test
    public void test001101() {
        TheGrandMasterLevelSystem levelSystem = new TheGrandMasterLevelSystem();
        GameState previousState = createGameState(0, 0, 1);
        GameState newState = createGameState(1, 0, 1);
        
        int level = levelSystem.computeLevel(previousState, newState);
        
        Assert.assertEquals(2, level);
    }
    
    /** Tests {@link TheGrandMasterLevelSystem#computeLevel(GameState, GameState)}. */
    @Test
    public void test000010() {
        TheGrandMasterLevelSystem levelSystem = new TheGrandMasterLevelSystem();
        GameState previousState = createGameState(0, 0, 0);
        GameState newState = createGameState(0, 1, 0);
        
        int level = levelSystem.computeLevel(previousState, newState);
        
        Assert.assertEquals(1, level);
    }
    
    /** Tests {@link TheGrandMasterLevelSystem#computeLevel(GameState, GameState)}. */
    @Test
    public void test8571238610123() {
        TheGrandMasterLevelSystem levelSystem = new TheGrandMasterLevelSystem();
        GameState previousState = createGameState(85, 7, 123);
        GameState newState = createGameState(86, 10, 123);
        
        int level = levelSystem.computeLevel(previousState, newState);
        
        Assert.assertEquals(127, level);
    }
    
    /** Tests {@link TheGrandMasterLevelSystem#computeLevel(GameState, GameState)}. */
    @Test
    public void test103799104799() {
        TheGrandMasterLevelSystem levelSystem = new TheGrandMasterLevelSystem();
        GameState previousState = createGameState(103, 7, 99);
        GameState newState = createGameState(104, 7, 99);
        
        int level = levelSystem.computeLevel(previousState, newState);
        
        Assert.assertEquals(99, level);
    }
    
    /** Tests {@link TheGrandMasterLevelSystem#computeLevel(GameState, GameState)}. */
    @Test
    public void test10371991047199() {
        TheGrandMasterLevelSystem levelSystem = new TheGrandMasterLevelSystem();
        GameState previousState = createGameState(103, 7, 199);
        GameState newState = createGameState(104, 7, 199);
        
        int level = levelSystem.computeLevel(previousState, newState);
        
        Assert.assertEquals(199, level);
    }
    
    /** Tests {@link TheGrandMasterLevelSystem#computeLevel(GameState, GameState)}. */
    @Test
    public void test10371991048199() {
        TheGrandMasterLevelSystem levelSystem = new TheGrandMasterLevelSystem();
        GameState previousState = createGameState(103, 7, 199);
        GameState newState = createGameState(104, 8, 199);
        
        int level = levelSystem.computeLevel(previousState, newState);
        
        Assert.assertEquals(200, level);
    }
    
    /** {@inheritDoc} */
    @Override
    AbstractLevelSystem createLevelSystem() {
        return new TheGrandMasterLevelSystem();
    }
}
