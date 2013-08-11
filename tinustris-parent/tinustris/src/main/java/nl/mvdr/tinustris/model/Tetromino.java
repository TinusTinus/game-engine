package nl.mvdr.tinustris.model;

/**
 * Representation of a Tetromino; that is, one of the seven groups of four blocks that can fall into the basin.
 * 
 * @author Martijn van de Rijdt
 */
public enum Tetromino {
    /**
     * I, or straight shape.
     * 
     * <pre>
     * +
     * +
     * +
     * +
     * </pre>
     */
    I,
    /**
     * O, or square shape.
     * 
     * <pre>
     * ++
     * ++
     * </pre>
     */
    O,
    /**
     * T shape.
     * 
     * <pre>
     * +++
     *  +
     * </pre>
     */
    T,
    /**
     * J shape.
     * 
     * <pre>
     *  +
     *  +
     * ++
     * </pre>
     */
    J,
    /**
     * L shape.
     * 
     * <pre>
     * +
     * +
     * ++
     * </pre>
     */
    L,
    /**
     * S skew shape.
     * 
     * <pre>
     *  ++
     * ++
     * </pre>
     */
    S,
    /**
     * Z skew shape.
     * 
     * <pre>
     * ++
     *  ++
     * </pre>
     */
    Z
}
