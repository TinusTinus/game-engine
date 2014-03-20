package nl.mvdr.tinustris.engine;

import nl.mvdr.tinustris.model.Tetromino;

/**
 * Generator for Tetrominoes. Used to select the next tetromino to be dropped in a game of Tetris.
 * 
 * @author Martijn van de Rijdt
 */
public class RandomTetrominoGenerator extends RandomGenerator<Tetromino> {
    /** Constructor. */
    public RandomTetrominoGenerator() {
        super(Tetromino.values().length, i -> Tetromino.values()[i], "Tetromino");
    }

    /**
     * Constructor.
     * 
     * @param randomSeed
     *            random seed
     */
    public RandomTetrominoGenerator(long randomSeed) {
        super(randomSeed, Tetromino.values().length, i -> Tetromino.values()[i], "Tetromino");
    }
}
