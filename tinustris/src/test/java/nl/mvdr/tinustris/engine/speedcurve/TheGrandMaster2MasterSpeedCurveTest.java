package nl.mvdr.tinustris.engine.speedcurve;

import org.junit.Test;

/**
 * Test class for {@link TheGrandMaster2MasterSpeedCurve}.
 * 
 * @author Martijn van de Rijdt
 */
public class TheGrandMaster2MasterSpeedCurveTest extends SpeedCurveTester {
    /** {@inheritDoc} */
    @Override
    SpeedCurve createSpeedCurve() {
        return new TheGrandMaster2MasterSpeedCurve();
    }
    
    /**
     * Test case which tests whether
     * {@link TheGrandMaster2MasterSpeedCurve#computeInternalGravity(nl.mvdr.tinustris.model.GameState)} results in a
     * 20G speed at the highest level.
     */
    @Test
    public void testMaxLevel() {
        testLevel(Integer.MAX_VALUE, 256 * 20);
    }
}
