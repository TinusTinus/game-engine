package nl.mvdr.tinustris.model;

import java.util.Set;

import lombok.extern.slf4j.Slf4j;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test class for {@link Tetromino}.
 * 
 * @author Martijn van de Rijdt
 */
@Slf4j
public class TetrominoTest {
    /**
     * Tests that each tetromino value contains a non-null points list which contains exactly four distinct points, with
     * all coordinates in the range [0, 4).
     */
    @Test
    public void testPoints() {
        for (Tetromino tetromino : Tetromino.values()) {
            log.info("Tetromino: " + tetromino);
            
            for (Orientation orientation : Orientation.values()) {
                log.info("  Orientation: " + orientation);
                
                Set<Point> points = tetromino.getPoints(orientation);
                Assert.assertEquals(4, points.size());

                for (Point point : points) {
                    log.info("    Point: " + point);
                    
                    Assert.assertTrue(0 <= point.getX());
                    Assert.assertTrue(point.getX() < 4);
                    Assert.assertTrue(0 <= point.getY());
                    Assert.assertTrue(point.getY() < 4);
                }
            }
        }
    }
    
    /** Tests what happens when {@link Tetromino#getPoints(Orientation)} is invoked with a null value. */
    @Test(expected = NullPointerException.class)
    public void testGetPointsNull() {
        Tetromino.I.getPoints(null);
    }
    
    /** Tests that the result of {@link Tetromino#getPoints(Orientation)} is unmodifiable. */
    @Test(expected = UnsupportedOperationException.class)
    public void testUnmodifiableSet() {
        Set<Point> points = Tetromino.J.getPoints(Orientation.getDefault());
        
        points.add(new Point(3, 3));
    }
}
