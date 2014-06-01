package nl.mvdr.tinustris.netcode;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import nl.mvdr.tinustris.input.InputState;
import nl.mvdr.tinustris.input.InputStateHolder;
import nl.mvdr.tinustris.model.DummyGameState;
import nl.mvdr.tinustris.model.FrameAndInputStatesContainer;
import nl.mvdr.tinustris.model.GameState;
import nl.mvdr.tinustris.netcode.NetcodeEngine;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test class for {@link NetcodeEngine}.
 * 
 * @author Martijn van de Rijdt
 */
public class NetcodeEngineTest {
    /**
     * Tests {@link NetcodeEngine#addGameState(GameState)} and {@link NetcodeEngine#retrieveLatestGameState()}.
     */
    @Test
    public void testAddAndRetrieve() {
        NetcodeEngine<DummyGameState> holder = new NetcodeEngine<>(Collections.<InputStateHolder> emptyList());

        holder.addGameState(DummyGameState.GAME_NOT_OVER);
        Assert.assertEquals(DummyGameState.GAME_NOT_OVER, holder.retrieveLatestGameState());
    }

    /**
     * Tests {@link NetcodeEngine#addGameState(GameState)} and {@link NetcodeEngine#retrieveLatestGameState()}.
     */
    @Test
    public void testAddAndRetrieveMultipleTimes() {
        NetcodeEngine<DummyGameState> holder = new NetcodeEngine<>(Collections.<InputStateHolder> emptyList());

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
        NetcodeEngine<DummyGameState> holder = new NetcodeEngine<>(Collections.<InputStateHolder> emptyList());

        holder.retrieveLatestGameState();
    }
    
    /** Test the {@link NetcodeEngine#accept(FrameAndInputStatesContainer)} method. */
    @Test
    public void testInputForOtherPlayer() {
        InputStateHolder inputHolder = new InputStateHolder(true);
        NetcodeEngine<DummyGameState> gameStateHolder = 
            new NetcodeEngine<>(Arrays.asList(
                new InputStateHolder(false), inputHolder, new InputStateHolder(false), new InputStateHolder(true)));
        InputState state = i -> true;
        @SuppressWarnings("serial")
        Map<Integer, InputState> inputStates = new HashMap<Integer, InputState>() {{
            put(3, state);
        }};
        
        gameStateHolder.accept(new FrameAndInputStatesContainer(0, inputStates));
        
        Assert.assertNotEquals(state, inputHolder.getInputState());
    }
    
    /** Test the {@link NetcodeEngine#accept(FrameAndInputStatesContainer)} method. */
    @Test
    public void testInputForOwnPlayer() {
        InputStateHolder inputHolder = new InputStateHolder(true);
        NetcodeEngine<DummyGameState> gameStateHolder = 
                new NetcodeEngine<>(Arrays.asList(new InputStateHolder(false), inputHolder));
        InputState state = i -> true;
        @SuppressWarnings("serial")
        Map<Integer, InputState> inputStates = new HashMap<Integer, InputState>() {{
            put(1, state);
        }};
        
        gameStateHolder.accept(new FrameAndInputStatesContainer(0, inputStates));
        
        Assert.assertSame(state, inputHolder.getInputState());
    }
}
