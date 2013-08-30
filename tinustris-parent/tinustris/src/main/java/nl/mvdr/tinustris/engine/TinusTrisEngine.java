package nl.mvdr.tinustris.engine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import nl.mvdr.tinustris.input.InputState;
import nl.mvdr.tinustris.model.GameState;
import nl.mvdr.tinustris.model.Tetromino;

/**
 * Implementation of {@link GameEngine}.
 * 
 * @author Martijn van de Rijdt
 */
public class TinusTrisEngine implements GameEngine {
    /** {@inheritDoc} */
    @Override
    public GameState computeNextState(GameState previousState, InputState inputState) {
        // TODO implement for realsies
        
        GameState result;
        // dummy implementation simply adds a single block each time
        int i = previousState.getGrid().indexOf(null);
        if (0 <= i) {
            List<Tetromino> grid = new ArrayList<>(previousState.getGrid());
            grid.set(i, Tetromino.T);
            grid = Collections.unmodifiableList(grid);
            result = new GameState(grid, previousState.getWidth(), previousState.getCurrentBlock(),
                    previousState.getNextBlock());            
        } else {
            // grid is already full
            result = previousState;
        }
        return result;
    }
}
