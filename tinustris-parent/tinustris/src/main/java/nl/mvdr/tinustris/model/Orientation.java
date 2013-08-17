package nl.mvdr.tinustris.model;

/**
 * Orientation of a tetromino.
 * 
 * Indicates which direction a tetromino is facing.
 * 
 * See the <a href="http://tetris.wikia.com/wiki/Orientation">Tetris Wiki</a> for more details.
 * 
 * @author Martijn van de Rijdt
 */
public enum Orientation {
    /**
     * Flat Down. The orientation where the flat side of the tetromino is on the bottom side. Also known as Point Up.
     * The default orientation when a new tetromino spawns.
     */
    FLAT_DOWN,
    /** Flat Left. The orientation where the flat side of the tetromino is on the left side. Also known as Point Right. */
    FLAT_LEFT,
    /** Flat Up. The orientation where the flat side of the tetromino is on the top side. Also known as Point Down. */
    FLAT_UP,
    /** Flat Right. The orientation where the flat side of the tetromino is on the right side. Also known as Point Left. */
    FLAT_RIGHT;

    /** @return the next orientation if rotated clockwise */
    public Orientation getNextClockwise() {
        Orientation[] values = values();
        return values[(ordinal() + 1) % values.length];
    }

    /** @return the next orientation if rotated counter-clockwise */
    public Orientation getNextCounterClockwise() {
        Orientation[] values = values();
        return values[(values.length + ordinal() - 1) % values.length];
    }
}
