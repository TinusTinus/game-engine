package nl.mvdr.tinustris.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import nl.mvdr.tinustris.input.InputState;
import nl.mvdr.tinustris.input.InputStateHolder;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test class for {@link AllGameStateHolder}.
 * 
 * @author Martijn van de Rijdt
 */
public class AllGameStateHolderTest {
    /**
     * Tests {@link AllGameStateHolder#addGameState(GameState)} and {@link AllGameStateHolder#retrieveLatestGameState()}.
     */
    @Test
    public void testAddAndRetrieve() {
        AllGameStateHolder<DummyGameState> holder = new AllGameStateHolder<>(Collections.<InputStateHolder> emptyList());

        holder.addGameState(DummyGameState.GAME_NOT_OVER);
        Assert.assertEquals(DummyGameState.GAME_NOT_OVER, holder.retrieveLatestGameState());
    }

    /**
     * Tests {@link AllGameStateHolder#addGameState(GameState)} and {@link AllGameStateHolder#retrieveLatestGameState()}.
     */
    @Test
    public void testAddAndRetrieveMultipleTimes() {
        AllGameStateHolder<DummyGameState> holder = new AllGameStateHolder<>(Collections.<InputStateHolder> emptyList());

        holder.addGameState(DummyGameState.GAME_NOT_OVER);
        Assert.assertEquals(DummyGameState.GAME_NOT_OVER, holder.retrieveLatestGameState());
        Assert.assertEquals(DummyGameState.GAME_NOT_OVER, holder.retrieveLatestGameState());
        holder.addGameState(DummyGameState.GAME_OVER);
        Assert.assertEquals(DummyGameState.GAME_OVER, holder.retrieveLatestGameState());
        Assert.assertEquals(DummyGameState.GAME_OVER, holder.retrieveLatestGameState());
    }

    /** Tests {@link AllGameStateHolder#retrieveLatestGameState()} in case no state has been added yet. */
    @Test(expected = NoSuchElementException.class)
    public void testRetrieveWithoutAdd() {
        AllGameStateHolder<DummyGameState> holder = new AllGameStateHolder<>(Collections.<InputStateHolder> emptyList());

        holder.retrieveLatestGameState();
    }
    
    /** Test the {@link AllGameStateHolder#accept(FrameAndInputStatesContainer)} method. */
    @Test
    public void testInputForOtherPlayer() {
        InputStateHolder inputHolder = new InputStateHolder(true);
        AllGameStateHolder<DummyGameState> gameStateHolder = 
                new AllGameStateHolder<>(Arrays.asList(new InputStateHolder(false), inputHolder));
        InputState state = i -> true;
        @SuppressWarnings("serial")
        Map<Integer, InputState> inputStates = new HashMap<Integer, InputState>() {{
            put(3, state);
        }};
        
        gameStateHolder.accept(new FrameAndInputStatesContainer(0, inputStates));
        
        Assert.assertNotEquals(state, inputHolder.getInputState());
    }
    
    /** Test the {@link AllGameStateHolder#accept(FrameAndInputStatesContainer)} method. */
    @Test
    public void testInputForOwnPlayer() {
        InputStateHolder inputHolder = new InputStateHolder(true);
        AllGameStateHolder<DummyGameState> gameStateHolder = 
                new AllGameStateHolder<>(Arrays.asList(new InputStateHolder(false), inputHolder));
        InputState state = i -> true;
        @SuppressWarnings("serial")
        Map<Integer, InputState> inputStates = new HashMap<Integer, InputState>() {{
            put(1, state);
        }};
        
        gameStateHolder.accept(new FrameAndInputStatesContainer(0, inputStates));
        
        Assert.assertSame(state, inputHolder.getInputState());
    }
}
