package nl.mvdr.tinustris.engine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import nl.mvdr.tinustris.engine.level.DummyLevelSystem;
import nl.mvdr.tinustris.engine.speedcurve.ConstantSpeedCurve;
import nl.mvdr.tinustris.engine.speedcurve.GameBoySpeedCurve;
import nl.mvdr.tinustris.input.Input;
import nl.mvdr.tinustris.input.InputState;
import nl.mvdr.tinustris.input.InputStateHistory;
import nl.mvdr.tinustris.model.Block;
import nl.mvdr.tinustris.model.OnePlayerGameState;
import nl.mvdr.tinustris.model.Orientation;
import nl.mvdr.tinustris.model.Point;
import nl.mvdr.tinustris.model.Tetromino;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test class for {@link OnePlayerEngine}.
 * 
 * @author Martijn van de Rijdt
 */
@Slf4j
public class OnePlayerEngineTest {
    /** Tests the constructor. */
    @Test
    public void testDefaultConstructor() {
        new OnePlayerEngine();
    }
    
    /** Tests the constructor. */
    @Test
    public void testConstructor() {
        new OnePlayerEngine(new DummyTetrominoGenerator(), new ConstantSpeedCurve(), new DummyLevelSystem());
    }
    
    /** Tests the constructor. */
    @Test(expected = NullPointerException.class)
    public void testConstructorNullGenerator() {
        new OnePlayerEngine(null, new ConstantSpeedCurve(), new DummyLevelSystem());
    }
    
    /** Tests the constructor. */
    @Test(expected = NullPointerException.class)
    public void testConstructorNullCurve() {
        new OnePlayerEngine(new DummyTetrominoGenerator(), null, new DummyLevelSystem());
    }
    
    /** Tests the constructor. */
    @Test(expected = NullPointerException.class)
    public void testConstructorNullLevelSystem() {
        new OnePlayerEngine(new DummyTetrominoGenerator(), new ConstantSpeedCurve(), null);
    }
    
    /** Tests the {@link OnePlayerEngine#initGameState()} method. */
    @Test
    public void testInitGameState() {
        TetrominoGenerator generator = new DummyTetrominoGenerator(Tetromino.I, Tetromino.T);
        OnePlayerEngine engine = new OnePlayerEngine(generator, new ConstantSpeedCurve(), new DummyLevelSystem());
        
        OnePlayerGameState state = engine.initGameState();
        
        log.info(state.toString());
        Assert.assertNotNull(state);
        Assert.assertFalse(state.isTopped());
        Assert.assertEquals(OnePlayerGameState.DEFAULT_WIDTH, state.getWidth());
        Assert.assertEquals(OnePlayerGameState.DEFAULT_HEIGHT, state.getHeight());
        Assert.assertEquals(Tetromino.I, state.getActiveTetromino());
        Assert.assertEquals(Tetromino.T, state.getNext());
    }
    
    /** Tests the {@link OnePlayerEngine#computeNextState(OnePlayerGameState, InputState)} method. */
    @Test
    public void testNextState() {
        OnePlayerEngine engine = new OnePlayerEngine(new DummyTetrominoGenerator(), new ConstantSpeedCurve(), new DummyLevelSystem());
        OnePlayerGameState state = new OnePlayerGameState();
        InputState inputState = input -> false;
        
        state = engine.computeNextState(state, Arrays.asList(inputState));

        log.info(state.toString());
        Assert.assertNotNull(state);
    }
    
    /**
     * Tests the {@link OnePlayerEngine#computeNextState(OnePlayerGameState, InputState)} method
     * without any input states.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testNextStateNoInputState() {
        OnePlayerEngine engine = new OnePlayerEngine(new DummyTetrominoGenerator(), new ConstantSpeedCurve(), new DummyLevelSystem());
        OnePlayerGameState state = new OnePlayerGameState();
        
        engine.computeNextState(state, Collections.<InputState>emptyList());
    }
    
    /** Tests the {@link OnePlayerEngine#computeNextState(OnePlayerGameState, InputState)} method with too many input states. */
    @Test(expected = IllegalArgumentException.class)
    public void testNextStateTwoInputStates() {
        TetrominoGenerator generator = new DummyTetrominoGenerator();
        OnePlayerEngine engine = new OnePlayerEngine(generator, new ConstantSpeedCurve(), new DummyLevelSystem());
        OnePlayerGameState state = new OnePlayerGameState();
        InputState inputState = input -> false;
        
        engine.computeNextState(state, Arrays.asList(inputState, inputState));
    }
    
    /**
     * Tests the {@link OnePlayerEngine#computeNextState(OnePlayerGameState, InputState)} method
     * with a hard drop input.
     */
    @Test
    public void testNextStateHardDrop() {
        OnePlayerEngine engine = new OnePlayerEngine(new DummyTetrominoGenerator(), new ConstantSpeedCurve(), new DummyLevelSystem());
        OnePlayerGameState state = createGameStateForHardDropTest();
        log.info("Before: " + state);
        InputState inputState = input -> input == Input.HARD_DROP;
        
        state = engine.computeNextState(state, Arrays.asList(inputState));

        log.info("After: " + state);
        Assert.assertNotNull(state);
    }
    
    /**
     * Tests the {@link OnePlayerEngine#computeNextState(OnePlayerGameState, InputState)} method with a hard drop input
     * and a Game Boy speed curve.
     */
    @Test
    public void testNextStateHardDropGameBoyCurve() {
        OnePlayerEngine engine = new OnePlayerEngine(new DummyTetrominoGenerator(), new GameBoySpeedCurve(), new DummyLevelSystem());
        OnePlayerGameState state = createGameStateForHardDropTest();
        log.info("Before: " + state);
        InputState inputState = input -> input == Input.HARD_DROP;
        
        state = engine.computeNextState(state, Arrays.asList(inputState));

        log.info("After: " + state);
        Assert.assertNotNull(state);
    }

    /**
     * Creates the game state for the hard drop test cases.
     * 
     * @return newly created game state
     */
    private OnePlayerGameState createGameStateForHardDropTest() {
        List<Block> grid = new ArrayList<>(Collections.nCopies(220, null));
        grid.set(1, Block.O);
        grid.set(2, Block.O);
        grid.set(3, Block.S);
        grid.set(4, Block.Z);
        grid.set(5, Block.O);
        grid.set(6, Block.O);
        grid.set(7, Block.J);
        grid.set(8, Block.J);
        grid.set(9, Block.L);
        grid.set(12, Block.S);
        grid.set(13, Block.S);
        grid.set(18, Block.J);
        grid.set(22, Block.S);
        grid.set(28, Block.J);
        OnePlayerGameState state = new OnePlayerGameState(grid, 10, Tetromino.O, new Point(5, 13), 
                Orientation.FLAT_DOWN, Tetromino.I, 11, 0, 11, InputStateHistory.NEW, 236, 93, 0);
        return state;
    }
}
