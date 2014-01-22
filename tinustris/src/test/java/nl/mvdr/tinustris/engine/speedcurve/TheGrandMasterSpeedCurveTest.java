package nl.mvdr.tinustris.engine.speedcurve;

import org.junit.Test;

/**
 * Test class for {@link TheGrandMasterSpeedCurve}.
 * 
 * @author Martijn van de Rijdt
 */
public class TheGrandMasterSpeedCurveTest extends SpeedCurveTester {
    /** {@inheritDoc} */
    @Override
    SpeedCurve createSpeedCurve() {
        return new TheGrandMasterSpeedCurve();
    }
    
    /**
     * Test case which tests whether
     * {@link TheGrandMasterSpeedCurve#computeInternalGravity(OnePlayerGameState)} results in a
     * 20G speed at the highest level.
     */
    @Test
    public void testMaxLevel() {
        testLevel(Integer.MAX_VALUE, 256 * 20);
    }
}
