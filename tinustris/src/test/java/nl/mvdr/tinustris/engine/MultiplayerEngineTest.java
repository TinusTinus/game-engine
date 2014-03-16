package nl.mvdr.tinustris.engine;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
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
        new MultiplayerEngine(2, new DummyOnePlayerGameEngine());
    }
    
    /** Tests the constructor. */
    @Test
    public void testConstructorThreePlayers() {
        new MultiplayerEngine(3, new DummyOnePlayerGameEngine());
    }
    
    /** Tests the constructor. */
    @Test
    public void testConstructorFourPlayers() {
        new MultiplayerEngine(4, new DummyOnePlayerGameEngine());
    }
    
    /** Tests the constructor. */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorNegativeNumberOfPlayers() {
        new MultiplayerEngine(-2, new DummyOnePlayerGameEngine());
    }
    
    /** Tests the constructor. */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorNoPlayers() {
        new MultiplayerEngine(0, new DummyOnePlayerGameEngine());
    }
    
    /** Tests the constructor. */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorOnePlayer() {
        new MultiplayerEngine(1, new DummyOnePlayerGameEngine());
    }
    
    /** Test the {@link MultiplayerEngine#toString()} method. */
    @Test
    public void testToString() {
        MultiplayerEngine engine = new MultiplayerEngine(2, new DummyOnePlayerGameEngine());
        
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
        MultiplayerEngine engine = new MultiplayerEngine(numberOfPlayers, new DummyOnePlayerGameEngine());
        
        MultiplayerGameState state = engine.initGameState();
        
        Assert.assertEquals(numberOfPlayers, state.getNumberOfPlayers());
    }
    
    /** Test case for {@link MultiplayerEngine#computeNextState(MultiplayerGameState, List)} */
    @Test
    public void testComputeNextState() {
        MultiplayerEngine engine = new MultiplayerEngine(2, new DummyOnePlayerGameEngine());
        OnePlayerGameState onePlayerState = new OnePlayerGameState();
        MultiplayerGameState state = new MultiplayerGameState(Arrays.asList(onePlayerState, onePlayerState), Arrays.asList(1, 0));
        InputState inputState = input -> false;

        MultiplayerGameState newState = engine.computeNextState(state, Arrays.asList(inputState, inputState));

        log.info(newState.toString());
        Assert.assertEquals(2, newState.getNumberOfPlayers());
    }
    
    /** Test case for {@link MultiplayerEngine#computeNextState(MultiplayerGameState, List)} */
    @Test(expected = IllegalArgumentException.class)
    public void testComputeNextStateTooFewInputs() {
        MultiplayerEngine engine = new MultiplayerEngine(2, new DummyOnePlayerGameEngine());
        OnePlayerGameState onePlayerState = new OnePlayerGameState();
        MultiplayerGameState state = new MultiplayerGameState(Arrays.asList(onePlayerState, onePlayerState), Arrays.asList(1, 0));
        InputState inputState = input -> false;

        engine.computeNextState(state, Arrays.asList(inputState));
    }
    
    /** Test case for {@link MultiplayerEngine#computeNextState(MultiplayerGameState, List)} */
    @Test(expected = IllegalArgumentException.class)
    public void testComputeNextStateTooManyInputs() {
        MultiplayerEngine engine = new MultiplayerEngine(2, new DummyOnePlayerGameEngine());
        OnePlayerGameState onePlayerState = new OnePlayerGameState();
        MultiplayerGameState state = new MultiplayerGameState(Arrays.asList(onePlayerState, onePlayerState), Arrays.asList(1, 0));
        InputState inputState = input -> false;

        engine.computeNextState(state, Arrays.asList(inputState, inputState, inputState));
    }
    
    /** Test case for {@link MultiplayerEngine#computeNextState(MultiplayerGameState, List)}. */
    @Test(expected = IllegalArgumentException.class)
    public void testComputeNextStateNoInputs() {
        MultiplayerEngine engine = new MultiplayerEngine(2, new DummyOnePlayerGameEngine());
        OnePlayerGameState onePlayerState = new OnePlayerGameState();
        MultiplayerGameState state = new MultiplayerGameState(Arrays.asList(onePlayerState, onePlayerState), Arrays.asList(1, 0));

        engine.computeNextState(state, Collections.<InputState>emptyList());
    }

    /**
     * Test case for {@link MultiplayerEngine#computeNextState(MultiplayerGameState, List)}, with two players, where one
     * player scores a single. This should not result in any garbage lines.
     */
    @Test
    public void testAddGarbageLineTwoPlayersSingle() {
        OnePlayerGameState playerOneState = new OnePlayerGameState();
        OnePlayerGameState playerTwoState = new OnePlayerGameState();
        MultiplayerGameState previousState = new MultiplayerGameState(Arrays.asList(playerOneState, playerTwoState), Arrays.asList(1, 0));
        DummyOnePlayerGameEngine onePlayerEngine = new DummyOnePlayerGameEngine() {
            /** {@inheritDoc} */
            @Override
            public OnePlayerGameState computeNextState(OnePlayerGameState previousState, List<InputState> inputStates) {
                OnePlayerGameState result;
                if (previousState == playerOneState) {
                    result = previousState.withLines(1);
                } else {
                    result = previousState;
                }
                return result;
            }
        };
        MultiplayerEngine engine = new MultiplayerEngine(2, onePlayerEngine);
        
        MultiplayerGameState newState = engine.computeNextState(previousState, Collections.nCopies(2, input -> false));
        
        Assert.assertEquals(Arrays.asList(1, 0), newState.getNextGarbageTargets());
        Assert.assertEquals(0, newState.getStateForPlayer(0).getGarbageLines());
        Assert.assertEquals(0, newState.getStateForPlayer(0).getGarbageLines());
    }

    
    /**
     * Test case for {@link MultiplayerEngine#computeNextState(MultiplayerGameState, List)}, with two players, where one
     * player scores a double. This should result in one garbage line for the opponent.
     */
    @Test
    public void testAddGarbageLineTwoPlayersDouble() {
        OnePlayerGameState playerOneState = new OnePlayerGameState();
        OnePlayerGameState playerTwoState = new OnePlayerGameState();
        MultiplayerGameState previousState = new MultiplayerGameState(Arrays.asList(playerOneState, playerTwoState), Arrays.asList(1, 0));
        DummyOnePlayerGameEngine onePlayerEngine = new DummyOnePlayerGameEngine() {
            /** {@inheritDoc} */
            @Override
            public OnePlayerGameState computeNextState(OnePlayerGameState previousState, List<InputState> inputStates) {
                OnePlayerGameState result;
                if (previousState == playerOneState) {
                    result = previousState.withLines(2);
                } else {
                    result = previousState;
                }
                return result;
            }
        };
        MultiplayerEngine engine = new MultiplayerEngine(2, onePlayerEngine);
        
        MultiplayerGameState newState = engine.computeNextState(previousState, Collections.nCopies(2, input -> false));
        
        Assert.assertEquals(Arrays.asList(1, 0), newState.getNextGarbageTargets());
        Assert.assertEquals(0, newState.getStateForPlayer(0).getGarbageLines());
        Assert.assertEquals(1, newState.getStateForPlayer(1).getGarbageLines());
    }
    
    /**
     * Test case for {@link MultiplayerEngine#computeNextState(MultiplayerGameState, List)}, with two players, where one
     * player scores a triple. This should result in two garbage lines for the opponent.
     */
    @Test
    public void testAddGarbageLineTwoPlayersTriple() {
        OnePlayerGameState playerOneState = new OnePlayerGameState();
        OnePlayerGameState playerTwoState = new OnePlayerGameState();
        MultiplayerGameState previousState = new MultiplayerGameState(Arrays.asList(playerOneState, playerTwoState), Arrays.asList(1, 0));
        DummyOnePlayerGameEngine onePlayerEngine = new DummyOnePlayerGameEngine() {
            /** {@inheritDoc} */
            @Override
            public OnePlayerGameState computeNextState(OnePlayerGameState previousState, List<InputState> inputStates) {
                OnePlayerGameState result;
                if (previousState == playerOneState) {
                    result = previousState.withLines(3);
                } else {
                    result = previousState;
                }
                return result;
            }
        };
        MultiplayerEngine engine = new MultiplayerEngine(2, onePlayerEngine);
        
        MultiplayerGameState newState = engine.computeNextState(previousState, Collections.nCopies(2, input -> false));
        
        Assert.assertEquals(Arrays.asList(1, 0), newState.getNextGarbageTargets());
        Assert.assertEquals(0, newState.getStateForPlayer(0).getGarbageLines());
        Assert.assertEquals(2, newState.getStateForPlayer(1).getGarbageLines());
    }
    
    /**
     * Test case for {@link MultiplayerEngine#computeNextState(MultiplayerGameState, List)}, with two players, where one
     * player scores a tetris. This should result in four garbage lines for the opponent.
     */
    @Test
    public void testAddGarbageLineTwoPlayersTetris() {
        OnePlayerGameState playerOneState = new OnePlayerGameState();
        OnePlayerGameState playerTwoState = new OnePlayerGameState();
        MultiplayerGameState previousState = new MultiplayerGameState(Arrays.asList(playerOneState, playerTwoState), Arrays.asList(1, 0));
        DummyOnePlayerGameEngine onePlayerEngine = new DummyOnePlayerGameEngine() {
            /** {@inheritDoc} */
            @Override
            public OnePlayerGameState computeNextState(OnePlayerGameState previousState, List<InputState> inputStates) {
                OnePlayerGameState result;
                if (previousState == playerOneState) {
                    result = previousState.withLines(4);
                } else {
                    result = previousState;
                }
                return result;
            }
        };
        MultiplayerEngine engine = new MultiplayerEngine(2, onePlayerEngine);
        
        MultiplayerGameState newState = engine.computeNextState(previousState, Collections.nCopies(2, input -> false));
        
        Assert.assertEquals(Arrays.asList(1, 0), newState.getNextGarbageTargets());
        Assert.assertEquals(0, newState.getStateForPlayer(0).getGarbageLines());
        Assert.assertEquals(4, newState.getStateForPlayer(1).getGarbageLines());
    }
    
    /**
     * Test case for {@link MultiplayerEngine#computeNextState(MultiplayerGameState, List)}, with three players, where one
     * player scores a double. This should result in one garbage line for their targeted opponent.
     */
    @Test
    public void testAddGarbageLineThreePlayersDouble() {
        OnePlayerGameState playerOneState = new OnePlayerGameState();
        OnePlayerGameState playerTwoState = new OnePlayerGameState();
        OnePlayerGameState playerThreeState = new OnePlayerGameState();
        MultiplayerGameState previousState = new MultiplayerGameState(Arrays.asList(playerOneState, playerTwoState, playerThreeState), Arrays.asList(1, 2, 0));
        DummyOnePlayerGameEngine onePlayerEngine = new DummyOnePlayerGameEngine() {
            /** {@inheritDoc} */
            @Override
            public OnePlayerGameState computeNextState(OnePlayerGameState previousState, List<InputState> inputStates) {
                OnePlayerGameState result;
                if (previousState == playerOneState) {
                    result = previousState.withLines(2);
                } else {
                    result = previousState;
                }
                return result;
            }
        };
        MultiplayerEngine engine = new MultiplayerEngine(3, onePlayerEngine);
        
        MultiplayerGameState newState = engine.computeNextState(previousState, Collections.nCopies(3, input -> false));
        
        Assert.assertEquals(Arrays.asList(2, 2, 0), newState.getNextGarbageTargets());
        Assert.assertEquals(0, newState.getStateForPlayer(0).getGarbageLines());
        Assert.assertEquals(1, newState.getStateForPlayer(1).getGarbageLines());
        Assert.assertEquals(0, newState.getStateForPlayer(2).getGarbageLines());
    }

    /**
     * Test case for {@link MultiplayerEngine#computeNextState(MultiplayerGameState, List)}, with three players, where one
     * player scores a triple. This should result in one garbage line for both opponents.
     */
    @Test
    public void testAddGarbageLineThreePlayersTriple() {
        OnePlayerGameState playerOneState = new OnePlayerGameState();
        OnePlayerGameState playerTwoState = new OnePlayerGameState();
        OnePlayerGameState playerThreeState = new OnePlayerGameState();
        MultiplayerGameState previousState = new MultiplayerGameState(Arrays.asList(playerOneState, playerTwoState, playerThreeState), Arrays.asList(1, 2, 0));
        DummyOnePlayerGameEngine onePlayerEngine = new DummyOnePlayerGameEngine() {
            /** {@inheritDoc} */
            @Override
            public OnePlayerGameState computeNextState(OnePlayerGameState previousState, List<InputState> inputStates) {
                OnePlayerGameState result;
                if (previousState == playerOneState) {
                    result = previousState.withLines(3);
                } else {
                    result = previousState;
                }
                return result;
            }
        };
        MultiplayerEngine engine = new MultiplayerEngine(3, onePlayerEngine);
        
        MultiplayerGameState newState = engine.computeNextState(previousState, Collections.nCopies(3, input -> false));
        
        Assert.assertEquals(Arrays.asList(1, 2, 0), newState.getNextGarbageTargets());
        Assert.assertEquals(0, newState.getStateForPlayer(0).getGarbageLines());
        Assert.assertEquals(1, newState.getStateForPlayer(1).getGarbageLines());
        Assert.assertEquals(1, newState.getStateForPlayer(2).getGarbageLines());
    }
    
    /**
     * Test case for {@link MultiplayerEngine#computeNextState(MultiplayerGameState, List)}, with three players, where one
     * player scores a Tetris. This should result in two garbage lines for both opponents.
     */
    @Test
    public void testAddGarbageLineThreePlayersTetris() {
        OnePlayerGameState playerOneState = new OnePlayerGameState();
        OnePlayerGameState playerTwoState = new OnePlayerGameState();
        OnePlayerGameState playerThreeState = new OnePlayerGameState();
        MultiplayerGameState previousState = new MultiplayerGameState(Arrays.asList(playerOneState, playerTwoState, playerThreeState), Arrays.asList(1, 2, 0));
        DummyOnePlayerGameEngine onePlayerEngine = new DummyOnePlayerGameEngine() {
            /** {@inheritDoc} */
            @Override
            public OnePlayerGameState computeNextState(OnePlayerGameState previousState, List<InputState> inputStates) {
                OnePlayerGameState result;
                if (previousState == playerOneState) {
                    result = previousState.withLines(4);
                } else {
                    result = previousState;
                }
                return result;
            }
        };
        MultiplayerEngine engine = new MultiplayerEngine(3, onePlayerEngine);
        
        MultiplayerGameState newState = engine.computeNextState(previousState, Collections.nCopies(3, input -> false));
        
        Assert.assertEquals(Arrays.asList(1, 2, 0), newState.getNextGarbageTargets());
        Assert.assertEquals(0, newState.getStateForPlayer(0).getGarbageLines());
        Assert.assertEquals(2, newState.getStateForPlayer(1).getGarbageLines());
        Assert.assertEquals(2, newState.getStateForPlayer(2).getGarbageLines());
    }
}
