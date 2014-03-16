package nl.mvdr.tinustris.engine;

import java.util.Collections;
import java.util.List;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

/**
 * Dummy implementation of {@link Generator}.
 * 
 * @param <S> value type
 * 
 * @author Martijn van de Rijdt
 */
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class DummyGenerator<S> implements Generator<S> {
    /** Tetrominoes to be returned by the get method. */
    private final List<S> values;

    /** Constructor. */
    DummyGenerator() {
        this(Collections.emptyList());
    }
    
    /**
     * {@inheritDoc}
     * 
     * Dummy implementation which just returns values i from the list passed into the constructor. Throws an
     * IndexOutOfBoundsException if i is not in range of the list.
     */
    @Override
    public S get(int i) {
        return values.get(i);
    }
}
