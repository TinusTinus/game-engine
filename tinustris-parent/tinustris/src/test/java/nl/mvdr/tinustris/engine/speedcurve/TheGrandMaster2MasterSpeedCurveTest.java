package nl.mvdr.tinustris.engine.speedcurve;

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
}
