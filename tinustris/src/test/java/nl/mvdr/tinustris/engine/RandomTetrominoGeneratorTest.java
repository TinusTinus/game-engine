package nl.mvdr.tinustris.engine;

import java.util.EnumSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import nl.mvdr.tinustris.model.Tetromino;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Test class for {@link RandomTetrominoGenerator}.
 * 
 * @author Martijn van de Rijdt
 */
public class RandomTetrominoGeneratorTest {
    /** Random seed. */
    private static final long SEED = 693741264L;
    /** The number of threads in the executor service used for concurrency tests. */
    int NUM_THREADS = 5;
    
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
    
    /** Tests whether generators with the same random seed also result in the same value. */
    @Test
    public void testRepeatable() {
        RandomTetrominoGenerator generator0 = new RandomTetrominoGenerator(SEED);
        RandomTetrominoGenerator generator1 = new RandomTetrominoGenerator(SEED);
        
        Tetromino tetromino0 = generator0.get(10_000);
        Tetromino tetromino1 = generator1.get(10_000);
        
        Assert.assertEquals(tetromino0, tetromino1);
    }

    /**
     * Test case where {@link RandomTetrominoGenerator#get(int)} is called from several different threads. We expect
     * each call to return the same tetromino.
     * 
     * @throws ExecutionException
     *             unexpected exception
     * @throws InterruptedException
     *             unexpected exception
     */
    @Test
    @Ignore // TODO make RandomTetrominoGenerator.get(int) threadsafe first!
    public void testGetMultiThreaded() throws ExecutionException, InterruptedException {
        // repeat the test a few times, to increase the likelyhood of failure in case of synchronisation bugs
        for (int i = 0; i != 100; i++) {
            RandomTetrominoGenerator generator = new RandomTetrominoGenerator();

            testMultithreaded(generator);
        }
    }
    
    /**
     * Tests whether generators with the same random seed also result in the same value, in a multithreaded environment.
     * @throws ExecutionException
     *             unexpected exception
     * @throws InterruptedException
     *             unexpected exception
     */
    @Test
    @Ignore // TODO make RandomTetrominoGenerator.get(int) threadsafe first!
    public void testRepeatableMultithreaded() throws InterruptedException, ExecutionException {
        // repeat the test a few times, to increase the likelyhood of failure in case of synchronisation bugs
        for (int i = 0; i != 100; i++){
            RandomTetrominoGenerator generator0 = new RandomTetrominoGenerator(SEED);
            RandomTetrominoGenerator generator1 = new RandomTetrominoGenerator(SEED);

            Tetromino tetromino0 = testMultithreaded(generator0);
            Tetromino tetromino1 = testMultithreaded(generator1);

            Assert.assertEquals("Failed at iteration " + 0, tetromino0, tetromino1);
        }
    }

    /**
     * Calls {@link RandomTetrominoGenerator#get(int)} with the same value from several different threads. Checks that
     * each thread results in the same tetromino value.
     * 
     * @param generator generator
     * @throws ExecutionException
     *             unexpected exception
     * @throws InterruptedException
     *             unexpected exception
     * @return tetromino value
     */
    private Tetromino testMultithreaded(final RandomTetrominoGenerator generator) throws InterruptedException,
            ExecutionException {
        Callable<Tetromino> getTetrominoTask = new Callable<Tetromino>() {
            /** {@inheritDoc} */
            @Override
            public Tetromino call() {
                return generator.get(10);
            }
        };
        List<Future<Tetromino>> futures = new LinkedList<>();
        ExecutorService service = Executors.newFixedThreadPool(NUM_THREADS);
        for (int i = 0; i != NUM_THREADS; i++) {
            futures.add(service.submit(getTetrominoTask));
        }
        
        // wait for all threads to complete and combine the results into a Set
        Set<Tetromino> results = EnumSet.noneOf(Tetromino.class);
        for (Future<Tetromino> future: futures) {
            Tetromino result = future.get();
            results.add(result);
        }
        
        // check that each thread resulted in the same value
        Assert.assertEquals("more than one unique tetromino returned", 1, results.size());
        
        // return the tetromino
        return results.iterator().next();
    }
}
