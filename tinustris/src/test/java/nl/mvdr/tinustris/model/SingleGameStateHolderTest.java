package nl.mvdr.tinustris.model;

import java.util.NoSuchElementException;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test class for {@link SingleGameStateHolder}.
 * 
 * @author Martijn van de Rijdt
 */
public class SingleGameStateHolderTest {
    /**
     * Tests {@link SingleGameStateHolder#addGameState(GameState)} and
     * {@link SingleGameStateHolder#retrieveLatestGameState()}.
     */
    @Test
    public void testAddAndRetrieve() {
        SingleGameStateHolder<DummyGameState> holder = new SingleGameStateHolder<>();

        holder.addGameState(DummyGameState.GAME_NOT_OVER);
        Assert.assertEquals(DummyGameState.GAME_NOT_OVER, holder.retrieveLatestGameState());
    }
    
    /**
     * Tests {@link SingleGameStateHolder#addGameState(GameState)} and
     * {@link SingleGameStateHolder#retrieveLatestGameState()}.
     */
    @Test
    public void testAddAndRetrieveMultipleTimes() {
        SingleGameStateHolder<DummyGameState> holder = new SingleGameStateHolder<>();

        holder.addGameState(DummyGameState.GAME_NOT_OVER);
        Assert.assertEquals(DummyGameState.GAME_NOT_OVER, holder.retrieveLatestGameState());
        Assert.assertEquals(DummyGameState.GAME_NOT_OVER, holder.retrieveLatestGameState());
        holder.addGameState(DummyGameState.GAME_OVER);
        Assert.assertEquals(DummyGameState.GAME_OVER, holder.retrieveLatestGameState());
        Assert.assertEquals(DummyGameState.GAME_OVER, holder.retrieveLatestGameState());
    }

    /** Tests {@link SingleGameStateHolder#retrieveLatestGameState()} in case no state has been added yet. */
    @Test(expected = NoSuchElementException.class)
    public void testRetrieveWithoutAdd() {
        SingleGameStateHolder<DummyGameState> holder = new SingleGameStateHolder<>();

        holder.retrieveLatestGameState();
    }
}
