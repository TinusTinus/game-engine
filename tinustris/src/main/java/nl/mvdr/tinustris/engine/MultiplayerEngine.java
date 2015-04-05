package nl.mvdr.tinustris.engine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import lombok.ToString;
import nl.mvdr.game.input.InputState;
import nl.mvdr.tinustris.input.Input;
import nl.mvdr.tinustris.model.MultiplayerGameState;
import nl.mvdr.tinustris.model.OnePlayerGameState;

/**
 * Implementation of {@link GameEngine} for a multiplayer game.
 * 
 * @author Martijn van de Rijdt
 */
@ToString
public class MultiplayerEngine implements GameEngine<MultiplayerGameState, Input> {
    /** Number of players. Determines how many players will be in states created by the {@link #initGameState()} method. */
    private final int numberOfPlayers;
    /** One-player game engine. */
    private final GameEngine<OnePlayerGameState, Input> onePlayerEngine;
    
    /**
     * Constructor.
     * 
     * @param numberOfPlayers
     *            number of players; determines how many players will be in states created by the
     *            {@link #initGameState()} method
     * @param onePlayerEngine
     *            single player game engine (to which most of the computation is offloaded)
     */
    public MultiplayerEngine(int numberOfPlayers, GameEngine<OnePlayerGameState, Input> onePlayerEngine) {
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
        
        List<Integer> targets = MultiplayerGameState.defaultTargets(numberOfPlayers);
        
        return new MultiplayerGameState(states, targets);
    }

    /** {@inheritDoc} */
    @Override
    public MultiplayerGameState computeNextState(MultiplayerGameState previousState, List<InputState<Input>> inputStates) {
        // check the input
        if (inputStates.size() != previousState.getNumberOfPlayers()) {
            throw new IllegalArgumentException(String.format(
                    "The number of inputs (%s) does not match the number of players (%s).", "" + inputStates.size(),
                    "" + previousState.getNumberOfPlayers()));
        }
        
        // compute next game state using the one player engine
        List<OnePlayerGameState> states = IntStream.range(0, previousState.getNumberOfPlayers())
                .mapToObj(i -> computeNextOnePlayerState(i, previousState, inputStates))
                .collect(Collectors.toCollection(ArrayList<OnePlayerGameState>::new));
        List<Integer> targets = new ArrayList<>(previousState.getNextGarbageTargets());

        // add any garbage lines
        for (int i = 0; i != states.size(); i++) {
            int linesScored = states.get(i).getLines() - previousState.getStateForPlayer(i).getLines();

            for (int garbageLines = computeGarbageLines(linesScored); 0 < garbageLines; garbageLines--) {
                // update the state for player i's target
                int target = targets.get(i).intValue();
                OnePlayerGameState targetState = states.get(target);
                targetState = targetState.withGarbageLines(targetState.getGarbageLines() + 1);
                states.set(target, targetState);
                
                // update the target to the next player
                target = (target + 1) % targets.size();
                if (target == i) {
                    // players may not target themselves, skip to the next opponent
                    target = (target + 1) % targets.size();
                }
                targets.set(i, target);
            }
        }

        states = Collections.unmodifiableList(states);
        targets = Collections.unmodifiableList(targets);
        
        return new MultiplayerGameState(states, targets);
    }
    
    /**
     * Given a number of lines, determines how many garbage lines should be sent to opponents.
     * 
     * @param lines number of lines scored in a single game state update; must be at least 0 and less than 5
     * @return number of garbage lines to be sent
     */
    private int computeGarbageLines(int lines) {
        int result;
        if (lines == 0 || lines == 1) {
            result = 0;
        } else if (lines == 2) {
            result = 1;
        } else if (lines == 3) {
            result = 2;
        } else if (lines == 4) {
            // Tetris!
            result = 4;
        } else {
            throw new IllegalArgumentException("Unexpected number of lines: " + lines);
        }
        return result;
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
    private OnePlayerGameState computeNextOnePlayerState(int playerIndex, MultiplayerGameState previousState, List<InputState<Input>> inputStates) {
        OnePlayerGameState previousOnePlayerState = previousState.getStateForPlayer(playerIndex);
        InputState<Input> inputState = inputStates.get(playerIndex);
        return onePlayerEngine.computeNextState(previousOnePlayerState, Collections.singletonList(inputState));
    }
}
