package nl.mvdr.tinustris.engine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
    private final OnePlayerEngine onePlayerEngine;
    
    /**
     * Constructor.
     * 
     * @param numberOfPlayers
     *            number of players; determines how many players will be in states created by the
     *            {@link #initGameState()} method
     */
    public MultiplayerEngine(int numberOfPlayers) {
        super();
        
        if (numberOfPlayers < 2) {
            throw new IllegalArgumentException("There need to be at least 2 players, was: " + numberOfPlayers);
        }
        
        this.numberOfPlayers = numberOfPlayers;
        this.onePlayerEngine = new OnePlayerEngine();
    }
    
    /** {@inheritDoc} */
    @Override
    public MultiplayerGameState initGameState() {
        OnePlayerGameState state = onePlayerEngine.initGameState();
        
        List<OnePlayerGameState> states = new ArrayList<>();
        while (states.size() != numberOfPlayers) {
            states.add(state);
        }
        states = Collections.unmodifiableList(states);
        
        return new MultiplayerGameState(states);
    }

    /** {@inheritDoc} */
    @Override
    public MultiplayerGameState computeNextState(MultiplayerGameState previousState, List<InputState> inputStates) {
        // TODO Auto-generated method stub
        return null;
    }
}
