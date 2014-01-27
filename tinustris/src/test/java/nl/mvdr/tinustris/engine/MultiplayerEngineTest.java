package nl.mvdr.tinustris.engine;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import nl.mvdr.tinustris.input.DummyInputController;
import nl.mvdr.tinustris.input.InputState;
import nl.mvdr.tinustris.model.MultiplayerGameState;
import nl.mvdr.tinustris.model.OnePlayerGameState;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test class for {@link MultiplayerEngine}.
 * 
 * @author Martijn van de Rijdt
 */
@Slf4j
public class MultiplayerEngineTest {
    /** Tests the constructor. */
    @Test
    public void testConstructorTwoPlayers() {
        new MultiplayerEngine(2);
    }
    
    /** Tests the constructor. */
    @Test
    public void testConstructorThreePlayers() {
        new MultiplayerEngine(3);
    }
    
    /** Tests the constructor. */
    @Test
    public void testConstructorFourPlayers() {
        new MultiplayerEngine(4);
    }
    
    /** Tests the constructor. */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorNegativeNumberOfPlayers() {
        new MultiplayerEngine(-2);
    }
    
    /** Tests the constructor. */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorNoPlayers() {
        new MultiplayerEngine(0);
    }
    
    /** Tests the constructor. */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorOnePlayer() {
        new MultiplayerEngine(1);
    }
    
    /** Test the {@link MultiplayerEngine#toString()} method. */
    @Test
    public void testToString() {
        MultiplayerEngine engine = new MultiplayerEngine(2);
        
        String string = engine.toString();
        
        log.info(string);
        Assert.assertNotNull(string);
        Assert.assertNotEquals("", string);
    }
    
    /** Test case for {@link MultiplayerEngine#initGameState()} with two players. */
    @Test
    public void testInitGameStateTwoPlayers() {
        testInitGameState(2);
    }
    
    /** Test case for {@link MultiplayerEngine#initGameState()} with three players. */
    @Test
    public void testInitGameStateThreePlayers() {
        testInitGameState(3);
    }
    
    /** Test case for {@link MultiplayerEngine#initGameState()} with four players. */
    @Test
    public void testInitGameStateFourPlayers() {
        testInitGameState(4);
    }
    
    /** Test case for {@link MultiplayerEngine#initGameState()} with 64 players. */
    @Test
    public void testInitGameState64Players() {
        testInitGameState(64);
    }
    
    /** 
     * Test case for {@link MultiplayerEngine#initGameState()}.
     * 
     * @param numberOfPlayers number of players
     */
    private void testInitGameState(int numberOfPlayers) {
        MultiplayerEngine engine = new MultiplayerEngine(numberOfPlayers);
        
        MultiplayerGameState state = engine.initGameState();
        
        Assert.assertEquals(numberOfPlayers, state.getNumberOfPlayers());
    }
    
    /** Test case for {@link MultiplayerEngine#computeNextState(MultiplayerGameState, List)} */
    @Test
    public void testComputeNextState() {
        MultiplayerEngine engine = new MultiplayerEngine(2);
        OnePlayerGameState onePlayerState = new OnePlayerGameState();
        MultiplayerGameState state = new MultiplayerGameState(onePlayerState, onePlayerState);
        InputState inputState = new DummyInputController().getInputState();

        MultiplayerGameState newState = engine.computeNextState(state, Arrays.asList(inputState, inputState));

        log.info(newState.toString());
        Assert.assertEquals(2, newState.getNumberOfPlayers());
    }
    
    /** Test case for {@link MultiplayerEngine#computeNextState(MultiplayerGameState, List)} */
    @Test(expected = IllegalArgumentException.class)
    public void testComputeNextStateTooFewInputs() {
        MultiplayerEngine engine = new MultiplayerEngine(2);
        OnePlayerGameState onePlayerState = new OnePlayerGameState();
        MultiplayerGameState state = new MultiplayerGameState(onePlayerState, onePlayerState);
        InputState inputState = new DummyInputController().getInputState();

        engine.computeNextState(state, Arrays.asList(inputState));
    }
    
    /** Test case for {@link MultiplayerEngine#computeNextState(MultiplayerGameState, List)} */
    @Test(expected = IllegalArgumentException.class)
    public void testComputeNextStateTooManyInputs() {
        MultiplayerEngine engine = new MultiplayerEngine(2);
        OnePlayerGameState onePlayerState = new OnePlayerGameState();
        MultiplayerGameState state = new MultiplayerGameState(onePlayerState, onePlayerState);
        InputState inputState = new DummyInputController().getInputState();

        engine.computeNextState(state, Arrays.asList(inputState, inputState, inputState));
    }
    
    /** Test case for {@link MultiplayerEngine#computeNextState(MultiplayerGameState, List)} */
    @Test(expected = IllegalArgumentException.class)
    public void testComputeNextStateNoInputs() {
        MultiplayerEngine engine = new MultiplayerEngine(2);
        OnePlayerGameState onePlayerState = new OnePlayerGameState();
        MultiplayerGameState state = new MultiplayerGameState(onePlayerState, onePlayerState);

        engine.computeNextState(state, Collections.<InputState>emptyList());
    }
}