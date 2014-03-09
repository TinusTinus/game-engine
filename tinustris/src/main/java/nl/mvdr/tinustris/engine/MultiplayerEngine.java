package nl.mvdr.tinustris.engine;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import lombok.ToString;
import nl.mvdr.tinustris.input.InputState;
import nl.mvdr.tinustris.model.MultiplayerGameState;
import nl.mvdr.tinustris.model.OnePlayerGameState;

/**
 * Implementation of {@link GameEngine} for a multiplayer game.
 * 
 * @author Martijn van de Rijdt
 */
@ToString
public class MultiplayerEngine implements GameEngine<MultiplayerGameState> {
    /** Number of players. Determines how many players will be in states created by the {@link #initGameState()} method. */
    private final int numberOfPlayers;
    /** One-player game engine. */
    private final GameEngine<OnePlayerGameState> onePlayerEngine;
    
    /**
     * Constructor.
     * 
     * @param numberOfPlayers
     *            number of players; determines how many players will be in states created by the
     *            {@link #initGameState()} method
     * @param onePlayerEngine
     *            single player game engine (to which most of the computation is offloaded)
     */
    public MultiplayerEngine(int numberOfPlayers, GameEngine<OnePlayerGameState> onePlayerEngine) {
        super();
        
        if (numberOfPlayers < 2) {
            throw new IllegalArgumentException("There need to be at least 2 players, was: " + numberOfPlayers);
        }
        
        this.numberOfPlayers = numberOfPlayers;
        this.onePlayerEngine = onePlayerEngine;
    }
    
    /** {@inheritDoc} */
    @Override
    public MultiplayerGameState initGameState() {
        OnePlayerGameState state = onePlayerEngine.initGameState();
        List<OnePlayerGameState> states = Collections.nCopies(numberOfPlayers, state);
        return new MultiplayerGameState(states);
    }

    /** {@inheritDoc} */
    @Override
    public MultiplayerGameState computeNextState(MultiplayerGameState previousState, List<InputState> inputStates) {
        if (inputStates.size() != previousState.getNumberOfPlayers()) {
            throw new IllegalArgumentException(String.format(
                    "The number of inputs (%s) does not match the number of players (%s).", "" + inputStates.size(),
                    "" + previousState.getNumberOfPlayers()));
        }
        
        List<OnePlayerGameState> states = Collections.unmodifiableList(
            IntStream.range(0, previousState.getNumberOfPlayers())
                .mapToObj(i -> computeNextOnePlayerState(i, previousState, inputStates))
                .collect(Collectors.toList()));
        
        // TODO update garbage counts
        
        return new MultiplayerGameState(states);
    }
    
    /**
     * Computes the next state for the given player index.
     * 
     * @param playerIndex
     *            index of the player
     * @param previousState
     *            previous game state
     * @param inputStates
     *            input states for all players; the length of this list must match the number of players in the game
     * @return new game state
     */
    private OnePlayerGameState computeNextOnePlayerState(int playerIndex, MultiplayerGameState previousState, List<InputState> inputStates) {
        OnePlayerGameState previousOnePlayerState = previousState.getStateForPlayer(playerIndex);
        InputState inputState = inputStates.get(playerIndex);
        return onePlayerEngine.computeNextState(previousOnePlayerState, Arrays.asList(inputState));
    }
}
