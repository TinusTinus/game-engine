package nl.mvdr.tinustris.engine;

import java.util.Arrays;
import java.util.List;

import nl.mvdr.tinustris.model.Tetromino;

/**
 * Dummy implementation of {@link TetrominoGenerator}.
 * 
 * @author Martijn van de Rijdt
 */
class DummyTetrominoGenerator implements TetrominoGenerator {
    /** Tetrominoes to be returned by the get method. */
    private List<Tetromino> tetrominoes;

    /**
     * Convenience constructor.
     * 
     * @param tetrominoes tetrominoes
     */
    DummyTetrominoGenerator(Tetromino... tetrominoes) {
        super();
        this.tetrominoes = Arrays.asList(tetrominoes);
    }
    
    /**
     * {@inheritDoc}
     * 
     * Dummy implementation which just returns tetromino i from the tetrominoes passed into the constructor. Throws an
     * IndexOutOfBoundsException if i is not in range of the list.
     */
    @Override
    public Tetromino get(int i) {
        return tetrominoes.get(i);
    }
}
