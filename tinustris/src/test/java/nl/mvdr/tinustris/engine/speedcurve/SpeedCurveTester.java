package nl.mvdr.tinustris.engine.speedcurve;

import java.util.ArrayList;
import java.util.List;

import nl.mvdr.tinustris.input.InputStateHistoryImpl;
import nl.mvdr.tinustris.model.Block;
import nl.mvdr.tinustris.model.OnePlayerGameState;
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
    
    /** Test method for {@link SpeedCurve#computeInternalGravity(OnePlayerGameState)}. */
    @Test
    public void testInternalGravity() {
        SpeedCurve curve = createSpeedCurve();
        OnePlayerGameState state = new OnePlayerGameState();
        
        curve.computeInternalGravity(state);
    }
    
    /** Test method for {@link SpeedCurve#computeLockDelay(OnePlayerGameState)}. */
    @Test
    public void testInternalLockDelay() {
        SpeedCurve curve = createSpeedCurve();
        OnePlayerGameState state = new OnePlayerGameState();
        
        curve.computeLockDelay(state);
    }
    
    /** Test method for {@link SpeedCurve#computeARE(OnePlayerGameState)}. */
    @Test
    public void testInternalARE() {
        SpeedCurve curve = createSpeedCurve();
        OnePlayerGameState state = new OnePlayerGameState();
        
        curve.computeARE(state);
    }
    
    /** Test method for {@link SpeedCurve#computeLineClearDelay(OnePlayerGameState)}. */
    @Test
    public void testLineClear() {
        SpeedCurve curve = createSpeedCurve();
        OnePlayerGameState state = new OnePlayerGameState();
        
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
        List<Block> grid = new ArrayList<>(220);
        for (int i = 0; i != 220; i++) {
            grid.add(null);
        }
        OnePlayerGameState state = new OnePlayerGameState(grid, 10, null, null, null, Tetromino.Z, 0, 0, 0,
                InputStateHistoryImpl.NEW, 0, level * 10, level);
        
        Assert.assertEquals(expectedInternalGravity, curve.computeInternalGravity(state));
    }
    
    /** Instantiates the speed curve to be tested. */
    abstract SpeedCurve createSpeedCurve();
}
