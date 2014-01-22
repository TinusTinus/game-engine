package nl.mvdr.tinustris.model;

import lombok.extern.slf4j.Slf4j;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test class for {@link MultiplayerGameState}.
 * 
 * @author Martijn van de Rijdt
 */
@Slf4j
public class MultiplayerGameStateTest {
    /** Test case for {@link MultiplayerGameState#MultiplayerGameState(OnePlayerGameState...)}. */
    @Test(expected = NullPointerException.class)
    public void testConstructorNullArray() {
        new MultiplayerGameState((OnePlayerGameState[]) null);
    }

    /** Test case for {@link MultiplayerGameState#MultiplayerGameState(OnePlayerGameState...)}. */
    @Test(expected = NullPointerException.class)
    public void testConstructorNullValues() {
        new MultiplayerGameState(null, null);
    }

    /** Test case for {@link MultiplayerGameState#MultiplayerGameState(OnePlayerGameState...)}. */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorOneValue() {
        OnePlayerGameState state = new OnePlayerGameState();

        new MultiplayerGameState(state);
    }

    /** Test case for {@link MultiplayerGameState#MultiplayerGameState(OnePlayerGameState...)}. */
    @Test(expected = NullPointerException.class)
    public void testConstructorFirstValueNull() {
        OnePlayerGameState state = new OnePlayerGameState();

        new MultiplayerGameState(null, state);
    }
    
    /** Test case for {@link MultiplayerGameState#MultiplayerGameState(OnePlayerGameState...)}. */
    @Test(expected = NullPointerException.class)
    public void testConstructorSecondValueNull() {
        OnePlayerGameState state = new OnePlayerGameState();

        new MultiplayerGameState(state, null);
    }
    
    /** Test case for {@link MultiplayerGameState#MultiplayerGameState(OnePlayerGameState...)}. */
    @Test
    public void testConstructorTwoPlayers() {
        OnePlayerGameState state = new OnePlayerGameState();

        new MultiplayerGameState(state, state);
    }

    /** Test case for {@link MultiplayerGameState#MultiplayerGameState(OnePlayerGameState...)}. */
    @Test
    public void testConstructorThreePlayers() {
        OnePlayerGameState state = new OnePlayerGameState();

        new MultiplayerGameState(state, state, state);
    }

    /**
     * Test case for {@link MultiplayerGameState#MultiplayerGameState(OnePlayerGameState...)}.
     * 
     * Passes in a null value. One value is too few arguments (there should be at least two players), and null values
     * are not allowed. Depending on the order of validation we should get either a NullPointerException or an
     * IllegalArgumentException.
     */
    @Test(expected = RuntimeException.class)
    public void testConstructorNullValue() {
        new MultiplayerGameState((OnePlayerGameState) null);
    }

    /** Test case for {@link MultiplayerGameState#toString()}. */
    @Test
    public void testToString() {
        OnePlayerGameState state = new OnePlayerGameState();
        MultiplayerGameState mulitplayerState = new MultiplayerGameState(state, state);

        String string = mulitplayerState.toString();

        log.info(string);
        Assert.assertNotNull(string);
        Assert.assertNotEquals("", string);
    }
}
