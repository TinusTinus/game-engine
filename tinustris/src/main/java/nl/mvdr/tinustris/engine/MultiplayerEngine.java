package nl.mvdr.tinustris.engine;

import java.util.List;

import lombok.ToString;
import nl.mvdr.tinustris.input.InputState;
import nl.mvdr.tinustris.model.MultiplayerGameState;

/**
 * Implementation of {@link GameEngine} for a multiplayer game.
 * 
 * @author Martijn van de Rijdt
 */
@ToString
public class MultiplayerEngine implements GameEngine<MultiplayerGameState> {
    /** Number of players. Determines how many players will be in states created by the {@link #initGameState()} method. */
    private final int numberOfPlayers;
    
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
    }
    
    /** {@inheritDoc} */
    @Override
    public MultiplayerGameState initGameState() {
        // TODO Auto-generated method stub
        return null;
    }

    /** {@inheritDoc} */
    @Override
    public MultiplayerGameState computeNextState(MultiplayerGameState previousState, List<InputState> inputStates) {
        // TODO Auto-generated method stub
        return null;
    }
}
