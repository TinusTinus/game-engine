package nl.mvdr.tinustris.engine.speedcurve;

import nl.mvdr.tinustris.engine.speedcurve.ConstantSpeedCurve;
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
        new ConstantSpeedCurve(1, 2, 3, 4);
    }
    
    /** Test case which tests {@link ConstantSpeedCurve#ConstantSpeedCurve()}. */
    @Test
    public void testDefaultConstructor() {
        new ConstantSpeedCurve();
    }   
    
    /** Test case which calls {@link ConstantSpeedCurve#computeInternalGravity(nl.mvdr.tinustris.model.GameState)}. */
    @Test
    public void testGravity() {
        ConstantSpeedCurve curve = new ConstantSpeedCurve(1, 2, 3, 4);
        
        Assert.assertEquals(1, curve.computeInternalGravity(new GameState()));
    }
    
    /** Test case which calls {@link ConstantSpeedCurve#computeLockDelay(nl.mvdr.tinustris.model.GameState)}. */
    @Test
    public void testLockDelay() {
        ConstantSpeedCurve curve = new ConstantSpeedCurve(1, 2, 3, 4);
        
        Assert.assertEquals(2, curve.computeLockDelay(new GameState()));
    }
    
    /** Test case which calls {@link ConstantSpeedCurve#computeARE(nl.mvdr.tinustris.model.GameState)}. */
    @Test
    public void testARE() {
        ConstantSpeedCurve curve = new ConstantSpeedCurve(1, 2, 3, 4);
        
        Assert.assertEquals(3, curve.computeARE(new GameState()));
    }
    
    /** Test case which calls {@link ConstantSpeedCurve#computeLockDelay(nl.mvdr.tinustris.model.GameState)}. */
    @Test
    public void testLineClear() {
        ConstantSpeedCurve curve = new ConstantSpeedCurve(1, 2, 3, 4);
        
        Assert.assertEquals(4, curve.computeLineClearDelay(new GameState()));
    }
}