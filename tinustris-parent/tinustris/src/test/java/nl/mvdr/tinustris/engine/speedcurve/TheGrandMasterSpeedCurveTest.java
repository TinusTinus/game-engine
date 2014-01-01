package nl.mvdr.tinustris.engine.speedcurve;

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
}
