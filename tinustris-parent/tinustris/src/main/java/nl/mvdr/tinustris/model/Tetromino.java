package nl.mvdr.tinustris.model;

import java.util.Arrays;
import java.util.List;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Representation of a Tetromino; that is, one of the seven groups of four blocks that can fall into the basin.
 * 
 * @author Martijn van de Rijdt
 */
@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
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
    I(Arrays.asList(new Point(0, 0), new Point(0, 0), new Point(0, 0), new Point(0, 0))),
    /**
     * O, or square shape.
     * 
     * <pre>
     * ++
     * ++
     * </pre>
     */
    O(Arrays.asList(new Point(0, 0), new Point(0, 0), new Point(0, 0), new Point(0, 0))),
    /**
     * T shape.
     * 
     * <pre>
     * +++
     *  +
     * </pre>
     */
    T(Arrays.asList(new Point(0, 0), new Point(0, 0), new Point(0, 0), new Point(0, 0))),
    /**
     * J shape.
     * 
     * <pre>
     *  +
     *  +
     * ++
     * </pre>
     */
    J(Arrays.asList(new Point(0, 0), new Point(0, 0), new Point(0, 0), new Point(0, 0))),
    /**
     * L shape.
     * 
     * <pre>
     * +
     * +
     * ++
     * </pre>
     */
    L(Arrays.asList(new Point(0, 0), new Point(0, 0), new Point(0, 0), new Point(0, 0))),
    /**
     * S skew shape.
     * 
     * <pre>
     *  ++
     * ++
     * </pre>
     */
    S(Arrays.asList(new Point(0, 0), new Point(0, 0), new Point(0, 0), new Point(0, 0))),
    /**
     * Z skew shape.
     * 
     * <pre>
     * ++
     *  ++
     * </pre>
     */
    Z(Arrays.asList(new Point(0, 0), new Point(0, 0), new Point(0, 0), new Point(0, 0)));

    /** List containing the (four) points where the tetrominoes actual blocks are located in a 4*4 grid. */
    private final List<Point> points;
}
