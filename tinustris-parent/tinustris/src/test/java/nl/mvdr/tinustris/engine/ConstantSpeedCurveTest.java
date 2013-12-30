package nl.mvdr.tinustris.engine;

import nl.mvdr.tinustris.model.GameState;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test class for {@link ConstantSpeedCurve}. 
 * 
 * @author Martijn van de Rijdt
 */
public class ConstantSpeedCurveTest {
    /** Test case which tests {@link ConstantSpeedCurve#ConstantSpeedCurve(int, int)}. */
    @Test
    public void testConstructor() {
        new ConstantSpeedCurve(1, 2);
    }    
    
    /** Test case which calls {@link ConstantSpeedCurve#computeInternalGravity(nl.mvdr.tinustris.model.GameState)}. */
    @Test
    public void testGravity() {
        ConstantSpeedCurve curve = new ConstantSpeedCurve(1, 2);
        
        Assert.assertEquals(1, curve.computeInternalGravity(new GameState()));
    }
    
    /** Test case which calls {@link ConstantSpeedCurve#computeLockDelay(nl.mvdr.tinustris.model.GameState)}. */
    @Test
    public void testLockDelay() {
        ConstantSpeedCurve curve = new ConstantSpeedCurve(1, 2);
        
        Assert.assertEquals(2, curve.computeLockDelay(new GameState()));
    }
}