package nl.mvdr.tinustris.model;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import lombok.NonNull;

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
     * ++++
     * </pre>
     */
    I(Arrays.asList(new Point(0, 2), new Point(1, 2), new Point(2, 2), new Point(3, 2)),
            Arrays.asList(new Point(2, 0), new Point(2, 1), new Point(2, 2), new Point(2, 3)),
            Arrays.asList(new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(3, 1)),
            Arrays.asList(new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(1, 3))),
    /**
     * O, or square shape.
     * 
     * <pre>
     * ++
     * ++
     * </pre>
     */
    O(Arrays.asList(new Point(1, 1), new Point(1, 2), new Point(2, 1), new Point(2, 2)),
            Arrays.asList(new Point(1, 1), new Point(1, 2), new Point(2, 1), new Point(2, 2)),
            Arrays.asList(new Point(1, 1), new Point(1, 2), new Point(2, 1), new Point(2, 2)),
            Arrays.asList(new Point(1, 1), new Point(1, 2), new Point(2, 1), new Point(2, 2))),
    /**
     * T shape.
     * 
     * <pre>
     *  +
     * +++
     * </pre>
     */
    T(Arrays.asList(new Point(0, 2), new Point(1, 2), new Point(2, 2), new Point(1, 3)),
            Arrays.asList(new Point(1, 1), new Point(1, 2), new Point(1, 3), new Point(2, 2)),
            Arrays.asList(new Point(0, 2), new Point(1, 2), new Point(2, 2), new Point(1, 1)),
            Arrays.asList(new Point(1, 1), new Point(1, 2), new Point(1, 3), new Point(0, 2))),
    /**
     * J shape.
     * 
     * <pre>
     * +
     * +++
     * </pre>
     */
    J(Arrays.asList(new Point(0, 2), new Point(1, 2), new Point(2, 2), new Point(0, 3)),
            Arrays.asList(new Point(1, 1), new Point(1, 2), new Point(1, 3), new Point(2, 3)),
            Arrays.asList(new Point(0, 2), new Point(1, 2), new Point(2, 2), new Point(2, 1)),
            Arrays.asList(new Point(1, 1), new Point(1, 2), new Point(1, 3), new Point(0, 1))),
    /**
     * L shape.
     * 
     * <pre>
     *   +
     * +++
     * </pre>
     */
    L(Arrays.asList(new Point(0, 2), new Point(1, 2), new Point(2, 2), new Point(2, 3)),
            Arrays.asList(new Point(1, 1), new Point(1, 2), new Point(1, 3), new Point(2, 1)),
            Arrays.asList(new Point(0, 2), new Point(1, 2), new Point(2, 2), new Point(0, 1)),
            Arrays.asList(new Point(1, 1), new Point(1, 2), new Point(1, 3), new Point(0, 3))),
    /**
     * S skew shape.
     * 
     * <pre>
     *  ++
     * ++
     * </pre>
     */
    S(Arrays.asList(new Point(0, 2), new Point(1, 2), new Point(1, 3), new Point(2, 3)),
            Arrays.asList(new Point(1, 3), new Point(1, 2), new Point(2, 2), new Point(2, 1)),
            Arrays.asList(new Point(0, 1), new Point(1, 1), new Point(1, 2), new Point(2, 2)),
            Arrays.asList(new Point(0, 3), new Point(0, 2), new Point(1, 2), new Point(1, 1))),
    /**
     * Z skew shape.
     * 
     * <pre>
     * ++
     *  ++
     * </pre>
     */
    Z(Arrays.asList(new Point(0, 0), new Point(0, 0), new Point(0, 0), new Point(0, 0)),
            Arrays.asList(new Point(0, 0), new Point(0, 0), new Point(0, 0), new Point(0, 0)),
            Arrays.asList(new Point(0, 0), new Point(0, 0), new Point(0, 0), new Point(0, 0)),
            Arrays.asList(new Point(0, 0), new Point(0, 0), new Point(0, 0), new Point(0, 0)));

    /**
     * Per orientation: a list containing the (four) points where the tetrominoes actual blocks are located in a 4*4
     * grid.
     */
    private final Map<Orientation, List<Point>> points;
    
    /**
     * Constructor.
     * 
     * @param pointsFlatDown list containing the points for the Flat Down orientation
     * @param pointsFlatLeft list containing the points for the Flat Left orientation
     * @param pointsFlatUp list containing the points for the Flat Up orientation
     * @param pointsFlatRight list containing the points for the Flat Right orientation
     */
    private Tetromino(List<Point> pointsFlatDown, List<Point> pointsFlatLeft, List<Point> pointsFlatUp, List<Point> pointsFlatRight) {
        this.points = new EnumMap<>(Orientation.class);
        this.points.put(Orientation.FLAT_DOWN,  pointsFlatDown);
        this.points.put(Orientation.FLAT_LEFT,  pointsFlatLeft);
        this.points.put(Orientation.FLAT_UP,    pointsFlatUp);
        this.points.put(Orientation.FLAT_RIGHT, pointsFlatRight);
    }

    /**
     * Retrieves the list of points for the given orientation.
     * 
     * @param key orientation value
     * @return a list containing the (four) points where the tetrominoes actual blocks are located in a 4*4
     * grid
     * @see java.util.Map#get(java.lang.Object)
     */
    public List<Point> getPoints(@NonNull Orientation key) {
        return points.get(key);
    }
}
