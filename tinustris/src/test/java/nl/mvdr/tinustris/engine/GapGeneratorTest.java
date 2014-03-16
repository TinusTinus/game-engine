package nl.mvdr.tinustris.engine;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test class for {@link GapGenerator}.
 * 
 * @author Martijn van de Rijdt
 */
public class GapGeneratorTest {
    /** Test case for {@link GapGenerator#get(int)}. */
    @Test
    public void testGet() {
        GapGenerator generator = new GapGenerator(10);
        
        Integer value = generator.get(0);
        
        Assert.assertTrue(0 <= value.intValue());
        Assert.assertTrue(value.intValue() < 10);
    }
}
