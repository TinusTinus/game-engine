package nl.mvdr.tinustris.engine;

import nl.mvdr.tinustris.model.Tetromino;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test class for {@link RandomTetrominoGenerator}.
 * 
 * @author Martijn van de Rijdt
 */
public class RandomTetrominoGeneratorTest {
    /** Invokes the get method a few times and checks that it returns non-null values. */
    @Test
    public void testGet() {
        TetrominoGenerator generator = new RandomTetrominoGenerator();
        for (int i = 0; i != 10; i++) {
            Assert.assertNotNull("failed on i = " + i, generator.get(i));
        }
    }
    
    /** Tests that the get method returns the same value when invoked with the same index multiple times. */
    @Test
    public void testGetMultipleCalls() {
        TetrominoGenerator generator = new RandomTetrominoGenerator();
        Tetromino first = generator.get(0);
        
        Assert.assertEquals(first, generator.get(0));
    }
    
    /**
     * Tests that the get method returns the same value when invoked with the same index multiple times, even when other
     * indices have been used as well.
     */
    @Test
    public void testGetMultipleCallsLater() {
        TetrominoGenerator generator = new RandomTetrominoGenerator();
        Tetromino first = generator.get(0);
        generator.get(1);
        generator.get(2);
        
        Assert.assertEquals(first, generator.get(0));
    }
    
    /**
     * Tests that the get method works correctly when skipping a tetromino index (even though this should never happen
     * in practice).
     */
    @Test
    public void testGetSkipValue() {
        TetrominoGenerator generator = new RandomTetrominoGenerator();
        Assert.assertNotNull(generator.get(1));
    }
    
    /**
     * Tests that the get method works correctly when skipping a tetromino index (even though this should never happen
     * in practice).
     */
    @Test
    public void testGetSkipValueMultipleTimes() {
        TetrominoGenerator generator = new RandomTetrominoGenerator();
        Tetromino first = generator.get(1);
        
        Assert.assertEquals(first, generator.get(1));
    }
    
    /** Test case with a negative index. */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testNegativeValue() {
        TetrominoGenerator generator = new RandomTetrominoGenerator();
        generator.get(-1);
    }
    
    /** Tests that reusing the same random seed produces the same results. */
    @Test
    public void testRandomSeed() {
        TetrominoGenerator generator = new RandomTetrominoGenerator(0L);
        Tetromino first = generator.get(0);
        generator = new RandomTetrominoGenerator(0L);
        
        Assert.assertEquals(first, generator.get(0));
    }
    
    /** Tests that reusing the same random seed in a new JVM instance still produces the same result. */
    @Test
    public void testRandomSeedFixed() {
        TetrominoGenerator generator = new RandomTetrominoGenerator(0L);
        
        Assert.assertEquals(Tetromino.S, generator.get(0));
        Assert.assertEquals(Tetromino.T, generator.get(1));
        Assert.assertEquals(Tetromino.L, generator.get(2));
        Assert.assertEquals(Tetromino.T, generator.get(3));
        Assert.assertEquals(Tetromino.L, generator.get(4));
        Assert.assertEquals(Tetromino.I, generator.get(5));
        Assert.assertEquals(Tetromino.T, generator.get(6));
        Assert.assertEquals(Tetromino.O, generator.get(7));
        Assert.assertEquals(Tetromino.Z, generator.get(8));
        Assert.assertEquals(Tetromino.T, generator.get(9));
    }
}
