package nl.mvdr.tinustris.model;

import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Test class for {@link Tetromino}.
 * 
 * @author Martijn van de Rijdt
 */
@Ignore // TODO fix tests and Tetromino enum itself
public class TetrominoTest {
    /** Tests that each tetromino value contains a non-null points list which contains exactly four distinct points, with all coordinates in the range [0, 4). */
    @Test
    public void testPoints() {
        for (Tetromino tetromino: Tetromino.values()) {
            Assert.assertEquals("Tetromino: " + tetromino, 4, tetromino.getPoints().size());
            
            // copy into a set to eliminate duplicates
            Set<Point> points = new HashSet<>(tetromino.getPoints());
            Assert.assertEquals("Tetromino: " + tetromino, 4, points.size());
            
            for (Point point: points) {
                Assert.assertTrue("Tetromino: " + tetromino, 0 <= point.getX());
                Assert.assertTrue("Tetromino: " + tetromino, point.getX() < 4);
                Assert.assertTrue("Tetromino: " + tetromino, 0 <= point.getY());
                Assert.assertTrue("Tetromino: " + tetromino, point.getY() < 4);
            }
        }
    }
}
