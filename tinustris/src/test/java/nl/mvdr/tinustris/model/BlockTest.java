package nl.mvdr.tinustris.model;

import lombok.extern.slf4j.Slf4j;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test class for {@link Block}.
 * 
 * @author Martijn van de Rijdt
 */
@Slf4j
public class BlockTest {
    /** Invokes the toString method on all values (and logs the result). */
    @Test
    public void testToString() {
        for (Block block: Block.values()) {
            String string = block.toString();
            
            log.info(string);
            Assert.assertNotNull(string);
            Assert.assertNotEquals("", string);
        }
    }
}
