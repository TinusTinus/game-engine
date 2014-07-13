package nl.mvdr.tinustris.engine;

/**
 * Generator for determining the gap in garbage lines in a game of Tetris.
 * 
 * @author Martijn van de Rijdt
 */
public class GapGenerator extends RandomGenerator<Integer> {
    /**
     * Constructor.
     * 
     * @param width
     *            width of the playing field
     */
    public GapGenerator(int width) {
        super(width, Integer::valueOf, "Gap");
    }

    /**
     * Constructor.
     * 
     * @param randomSeed
     *            seed for the random generator
     * @param width
     *            width of the playing field
     */
    public GapGenerator(long randomSeed, int width) {
        super(randomSeed, width, Integer::valueOf, "Gap");
    }
}
