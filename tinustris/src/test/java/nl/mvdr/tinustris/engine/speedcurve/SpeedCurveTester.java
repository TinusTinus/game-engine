package nl.mvdr.tinustris.engine.speedcurve;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import nl.mvdr.tinustris.input.InputStateHistory;
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
    
    /** Test with a negative level value. */
    @Test(expected = NoSuchElementException.class)
    public void testNegativeValue() {
        SpeedCurve curve = createSpeedCurve();
        OnePlayerGameState state = createGameState(-1);
        
        curve.computeInternalGravity(state);
    }
    
    /**
     * Checks whether the curve has the expected internal gravity for the given level.
     * 
     * @param level level
     * @param expectedInternalGravity expected internal gravity
     */
    void testLevel(int level, int expectedInternalGravity) {
        SpeedCurve curve = createSpeedCurve();
        OnePlayerGameState state = createGameState(level);
        
        Assert.assertEquals(expectedInternalGravity, curve.computeInternalGravity(state));
    }

    /**
     * Creates a dummy game state with the given level value.
     * 
     * @param level level
     * @return state
     */
    private OnePlayerGameState createGameState(int level) {
        List<Optional<Block>> grid = Collections.nCopies(220, Optional.empty());
        OnePlayerGameState state = new OnePlayerGameState(grid, 10, Optional.empty(), Optional.empty(),
                Optional.empty(), Tetromino.Z, 0, 0, 0, InputStateHistory.NEW, 0, level * 10, 0, level, 0, 0);
        return state;
    }
    
    /** Instantiates the speed curve to be tested. */
    abstract SpeedCurve createSpeedCurve();
}
