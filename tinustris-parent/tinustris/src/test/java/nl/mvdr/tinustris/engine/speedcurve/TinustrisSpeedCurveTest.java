package nl.mvdr.tinustris.engine.speedcurve;

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
}
