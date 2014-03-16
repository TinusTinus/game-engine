package nl.mvdr.tinustris.engine;

import java.util.function.Function;

/**
 * Generator for determining the gap in garbage lines in a game of Tetris.
 * 
 * @author Martijn van de Rijdt
 */
class GapGenerator extends RandomGenerator<Integer> {
    /**
     * Constructor.
     * 
     * @param width
     *            width of the playing field
     */
    GapGenerator(int width) {
        super(width, Function.identity(), "Gap");
    }

    /**
     * Constructor.
     * 
     * @param randomSeed
     *            seed for the random generator
     * @param width
     *            width of the playing field
     */
    GapGenerator(long randomSeed, int width) {
        super(randomSeed, width, Function.identity(), "Gap");
    }
}
