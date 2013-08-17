package nl.mvdr.tinustris.model;

import java.util.HashSet;
import java.util.List;
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
                
                List<Point> pointsList = tetromino.getPoints(orientation);
                Assert.assertEquals(4, pointsList.size());

                // copy into a set to eliminate duplicates
                Set<Point> points = new HashSet<>(pointsList);
                Assert.assertEquals(4, points.size());

                for (Point point : points) {
                    log.info("    Point: " + point);
                    
                    Assert.assertTrue("Tetromino: " + tetromino, 0 <= point.getX());
                    Assert.assertTrue("Tetromino: " + tetromino, point.getX() < 4);
                    Assert.assertTrue("Tetromino: " + tetromino, 0 <= point.getY());
                    Assert.assertTrue("Tetromino: " + tetromino, point.getY() < 4);
                }
            }
        }
    }
    
    /** Tests what happens when {@link Tetromino#getPoints(Orientation)} is invoked with a null value. */
    @Test(expected = NullPointerException.class)
    public void testGetPointsNull() {
        Tetromino.I.getPoints(null);
    }
}
