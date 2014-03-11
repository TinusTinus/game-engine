package nl.mvdr.tinustris.model;

import java.util.Arrays;
import java.util.Collections;

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
    /** Test case for {@link MultiplayerGameState#MultiplayerGameState(List<OnePlayerGameState>)}. */
    @Test(expected = NullPointerException.class)
    public void testConstructorNullList() {
        new MultiplayerGameState(null);
    }

    /** Test case for {@link MultiplayerGameState#MultiplayerGameState(List<OnePlayerGameState>)}. */
    @Test(expected = NullPointerException.class)
    public void testConstructorNullValues() {
        new MultiplayerGameState(Arrays.<OnePlayerGameState>asList(null, null));
    }

    /** Test case for {@link MultiplayerGameState#MultiplayerGameState(List<OnePlayerGameState>)}. */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorOneValue() {
        OnePlayerGameState state = new OnePlayerGameState();

        new MultiplayerGameState(Collections.singletonList(state));
    }

    /** Test case for {@link MultiplayerGameState#MultiplayerGameState(List<OnePlayerGameState>)}. */
    @Test(expected = NullPointerException.class)
    public void testConstructorFirstValueNull() {
        OnePlayerGameState state = new OnePlayerGameState();

        new MultiplayerGameState(Arrays.asList(null, state));
    }
    
    /** Test case for {@link MultiplayerGameState#MultiplayerGameState(List<OnePlayerGameState>)}. */
    @Test(expected = NullPointerException.class)
    public void testConstructorSecondValueNull() {
        OnePlayerGameState state = new OnePlayerGameState();

        new MultiplayerGameState(Arrays.asList(state, null));
    }
    
    /** Test case for {@link MultiplayerGameState#MultiplayerGameState(List<OnePlayerGameState>)}. */
    @Test
    public void testConstructorTwoPlayers() {
        OnePlayerGameState state = new OnePlayerGameState();

        new MultiplayerGameState(Arrays.asList(state, state));
    }

    /** Test case for {@link MultiplayerGameState#MultiplayerGameState(List<OnePlayerGameState>)}. */
    @Test
    public void testConstructorThreePlayers() {
        OnePlayerGameState state = new OnePlayerGameState();

        new MultiplayerGameState(Arrays.asList(state, state, state));
    }

    /**
     * Test case for {@link MultiplayerGameState#MultiplayerGameState(List<OnePlayerGameState>)}.
     * 
     * Passes in a null value. One value is too few arguments (there should be at least two players), and null values
     * are not allowed. Depending on the order of validation we should get either a NullPointerException or an
     * IllegalArgumentException.
     */
    @Test(expected = RuntimeException.class)
    public void testConstructorNullValue() {
        new MultiplayerGameState(Arrays.asList((OnePlayerGameState)null));
    }
    
    /**
     * Test case for {@link MultiplayerGameState#MultiplayerGameState(java.util.List, java.util.List)} where the target
     * list is null.
     */
    @Test(expected = NullPointerException.class)
    public void testConstructorNullTargets() {
        OnePlayerGameState state = new OnePlayerGameState();
        
        new MultiplayerGameState(Arrays.asList(state, state, state), null);
    }
    
    /**
     * Test case for {@link MultiplayerGameState#MultiplayerGameState(java.util.List, java.util.List)} where the sizes
     * of the two lists do not match.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorNonMatchingSizes() {
        OnePlayerGameState state = new OnePlayerGameState();
        
        new MultiplayerGameState(Arrays.asList(state, state, state), Arrays.asList(2, 1));
    }

    /**
     * Test case for {@link MultiplayerGameState#MultiplayerGameState(java.util.List, java.util.List)} where the targets
     * list contains a null value.
     */
    @Test(expected = NullPointerException.class)
    public void testConstructorNullTarget() {
        OnePlayerGameState state = new OnePlayerGameState();
        
        new MultiplayerGameState(Arrays.asList(state, state, state), Arrays.asList(2, 1, null));
    }
    
    // TODO more test cases!
    
    /** Test case for {@link MultiplayerGameState#toString()}. */
    @Test
    public void testToString() {
        OnePlayerGameState state = new OnePlayerGameState();
        MultiplayerGameState mulitplayerState = new MultiplayerGameState(Arrays.asList(state, state));

        String string = mulitplayerState.toString();

        log.info(string);
        Assert.assertNotNull(string);
        Assert.assertNotEquals("", string);
    }
    
    /** Test case for {@link MultiplayerGameState#isGameOver()}. */
    @Test
    public void testGameOverTwoActivePlayers() {
        OnePlayerGameState state = new OnePlayerGameState();
        MultiplayerGameState mulitplayerState = new MultiplayerGameState(Arrays.asList(state, state));

        Assert.assertFalse(mulitplayerState.isGameOver());
    }
    
    /** Test case for {@link MultiplayerGameState#isGameOver()}. */
    @Test
    public void testGameOverTwoToppedPlayers() {
        OnePlayerGameState state = createToppedState();
        MultiplayerGameState mulitplayerState = new MultiplayerGameState(Arrays.asList(state, state));

        Assert.assertTrue(mulitplayerState.isGameOver());
    }
    
    /** Test case for {@link MultiplayerGameState#isGameOver()}. */
    @Test
    public void testGameOverOneActiveOneToppedPlayer() {
        OnePlayerGameState activeState = new OnePlayerGameState();
        OnePlayerGameState toppedState = createToppedState();
        MultiplayerGameState mulitplayerState = new MultiplayerGameState(Arrays.asList(activeState, toppedState));

        Assert.assertTrue(mulitplayerState.isGameOver());
    }

    /** Test case for {@link MultiplayerGameState#isGameOver()}. */
    @Test
    public void testGameOverOneToppedOneActivePlayer() {
        OnePlayerGameState toppedState = createToppedState();
        OnePlayerGameState activeState = new OnePlayerGameState();
        MultiplayerGameState mulitplayerState = new MultiplayerGameState(Arrays.asList(toppedState, activeState));

        Assert.assertTrue(mulitplayerState.isGameOver());
    }
    
    /** Test case for {@link MultiplayerGameState#isGameOver()}. */
    @Test
    public void testGameOverThreeActivePlayers() {
        OnePlayerGameState state = new OnePlayerGameState();
        MultiplayerGameState multiplayerState = new MultiplayerGameState(Arrays.asList(state, state, state));
        
        Assert.assertFalse(multiplayerState.isGameOver());
    }
    
    /** Test case for {@link MultiplayerGameState#isGameOver()}. */
    @Test
    public void testGameOverThreeToppedPlayers() {
        OnePlayerGameState state = createToppedState();
        MultiplayerGameState multiplayerState = new MultiplayerGameState(Arrays.asList(state, state, state));
        
        Assert.assertTrue(multiplayerState.isGameOver());
    }
    
    /** Test case for {@link MultiplayerGameState#isGameOver()}. */
    @Test
    public void testGameOverOneActiveTwoToppedPlayers() {
        OnePlayerGameState activeState = new OnePlayerGameState();
        OnePlayerGameState toppedState = createToppedState();
        MultiplayerGameState multiplayerState = new MultiplayerGameState(Arrays.asList(activeState, toppedState,
                toppedState));
        
        Assert.assertTrue(multiplayerState.isGameOver());
    }
    
    /** Test case for {@link MultiplayerGameState#isGameOver()}. */
    @Test
    public void testGameOverTwoActiveOneToppedPlayers() {
        OnePlayerGameState activeState = new OnePlayerGameState();
        OnePlayerGameState toppedState = createToppedState();
        MultiplayerGameState multiplayerState = new MultiplayerGameState(Arrays.asList(activeState, activeState,
                toppedState));
        
        // game should not be over as long as there are at least two active players
        Assert.assertFalse(multiplayerState.isGameOver());
    }
    
    /** 
     * Creates a topped single player game state.
     * 
     * @return state
     */
    private OnePlayerGameState createToppedState() {
        return new OnePlayerGameState() {
            /** 
             * {@inheritDoc}
             * 
             * Dummy implementation which always returns true.
             */
            @Override
            public boolean isTopped() {
                return true;
            }
        };
    }
    
    /** Test case for {@link MultiplayerGameState#getNumberOfPlayers()}. */
    @Test
    public void testGetNumberOfPlayers2() {
        OnePlayerGameState state = new OnePlayerGameState();
        MultiplayerGameState mulitplayerState = new MultiplayerGameState(Arrays.asList(state, state));

        Assert.assertEquals(2, mulitplayerState.getNumberOfPlayers());
    }
    
    /** Test case for {@link MultiplayerGameState#getNumberOfPlayers()}. */
    @Test
    public void testGetNumberOfPlayers3() {
        OnePlayerGameState state = new OnePlayerGameState();
        MultiplayerGameState mulitplayerState = new MultiplayerGameState(Arrays.asList(state, state, state));

        Assert.assertEquals(3, mulitplayerState.getNumberOfPlayers());
    }
    
    /** Test case for {@link MultiplayerGameState#getStateForPlayer(int)}. */
    @Test
    public void testGetStateForPlayer0() {
        OnePlayerGameState state = new OnePlayerGameState();
        MultiplayerGameState mulitplayerState = new MultiplayerGameState(Arrays.asList(state, state));

        Assert.assertNotNull(mulitplayerState.getStateForPlayer(0));
    }
    
    /** Test case for {@link MultiplayerGameState#getStateForPlayer(int)}. */
    @Test
    public void testGetStateForPlayer1() {
        OnePlayerGameState state = new OnePlayerGameState();
        MultiplayerGameState mulitplayerState = new MultiplayerGameState(Arrays.asList(state, state));

        Assert.assertNotNull(mulitplayerState.getStateForPlayer(1));
    }

    /** Test case for {@link MultiplayerGameState#getStateForPlayer(int)}. */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testGetStateForPlayerNegative() {
        OnePlayerGameState state = new OnePlayerGameState();
        MultiplayerGameState mulitplayerState = new MultiplayerGameState(Arrays.asList(state, state));

        mulitplayerState.getStateForPlayer(-1);
    }
    
    /** Test case for {@link MultiplayerGameState#getStateForPlayer(int)}. */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testGetStateForPlayerTooLarge() {
        OnePlayerGameState state = new OnePlayerGameState();
        MultiplayerGameState mulitplayerState = new MultiplayerGameState(Arrays.asList(state, state));

        mulitplayerState.getStateForPlayer(3);
    }
}
