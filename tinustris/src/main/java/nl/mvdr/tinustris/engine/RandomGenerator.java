package nl.mvdr.tinustris.engine;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.IntFunction;

import lombok.extern.slf4j.Slf4j;

/**
 * Implementation of {@link Generator} that randomly determines new values, and uses a List to store past ones.
 * 
 * This implementation uses {@link Random} to randomly choose an int value, then uses the provided function to transform
 * the int value into an object of the desired type.
 * 
 * @param <S> value type
 * 
 * @author Martijn van de Rijdt
 */
@Slf4j
abstract class RandomGenerator<S> implements Generator<S> {
    /** Random number generator. */
    private final Random random;
    /** List of values returned so far. */
    // Note that this list can only grow, never shrink, which is a potential memory leak.
    // In practice games are not expected to go on long enough for this to be a problem.
    // If that assumption is wrong, this list will probably need to be converted to a linked list where the first part
    // is cleared when it is no longer required.
    private final List<S> values;
    /** Maximum int value used as input for {@link Random#nextInt()}. */
    private final int maxRandomValue;
    /** Function to map an integer value as determined by {@link Random#nextInt()} into an actual value. */
    private final IntFunction<S> mapping;
    /** Name for the value type. Used only for logging. */
    private final String valueName;
    
    /**
     * Constructor.
     * 
     * @param maxRandomValue
     *            maximum int value used as input for {@link Random#nextInt()}
     * @param mapping
     *            function to map an integer value as determined by {@link Random#nextInt()} into an actual value
     * @param valueName
     *            name for the value type; used only for logging
     */
    RandomGenerator(int maxRandomValue, IntFunction<S> mapping, String valueName) {
        super();
        
        this.random = new Random();
        this.values = new ArrayList<>();
        
        this.maxRandomValue = maxRandomValue;
        this.mapping = mapping;
        this.valueName = valueName;
    }

    /**
     * Constructor.
     * 
     * @param randomSeed
     *            random seed
     * @param maxRandomValue
     *            maximum int value used as input for {@link Random#nextInt()}
     * @param mapping
     *            function to map an integer value as determined by {@link Random#nextInt()} into an actual value
     * @param valueName
     *            name for the value type; used only for logging
     */
    RandomGenerator(long randomSeed, int maxRandomValue, IntFunction<S> mapping, String valueName) {
        super();

        this.random = new Random(randomSeed);
        this.values = new ArrayList<>();
        
        this.maxRandomValue = maxRandomValue;
        this.mapping = mapping;
        this.valueName = valueName;
    }
    
    /**
     * {@inheritDoc} 
     * 
     * This implementation is thread-safe.
     */
    @Override
    public S get(int i) {
        synchronized (values) {
            while (values.size() <= i) {
                int ord = random.nextInt(maxRandomValue);
                S value = mapping.apply(ord);
                values.add(value);
                if (log.isInfoEnabled()) {
                    log.info("{} {}: {}", valueName, Integer.valueOf(values.size() - 1), value);
                }
            }
        }
        
        return values.get(i);
    }
}
