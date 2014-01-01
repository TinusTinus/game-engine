package nl.mvdr.tinustris.engine.speedcurve;

import java.util.ArrayList;
import java.util.List;

import nl.mvdr.tinustris.engine.speedcurve.GameBoySpeedCurve;
import nl.mvdr.tinustris.input.InputStateHistory;
import nl.mvdr.tinustris.model.GameState;
import nl.mvdr.tinustris.model.Tetromino;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test class for {@link GameBoySpeedCurve}.
 * 
 * @author Martijn van de Rijdt
 */
public class GameBoySpeedCurveTest {
    /** Test case which tests {@link GameBoySpeedCurve#GameBoySpeedCurve())}. */
    @Test
    public void testConstructor() {
        new GameBoySpeedCurve();
    }
    
    /** Test case which tests {@link GameBoySpeedCurve#computeInternalGravity(nl.mvdr.tinustris.model.GameState)}. */
    @Test
    public void testLevel0() {
        testLevel(0, 5);
    }
    
    /** Test case which tests {@link GameBoySpeedCurve#computeInternalGravity(nl.mvdr.tinustris.model.GameState)}. */
    @Test
    public void testLevel1() {
        testLevel(1, 5);
    }
    
    /** Test case which tests {@link GameBoySpeedCurve#computeInternalGravity(nl.mvdr.tinustris.model.GameState)}. */
    @Test
    public void testLevel2() {
        testLevel(2, 6);
    }
    
    /** Test case which tests {@link GameBoySpeedCurve#computeInternalGravity(nl.mvdr.tinustris.model.GameState)}. */
    @Test
    public void testLevel3() {
        testLevel(3, 6);
    }
    
    /** Test case which tests {@link GameBoySpeedCurve#computeInternalGravity(nl.mvdr.tinustris.model.GameState)}. */
    @Test
    public void testLevel4() {
        testLevel(4, 7);
    }
    
    /** Test case which tests {@link GameBoySpeedCurve#computeInternalGravity(nl.mvdr.tinustris.model.GameState)}. */
    @Test
    public void testLevel5() {
        testLevel(5, 8);
    }
    
    /** Test case which tests {@link GameBoySpeedCurve#computeInternalGravity(nl.mvdr.tinustris.model.GameState)}. */
    @Test
    public void testLevel6() {
        testLevel(6, 9);
    }
    
    /** Test case which tests {@link GameBoySpeedCurve#computeInternalGravity(nl.mvdr.tinustris.model.GameState)}. */
    @Test
    public void testLevel7() {
        testLevel(7, 12);
    }
    
    /** Test case which tests {@link GameBoySpeedCurve#computeInternalGravity(nl.mvdr.tinustris.model.GameState)}. */
    @Test
    public void testLevel8() {
        testLevel(8, 15);
    }
    
    /** Test case which tests {@link GameBoySpeedCurve#computeInternalGravity(nl.mvdr.tinustris.model.GameState)}. */
    @Test
    public void testLevel9() {
        testLevel(9, 23);
    }
    
    /** Test case which tests {@link GameBoySpeedCurve#computeInternalGravity(nl.mvdr.tinustris.model.GameState)}. */
    @Test
    public void testLevel10() {
        testLevel(10, 26);
    }
    
    /** Test case which tests {@link GameBoySpeedCurve#computeInternalGravity(nl.mvdr.tinustris.model.GameState)}. */
    @Test
    public void testLevel11() {
        testLevel(11, 28);
    }
    
    /** Test case which tests {@link GameBoySpeedCurve#computeInternalGravity(nl.mvdr.tinustris.model.GameState)}. */
    @Test
    public void testLevel12() {
        testLevel(12, 32);
    }
    
    /** Test case which tests {@link GameBoySpeedCurve#computeInternalGravity(nl.mvdr.tinustris.model.GameState)}. */
    @Test
    public void testLevel13() {
        testLevel(13, 37);
    }
    
    /** Test case which tests {@link GameBoySpeedCurve#computeInternalGravity(nl.mvdr.tinustris.model.GameState)}. */
    @Test
    public void testLevel14() {
        testLevel(14, 43);
    }
    
    /** Test case which tests {@link GameBoySpeedCurve#computeInternalGravity(nl.mvdr.tinustris.model.GameState)}. */
    @Test
    public void testLevel15() {
        testLevel(15, 43);
    }
    
    /** Test case which tests {@link GameBoySpeedCurve#computeInternalGravity(nl.mvdr.tinustris.model.GameState)}. */
    @Test
    public void testLevel16() {
        testLevel(16, 51);
    }
    
    /** Test case which tests {@link GameBoySpeedCurve#computeInternalGravity(nl.mvdr.tinustris.model.GameState)}. */
    @Test
    public void testLevel17() {
        testLevel(17, 51);
    }
    
    /** Test case which tests {@link GameBoySpeedCurve#computeInternalGravity(nl.mvdr.tinustris.model.GameState)}. */
    @Test
    public void testLevel18() {
        testLevel(18, 64);
    }
    
    /** Test case which tests {@link GameBoySpeedCurve#computeInternalGravity(nl.mvdr.tinustris.model.GameState)}. */
    @Test
    public void testLevel19() {
        testLevel(19, 64);
    }
    
    /** Test case which tests {@link GameBoySpeedCurve#computeInternalGravity(nl.mvdr.tinustris.model.GameState)}. */
    @Test
    public void testLevel20() {
        testLevel(20, 85);
    }
    
    /** Test case which tests {@link GameBoySpeedCurve#computeInternalGravity(nl.mvdr.tinustris.model.GameState)}. */
    @Test
    public void testLevel21() {
        testLevel(21, 85);
    }
    
    /** Test case which tests {@link GameBoySpeedCurve#computeInternalGravity(nl.mvdr.tinustris.model.GameState)}. */
    @Test
    public void testLevel200() {
        testLevel(200, 85);
    }
    
    /** Test case which tests {@link GameBoySpeedCurve#computeInternalGravity(nl.mvdr.tinustris.model.GameState)}. */
    @Test
    public void testLevel1984() {
        testLevel(1984, 85);
    }
    
    /**
     * Checks whether the curve has the expected internal gravity for the given level.
     * 
     * @param level level
     * @param expectedInternalGravity expected internal gravity
     */
    private void testLevel(int level, int expectedInternalGravity) {
        GameBoySpeedCurve curve = new GameBoySpeedCurve();
        List<Tetromino> grid = new ArrayList<>(220);
        for (int i = 0; i != 220; i++) {
            grid.add(null);
        }
        GameState state = new GameState(grid, 10, null, null, null, Tetromino.Z, 0, 0, 0, new InputStateHistory(), 0,
                level * 10);
        
        Assert.assertEquals(expectedInternalGravity, curve.computeInternalGravity(state));
    }
}
