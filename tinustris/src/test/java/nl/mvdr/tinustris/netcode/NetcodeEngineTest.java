package nl.mvdr.tinustris.netcode;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import nl.mvdr.tinustris.engine.DummyGameEngine;
import nl.mvdr.tinustris.input.InputState;
import nl.mvdr.tinustris.input.InputStateHolder;
import nl.mvdr.tinustris.model.DummyGameState;
import nl.mvdr.tinustris.model.FrameAndInputStatesContainer;
import nl.mvdr.tinustris.model.GameState;
import nl.mvdr.tinustris.netcode.NetcodeEngine;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Test class for {@link NetcodeEngine}.
 * 
 * @author Martijn van de Rijdt
 */
public class NetcodeEngineTest {
    /** Tests {@link NetcodeEngine#addGameState(GameState)} and {@link NetcodeEngine#retrieveLatestGameState()}. */
    @Test
    public void testAddAndRetrieve() {
        NetcodeEngine<DummyGameState> holder = new NetcodeEngine<>(Collections.<InputStateHolder> emptyList(),
                new DummyGameEngine());

        holder.addGameState(DummyGameState.GAME_NOT_OVER);
        Assert.assertEquals(DummyGameState.GAME_NOT_OVER, holder.retrieveLatestGameState());
    }

    /** Tests {@link NetcodeEngine#addGameState(GameState)} and {@link NetcodeEngine#retrieveLatestGameState()}. */
    @Test
    public void testAddAndRetrieveMultipleTimes() {
        NetcodeEngine<DummyGameState> holder = new NetcodeEngine<>(Collections.<InputStateHolder> emptyList(),
                new DummyGameEngine());

        holder.addGameState(DummyGameState.GAME_NOT_OVER);
        Assert.assertEquals(DummyGameState.GAME_NOT_OVER, holder.retrieveLatestGameState());
        Assert.assertEquals(DummyGameState.GAME_NOT_OVER, holder.retrieveLatestGameState());
        holder.addGameState(DummyGameState.GAME_OVER);
        Assert.assertEquals(DummyGameState.GAME_OVER, holder.retrieveLatestGameState());
        Assert.assertEquals(DummyGameState.GAME_OVER, holder.retrieveLatestGameState());
    }

    /** Tests {@link NetcodeEngine#retrieveLatestGameState()} in case no state has been added yet. */
    @Test(expected = NoSuchElementException.class)
    public void testRetrieveWithoutAdd() {
        NetcodeEngine<DummyGameState> holder = new NetcodeEngine<>(Collections.<InputStateHolder> emptyList(),
                new DummyGameEngine());

        holder.retrieveLatestGameState();
    }
    
    /** Tests the {@link NetcodeEngine#accept(FrameAndInputStatesContainer)} method. */
    @Test
    public void testInputForOtherPlayer() {
        InputStateHolder inputHolder = new InputStateHolder(true);
        NetcodeEngine<DummyGameState> gameStateHolder = 
            new NetcodeEngine<>(
                Arrays.asList(new InputStateHolder(false), inputHolder, new InputStateHolder(false), new InputStateHolder(true)),
                new DummyGameEngine());
        InputState state = i -> true;
        @SuppressWarnings("serial")
        Map<Integer, InputState> inputStates = new HashMap<Integer, InputState>() {{
            put(3, state);
        }};
        
        gameStateHolder.accept(new FrameAndInputStatesContainer(0, inputStates));
        
        Assert.assertNotEquals(state, inputHolder.getInputState());
    }
    
    /** Tests the {@link NetcodeEngine#accept(FrameAndInputStatesContainer)} method. */
    @Test
    public void testInputForOwnPlayer() {
        InputStateHolder inputHolder = new InputStateHolder(true);
        NetcodeEngine<DummyGameState> gameStateHolder = new NetcodeEngine<>(
            Arrays.asList(new InputStateHolder(false), inputHolder), new DummyGameEngine());
        InputState state = i -> true;
        @SuppressWarnings("serial")
        Map<Integer, InputState> inputStates = new HashMap<Integer, InputState>() {{
            put(1, state);
        }};
        
        gameStateHolder.accept(new FrameAndInputStatesContainer(0, inputStates));
        
        Assert.assertSame(state, inputHolder.getInputState());
    }
    
    
    /** Tests the {@link NetcodeEngine#isGameOver()} method, in case the latest game state is not a game over. */
    @Test
    public void testGameNotOver() {
        NetcodeEngine<DummyGameState> holder = new NetcodeEngine<>(Collections.<InputStateHolder> emptyList(),
                new DummyGameEngine());
        holder.addGameState(DummyGameState.GAME_NOT_OVER);
        
        Assert.assertFalse(holder.isGameOver());
    }
    
    /** Tests the {@link NetcodeEngine#isGameOver()} method, in case there is just one game state, which is game over. */
    @Test
    public void testGameOver() {
        NetcodeEngine<DummyGameState> holder = new NetcodeEngine<>(Collections.<InputStateHolder> emptyList(),
                new DummyGameEngine());
        holder.addGameState(DummyGameState.GAME_OVER);
        
        Assert.assertTrue(holder.isGameOver());        
    }

    /**
     * Tests the {@link NetcodeEngine#isGameOver()} method, in case there have been multiple game state updates, all
     * input states are known, and the game is over.
     */
    @SuppressWarnings("serial") // local test maps, not to be serialised
    @Test
    public void testGameOverAfterSeveralStates() {
        NetcodeEngine<DummyGameState> holder = new NetcodeEngine<>(
                Arrays.asList(new InputStateHolder(false), new InputStateHolder(false)),
                new DummyGameEngine());
        holder.addGameState(DummyGameState.GAME_NOT_OVER);
        holder.accept(new FrameAndInputStatesContainer(0, new HashMap<Integer, InputState>() {{
            put(0, input -> false);
            put(1, input -> false);
        }}));
        holder.addGameState(DummyGameState.GAME_NOT_OVER);
        holder.accept(new FrameAndInputStatesContainer(1, new HashMap<Integer, InputState>() {{
            put(0, input -> false);
            put(1, input -> false);
        }}));
        holder.addGameState(DummyGameState.GAME_OVER);
        
        Assert.assertTrue(holder.isGameOver());        
    }
    
    /**
     * Tests the {@link NetcodeEngine#isGameOver()} method, in case there have been multiple game state updates, but not
     * all input states are known.
     */
    @SuppressWarnings("serial") // local test maps, not to be serialised
    @Test
    @Ignore // TODO implement this functionality
    public void testGameOverAfterSeveralStatesIncompleteInputs() {
        NetcodeEngine<DummyGameState> holder = new NetcodeEngine<>(
                Arrays.asList(new InputStateHolder(false), new InputStateHolder(true)),
                new DummyGameEngine());
        holder.addGameState(DummyGameState.GAME_NOT_OVER);
        holder.accept(new FrameAndInputStatesContainer(0, new HashMap<Integer, InputState>() {{
            put(0, input -> false);
        }}));
        holder.accept(new FrameAndInputStatesContainer(0, new HashMap<Integer, InputState>() {{
            put(1, input -> false);
        }}));
        holder.addGameState(DummyGameState.GAME_NOT_OVER);
        holder.accept(new FrameAndInputStatesContainer(1, new HashMap<Integer, InputState>() {{
            put(0, input -> false);
        }}));
        holder.addGameState(DummyGameState.GAME_OVER);
        
        Assert.assertFalse(holder.isGameOver());        
    }
}
