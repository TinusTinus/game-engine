package nl.mvdr.tinustris.model;

import java.util.NoSuchElementException;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test class for {@link AllGameStateHolder}.
 * 
 * @author Martijn van de Rijdt
 */
public class AllGameStateHolderTest {
    /**
     * Tests {@link AllGameStateHolder#addGameState(GameState)} and
     * {@link AllGameStateHolder#retrieveLatestGameState()}.
     */
    @Test
    public void testAddAndRetrieve() {
        AllGameStateHolder<DummyGameState> holder = new AllGameStateHolder<>();

        holder.addGameState(DummyGameState.GAME_NOT_OVER);
        Assert.assertEquals(DummyGameState.GAME_NOT_OVER, holder.retrieveLatestGameState());
    }
    
    /**
     * Tests {@link AllGameStateHolder#addGameState(GameState)} and
     * {@link AllGameStateHolder#retrieveLatestGameState()}.
     */
    @Test
    public void testAddAndRetrieveMultipleTimes() {
        AllGameStateHolder<DummyGameState> holder = new AllGameStateHolder<>();

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
        AllGameStateHolder<DummyGameState> holder = new AllGameStateHolder<>();

        holder.retrieveLatestGameState();
    }
}
