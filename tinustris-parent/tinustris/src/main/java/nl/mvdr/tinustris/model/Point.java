package nl.mvdr.tinustris.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * Representation of a value consisiting of an x and y coordinates.
 * 
 * @author Martijn van de Rijdt
 */
@RequiredArgsConstructor
@ToString
@EqualsAndHashCode
@Getter
public class Point {
    /** X coordinate. */
    private final int x;
    /** Y coordinate. */
    private final int y;
}
