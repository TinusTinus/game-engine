package nl.mvdr.tinustris.model;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test class for {@link Orientation}.
 * 
 * @author Martijn van de Rijdt
 */
public class OrientationTest {
    /** Tests the retrieval of the next orientation when rotating a tetromino. */
    @Test
    public void testGetNextClockwiseFlatDown() {
        Assert.assertEquals(Orientation.FLAT_LEFT, Orientation.FLAT_DOWN.getNextClockwise());
    }
    
    /** Tests the retrieval of the next orientation when rotating a tetromino. */
    @Test
    public void testGetNextClockwiseFlatLeft() {
        Assert.assertEquals(Orientation.FLAT_UP, Orientation.FLAT_LEFT.getNextClockwise());
    }
    
    /** Tests the retrieval of the next orientation when rotating a tetromino. */
    @Test
    public void testGetNextClockwiseFlatUp() {
        Assert.assertEquals(Orientation.FLAT_RIGHT, Orientation.FLAT_UP.getNextClockwise());
    }
    
    /** Tests the retrieval of the next orientation when rotating a tetromino. */
    @Test
    public void testGetNextClockwiseFlatRight() {
        Assert.assertEquals(Orientation.FLAT_DOWN, Orientation.FLAT_RIGHT.getNextClockwise());
    }
    
    /** Tests the retrieval of the next orientation when rotating a tetromino. */
    @Test
    public void testGetNextCounterClockwiseFlatDown() {
        Assert.assertEquals(Orientation.FLAT_RIGHT, Orientation.FLAT_DOWN.getNextCounterClockwise());
    }
    
    /** Tests the retrieval of the next orientation when rotating a tetromino. */
    @Test
    public void testGetNextCounterClockwiseFlatRight() {
        Assert.assertEquals(Orientation.FLAT_UP, Orientation.FLAT_RIGHT.getNextCounterClockwise());
    }
    
    /** Tests the retrieval of the next orientation when rotating a tetromino. */
    @Test
    public void testGetNextCounterClockwiseFlatUp() {
        Assert.assertEquals(Orientation.FLAT_LEFT, Orientation.FLAT_UP.getNextCounterClockwise());
    }
    
    /** Tests the retrieval of the next orientation when rotating a tetromino. */
    @Test
    public void testGetNextCounterClockwiseFlatLeft() {
        Assert.assertEquals(Orientation.FLAT_DOWN, Orientation.FLAT_LEFT.getNextCounterClockwise());
    }
}
