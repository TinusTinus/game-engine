package nl.mvdr.tinustris.engine.speedcurve;

import org.junit.Test;

/**
 * Test class for {@link TheGrandMaster2NormalSpeedCurve}.
 * 
 * @author Martijn van de Rijdt
 */
public class TheGrandMaster2NormalSpeedCurveTest extends SpeedCurveTester {
    /** {@inheritDoc} */
    @Override
    SpeedCurve createSpeedCurve() {
        return new TheGrandMaster2NormalSpeedCurve();
    }
    
    /**
     * Test case which tests whether
     * {@link TheGrandMaster2NormalSpeedCurve#computeInternalGravity(nl.mvdr.tinustris.model.OnePlayerGameState)} results in a
     * 20G speed at the highest level.
     */
    @Test
    public void testMaxLevel() {
        testLevel(Integer.MAX_VALUE, 256 * 20);
    }
}
