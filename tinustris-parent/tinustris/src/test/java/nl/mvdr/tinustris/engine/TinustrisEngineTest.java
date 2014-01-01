package nl.mvdr.tinustris.engine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import nl.mvdr.tinustris.engine.speedcurve.ConstantSpeedCurve;
import nl.mvdr.tinustris.engine.speedcurve.GameBoySpeedCurve;
import nl.mvdr.tinustris.input.DummyInputController;
import nl.mvdr.tinustris.input.Input;
import nl.mvdr.tinustris.input.InputState;
import nl.mvdr.tinustris.input.InputStateHistory;
import nl.mvdr.tinustris.model.GameState;
import nl.mvdr.tinustris.model.Orientation;
import nl.mvdr.tinustris.model.Point;
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
    /** Tests the constructor. */
    @Test
    public void testDefaultConstructor() {
        new TinustrisEngine();
    }
    
    /** Tests the constructor. */
    @Test
    public void testConstructor() {
        new TinustrisEngine(new DummyTetrominoGenerator(), new ConstantSpeedCurve());
    }
    
    /** Tests the constructor. */
    @Test(expected = NullPointerException.class)
    public void testConstructorNullGenerator() {
        new TinustrisEngine(null, new ConstantSpeedCurve());
    }
    
    /** Tests the constructor. */
    @Test(expected = NullPointerException.class)
    public void testConstructorNullCurve() {
        new TinustrisEngine(new DummyTetrominoGenerator(), null);
    }
    
    /** Tests the {@link TinustrisEngine#initGameState()} method. */
    @Test
    public void testInitGameState() {
        TetrominoGenerator generator = new DummyTetrominoGenerator(Tetromino.I, Tetromino.T);
        TinustrisEngine engine = new TinustrisEngine(generator, new ConstantSpeedCurve());
        
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
        TinustrisEngine engine = new TinustrisEngine(generator, new ConstantSpeedCurve());
        GameState state = new GameState();
        InputState inputState = new DummyInputController().getInputState();
        
        state = engine.computeNextState(state, inputState);

        log.info(state.toString());
        Assert.assertNotNull(state);
    }
    
    /**
     * Tests the {@link TinustrisEngine#computeNextState(GameState, nl.mvdr.tinustris.input.InputState)} method with a
     * hard drop input.
     */
    @Test
    public void testNextStateHardDrop() {
        TetrominoGenerator generator = new DummyTetrominoGenerator();
        TinustrisEngine engine = new TinustrisEngine(generator, new ConstantSpeedCurve());
        List<Tetromino> grid = new ArrayList<>();
        for (int i = 0; i != 220; i++) {
            grid.add(null);
        }
        grid.set(1, Tetromino.O);
        grid.set(2, Tetromino.O);
        grid.set(3, Tetromino.S);
        grid.set(4, Tetromino.Z);
        grid.set(5, Tetromino.O);
        grid.set(6, Tetromino.O);
        grid.set(7, Tetromino.J);
        grid.set(8, Tetromino.J);
        grid.set(9, Tetromino.L);
        grid.set(12, Tetromino.S);
        grid.set(13, Tetromino.S);
        grid.set(18, Tetromino.J);
        grid.set(22, Tetromino.S);
        grid.set(28, Tetromino.J);
        GameState state = new GameState(grid, 10, Tetromino.O, new Point(5, 13), Orientation.FLAT_DOWN, Tetromino.I,
                11, 0, 11, new InputStateHistory(), 236, 93, 0);
        log.info("Before: " + state);
        InputState inputState = new DummyInputController(Collections.singleton(Input.HARD_DROP)).getInputState();
        
        state = engine.computeNextState(state, inputState);

        log.info("After: " + state);
        Assert.assertNotNull(state);
    }
    
    /**
     * Tests the {@link TinustrisEngine#computeNextState(GameState, nl.mvdr.tinustris.input.InputState)} method with a
     * hard drop input and a Game Boy speed curve.
     */
    @Test
    public void testNextStateHardDropGameBoyCurve() {
        TetrominoGenerator generator = new DummyTetrominoGenerator();
        TinustrisEngine engine = new TinustrisEngine(generator, new GameBoySpeedCurve());
        List<Tetromino> grid = new ArrayList<>();
        for (int i = 0; i != 220; i++) {
            grid.add(null);
        }
        grid.set(1, Tetromino.O);
        grid.set(2, Tetromino.O);
        grid.set(3, Tetromino.S);
        grid.set(4, Tetromino.Z);
        grid.set(5, Tetromino.O);
        grid.set(6, Tetromino.O);
        grid.set(7, Tetromino.J);
        grid.set(8, Tetromino.J);
        grid.set(9, Tetromino.L);
        grid.set(12, Tetromino.S);
        grid.set(13, Tetromino.S);
        grid.set(18, Tetromino.J);
        grid.set(22, Tetromino.S);
        grid.set(28, Tetromino.J);
        GameState state = new GameState(grid, 10, Tetromino.O, new Point(5, 13), Orientation.FLAT_DOWN, Tetromino.I,
                11, 0, 11, new InputStateHistory(), 236, 93, 0);
        log.info("Before: " + state);
        InputState inputState = new DummyInputController(Collections.singleton(Input.HARD_DROP)).getInputState();
        
        state = engine.computeNextState(state, inputState);

        log.info("After: " + state);
        Assert.assertNotNull(state);
    }

}
