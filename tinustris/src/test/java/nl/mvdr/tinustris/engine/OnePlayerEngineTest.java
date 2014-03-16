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
        new OnePlayerEngine(new DummyGenerator<>(), new ConstantSpeedCurve(), new DummyLevelSystem(),
                new DummyGenerator<>());
    }
    
    /** Tests the constructor. */
    @Test(expected = NullPointerException.class)
    public void testConstructorNullGenerator() {
        new OnePlayerEngine(null, new ConstantSpeedCurve(), new DummyLevelSystem(), new DummyGenerator<>());
    }
    
    /** Tests the constructor. */
    @Test(expected = NullPointerException.class)
    public void testConstructorNullCurve() {
        new OnePlayerEngine(new DummyGenerator<>(), null, new DummyLevelSystem(), new DummyGenerator<>());
    }
    
    /** Tests the constructor. */
    @Test(expected = NullPointerException.class)
    public void testConstructorNullLevelSystem() {
        new OnePlayerEngine(new DummyGenerator<>(), new ConstantSpeedCurve(), null, new DummyGenerator<>());
    }
    
    /** Tests the constructor. */
    @Test(expected = NullPointerException.class)
    public void testConstructorNullGapGenerator() {
        new OnePlayerEngine(new DummyGenerator<>(), new ConstantSpeedCurve(), new DummyLevelSystem(), null);
    }
    
    /** Tests the {@link OnePlayerEngine#initGameState()} method. */
    @Test
    public void testInitGameState() {
        Generator<Tetromino> generator = new DummyGenerator<>(Arrays.asList(Tetromino.I, Tetromino.T));
        OnePlayerEngine engine = new OnePlayerEngine(generator, new ConstantSpeedCurve(), new DummyLevelSystem(),
                new DummyGenerator<>());
        
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
        OnePlayerEngine engine = new OnePlayerEngine(new DummyGenerator<>(), new ConstantSpeedCurve(),
                new DummyLevelSystem(), new DummyGenerator<>());
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
        OnePlayerEngine engine = new OnePlayerEngine(new DummyGenerator<>(), new ConstantSpeedCurve(),
                new DummyLevelSystem(), new DummyGenerator<>());
        OnePlayerGameState state = new OnePlayerGameState();
        
        engine.computeNextState(state, Collections.<InputState>emptyList());
    }
    
    /** Tests the {@link OnePlayerEngine#computeNextState(OnePlayerGameState, InputState)} method with too many input states. */
    @Test(expected = IllegalArgumentException.class)
    public void testNextStateTwoInputStates() {
        Generator<Tetromino> generator = new DummyGenerator<>();
        OnePlayerEngine engine = new OnePlayerEngine(generator, new ConstantSpeedCurve(), new DummyLevelSystem(),
                new DummyGenerator<>());
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
        OnePlayerEngine engine = new OnePlayerEngine(new DummyGenerator<>(), new ConstantSpeedCurve(),
                new DummyLevelSystem(), new DummyGenerator<>());
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
        OnePlayerEngine engine = new OnePlayerEngine(new DummyGenerator<>(), new GameBoySpeedCurve(),
                new DummyLevelSystem(), new DummyGenerator<>());
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
                Orientation.FLAT_DOWN, Tetromino.I, 11, 0, 11, InputStateHistory.NEW, 236, 93, 0, 0, 0);
        return state;
    }
    
    /**
     * Test case for {@link OnePlayerEngine#computeNextState(OnePlayerGameState, List)} in case the previous state has
     * garbage lines to be processed.
     */
    @Test
    public void testGarbageLine() {
        DummyGenerator<Tetromino> generator = new DummyGenerator<>(Arrays.asList(Tetromino.O, Tetromino.T, Tetromino.L));
        ConstantSpeedCurve curve = new ConstantSpeedCurve();
        OnePlayerEngine engine = new OnePlayerEngine(generator, curve, new DummyLevelSystem(), i -> 0);
        List<Block> grid = new ArrayList<>(Collections.nCopies(220, null));
        grid.set(0, Block.O);
        OnePlayerGameState previousState = new OnePlayerGameState(grid, OnePlayerGameState.DEFAULT_WIDTH, null, null,
                null, Tetromino.T, 0, curve.getAre() + 1, 0, InputStateHistory.NEW, 0, 0, 1, 0, 1);
        log.info("Previous game state: " + previousState);
        
        OnePlayerGameState state = engine.computeNextState(previousState, Collections.singletonList(input -> false));
        
        log.info("Next game state: " + state);
        Assert.assertEquals(0, state.getGarbageLines());
        Assert.assertEquals(null, state.getGrid().get(0));
        state.getGrid().subList(1, OnePlayerGameState.DEFAULT_WIDTH).forEach(block -> Assert.assertEquals(Block.GARBAGE, block));
        Assert.assertEquals(Block.O, state.getGrid().get(OnePlayerGameState.DEFAULT_WIDTH));
        state.getGrid().subList(OnePlayerGameState.DEFAULT_WIDTH + 1, 220).forEach(Assert::assertNull);
    }
}
