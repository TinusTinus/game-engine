package nl.mvdr.tinustris.engine.speedcurve;

/**
 * Test class for {@link NESSpeedCurve}.
 * 
 * @author Martijn van de Rijdt
 */
public class NESSpeedCurveTest extends SpeedCurveTester {
    /** {@inheritDoc} */
    @Override
    SpeedCurve createSpeedCurve() {
        return new NESSpeedCurve();
    }
}
