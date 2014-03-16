package nl.mvdr.tinustris.engine;

import nl.mvdr.tinustris.model.Tetromino;

/**
 * Implementation of the tetromino generator that uses a random generator to determine new tetrominoes, and a List to
 * store past ones.
 * 
 * @author Martijn van de Rijdt
 */
class RandomTetrominoGenerator extends RandomGenerator<Tetromino> {
    /** Constructor. */
    RandomTetrominoGenerator() {
        super(Tetromino.values().length, i -> Tetromino.values()[i], "Tetromino");
    }

    /**
     * Constructor.
     * 
     * @param randomSeed
     *            random seed
     */
    RandomTetrominoGenerator(long randomSeed) {
        super(randomSeed, Tetromino.values().length, i -> Tetromino.values()[i], "Tetromino");
    }
}
