package nl.mvdr.tinustris.model;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

/**
 * Types of blocks that can make up a Tetris grid.
 * 
 * @author Martijn van de Rijdt
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum Block {
    /** Block that makes up the I shape. */
    I("I"),
    /** Block that makes up the O shape. */
    O("O"),
    /** Block that makes up the T shape. */
    T("T"),
    /** Block that makes up the J shape. */
    J("J"),
    /** Block that makes up the L shape. */
    L("L"),
    /** Block that makes up the S skew shape. */
    S("S"),
    /** Block that makes up the Z skew shape. */
    Z("Z"),
    /** Garbage block. */
    GARBAGE("G");
    
    /** String representation. */
    private final String stringRepresentation;

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return stringRepresentation;
    }
}
