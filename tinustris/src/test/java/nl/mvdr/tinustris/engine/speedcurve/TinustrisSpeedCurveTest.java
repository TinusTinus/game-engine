package nl.mvdr.tinustris.engine.speedcurve;

import org.junit.Test;

/**
 * Test class for {@link TinustrisSpeedCurve}.
 * 
 * @author Martijn van de Rijdt
 */
public class TinustrisSpeedCurveTest extends SpeedCurveTester {
    /** {@inheritDoc} */
    @Override
    SpeedCurve createSpeedCurve() {
        return new TinustrisSpeedCurve();
    }
    
    /**
     * Test case which tests whether {@link TinustrisSpeedCurve#computeInternalGravity(nl.mvdr.tinustris.model.OnePlayerGameState)}
     * results in a 20G speed at the highest level.
     */
    @Test
    public void testMaxLevel() {
        testLevel(Integer.MAX_VALUE, 256 * 20);
    }
}
