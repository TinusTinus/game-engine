package nl.mvdr.tinustris.engine;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import lombok.extern.slf4j.Slf4j;
import nl.mvdr.tinustris.model.Tetromino;

/**
 * Implementation of the tetromino generator that uses a random generator to determine new tetrominoes, and a List to
 * store past ones.
 * 
 * @author Martijn van de Rijdt
 */
@Slf4j
class RandomTetrominoGenerator implements Generator<Tetromino> {
    /** Random number generator. */
    private final Random random;
    /** List of tetrominoes returned so far. */
    // Note that this list can only grow, never shrink, which is a potential memory leak.
    // In practice games are not expected to go on long enough for this to be a problem.
    // If that assumption is wrong, this list will probably need to be converted to a linked list where the first part
    // is cleared when it is no longer required.
    private final List<Tetromino> tetrominoes;
    
    /** Constructor. */
    RandomTetrominoGenerator() {
        super();
        this.random = new Random();
        this.tetrominoes = new ArrayList<>();
    }

    /**
     * Constructor.
     * 
     * @param randomSeed
     *            random seed
     */
    RandomTetrominoGenerator(long randomSeed) {
        super();
        this.random = new Random(randomSeed);
        this.tetrominoes = new ArrayList<>();
    }
    
    /**
     * {@inheritDoc} 
     * 
     * This implementation is thread-safe.
     */
    @Override
    public Tetromino get(int i) {
        synchronized (tetrominoes) {
            while (tetrominoes.size() <= i) {
                int ord = random.nextInt(Tetromino.values().length);
                Tetromino tetromino = Tetromino.values()[ord];
                tetrominoes.add(tetromino);
                if (log.isInfoEnabled()) {
                    log.info("Block {}: {}", Integer.valueOf(tetrominoes.size()), tetromino);
                }
            }
        }
        
        return tetrominoes.get(i);
    }
}
