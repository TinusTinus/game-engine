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
}
