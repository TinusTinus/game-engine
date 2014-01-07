package nl.mvdr.tinustris.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Representation of a value consisting of x and y coordinates.
 * 
 * @author Martijn van de Rijdt
 */
@RequiredArgsConstructor
@EqualsAndHashCode
@Getter
public class Point {
    /** X coordinate. */
    private final int x;
    /** Y coordinate. */
    private final int y;
    
    /** {@inheritDoc} */
    @Override
    public String toString() {
        return "(" + x + ", " + y  + ")";
    }
    
    /**
     * Returns a point equal to this point, translated with the given delta. Note that this method does not modify this
     * point instance, instead a new instance is returned.
     * 
     * @param deltaX
     *            amount to be added to x
     * @param deltaY
     *            amount to be added to y
     * @return new point
     */
    public Point translate(int deltaX, int deltaY) {
        return new Point(x + deltaX, y + deltaY);
    }
}
