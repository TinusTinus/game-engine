package nl.mvdr.tinustris.engine;

import lombok.extern.slf4j.Slf4j;
import nl.mvdr.tinustris.input.DummyInputController;
import nl.mvdr.tinustris.input.InputState;
import nl.mvdr.tinustris.model.GameState;
import nl.mvdr.tinustris.model.Tetromino;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test class for {@link TinustrisEngine}.
 * 
 * @author Martijn van de Rijdt
 */
@Slf4j
public class TinustrisEngineTest {
    /** Tests the {@link TinustrisEngine#initGameState()} method. */
    @Test
    public void testInitGameState() {
        TetrominoGenerator generator = new DummyTetrominoGenerator(Tetromino.I, Tetromino.T);
        TinustrisEngine engine = new TinustrisEngine(generator);
        
        GameState state = engine.initGameState();
        
        log.info(state.toString());
        Assert.assertNotNull(state);
        Assert.assertFalse(state.isTopped());
        Assert.assertEquals(GameState.DEFAULT_WIDTH, state.getWidth());
        Assert.assertEquals(GameState.DEFAULT_HEIGHT, state.getHeight());
        Assert.assertEquals(Tetromino.I, state.getCurrentBlock());
        Assert.assertEquals(Tetromino.T, state.getNextBlock());
    }
    
    /** Tests the {@link TinustrisEngine#computeNextState(GameState, nl.mvdr.tinustris.input.InputState)} method. */
    @Test
    public void testNextState() {
        TetrominoGenerator generator = new DummyTetrominoGenerator();
        TinustrisEngine engine = new TinustrisEngine(generator);
        GameState state = new GameState();
        InputState inputState = new DummyInputController().getInputState();
        
        state = engine.computeNextState(state, inputState);

        log.info(state.toString());
        Assert.assertNotNull(state);
    }
}
