package nl.mvdr.tinustris.engine.speedcurve;

import java.util.ArrayList;
import java.util.List;

import nl.mvdr.tinustris.input.InputStateHistory;
import nl.mvdr.tinustris.model.GameState;
import nl.mvdr.tinustris.model.Tetromino;

import org.junit.Assert;
import org.junit.Test;

/**
 * Abstract superclass for test for classes that implement {@link SpeedCurve}.
 * 
 * @author Martijn van de Rijdt
 */
public abstract class SpeedCurveTester {
    
    /** Tests the constructor. */
    @Test
    public void testConstruction() {
        createSpeedCurve();
    }
    
    /** Test method for {@link SpeedCurve#computeInternalGravity(GameState)}. */
    @Test
    public void testInternalGravity() {
        SpeedCurve curve = createSpeedCurve();
        GameState state = new GameState();
        
        curve.computeInternalGravity(state);
    }
    
    /** Test method for {@link SpeedCurve#computeLockDelay(GameState)}. */
    @Test
    public void testInternalLockDelay() {
        SpeedCurve curve = createSpeedCurve();
        GameState state = new GameState();
        
        curve.computeLockDelay(state);
    }
    
    /** Test method for {@link SpeedCurve#computeARE(GameState)}. */
    @Test
    public void testInternalARE() {
        SpeedCurve curve = createSpeedCurve();
        GameState state = new GameState();
        
        curve.computeARE(state);
    }
    
    /** Test method for {@link SpeedCurve#computeLineClearDelay(GameState)}. */
    @Test
    public void testLineClear() {
        SpeedCurve curve = createSpeedCurve();
        GameState state = new GameState();
        
        curve.computeLineClearDelay(state);
    }
    
    /**
     * Checks whether the curve has the expected internal gravity for the given level.
     * 
     * @param level level
     * @param expectedInternalGravity expected internal gravity
     */
    void testLevel(int level, int expectedInternalGravity) {
        SpeedCurve curve = createSpeedCurve();
        List<Tetromino> grid = new ArrayList<>(220);
        for (int i = 0; i != 220; i++) {
            grid.add(null);
        }
        GameState state = new GameState(grid, 10, null, null, null, Tetromino.Z, 0, 0, 0, new InputStateHistory(), 0,
                level * 10, level);
        
        Assert.assertEquals(expectedInternalGravity, curve.computeInternalGravity(state));
    }
    
    /** Instantiates the speed curve to be tested. */
    abstract SpeedCurve createSpeedCurve();
}
