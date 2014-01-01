package nl.mvdr.tinustris.engine.speedcurve;

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
}
