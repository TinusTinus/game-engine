package nl.mvdr.tinustris.engine.speedcurve;

import java.util.HashMap;

import lombok.extern.slf4j.Slf4j;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test class for {@link RangedCurve}.
 * 
 * @author Martijn van de Rijdt
 */
@Slf4j
public class RangedCurveTest {
    /** Tests the {@link RangedCurve#toString()} method. */
    @Test
    public void testToString() {
        @SuppressWarnings("serial") // not to be serialised
        RangedCurve curve = new RangedCurve(new HashMap<Integer, Integer>(){{
            put(0, 5);
            put(4, 7);
            put(7, 23);
            put(8, 24);
        }});
        
        String string = curve.toString();
        
        Assert.assertNotNull(string);
        log.info(string);
    }
}
