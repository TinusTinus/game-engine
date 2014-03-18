package nl.mvdr.tinustris.gui;

import lombok.extern.slf4j.Slf4j;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test class for {@link GraphicsStyle}.
 * 
 * @author Martijn van de Rijdt
 */
@Slf4j
public class GraphicsStyleTest {
    /** Test method for {@link GraphicsStyle#makeBlockCreator()}. */
    @Test
    public void test2DBlockCreator() {
        BlockCreator creator = GraphicsStyle.TWO_DIMENSIONAL.makeBlockCreator();
        
        Assert.assertNotNull(creator);
    }
    
    /** Test method for {@link GraphicsStyle#makeBlockCreator()}. */
    @Test
    public void test3DBlockCreator() {
        BlockCreator creator = GraphicsStyle.THREE_DIMENSIONAL.makeBlockCreator();
        
        Assert.assertNotNull(creator);
    }
    
    /** Test method for {@link GraphicsStyle#isAvailable()}. */
    @Test
    public void test2DIsAvailable() {
        boolean available = GraphicsStyle.TWO_DIMENSIONAL.isAvailable();
        
        Assert.assertTrue(available);
    }
    
    /** Test method for {@link GraphicsStyle#isAvailable()}. */
    @Test
    public void test3DIsAvailable() {
        boolean available = GraphicsStyle.TWO_DIMENSIONAL.isAvailable();
        
        log.info("3D available: " + available);
    }
    
    /** Tests the toString method. */
    @Test
    public void testToString() {
        for (GraphicsStyle style: GraphicsStyle.values()) {
            log.info(style.toString());
        }
    }
}
