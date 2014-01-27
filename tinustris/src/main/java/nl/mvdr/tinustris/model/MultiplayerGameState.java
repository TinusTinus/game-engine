package nl.mvdr.tinustris.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import lombok.NonNull;
import lombok.ToString;

/**
 * Contains the game state for a multiplayer game.
 * 
 * @author Martijn van de Rijdt
 */
@ToString
public class MultiplayerGameState implements GameState {
    /** Game states of the individualy players. Contains at least two elements and no null values. */
    @NonNull
    private final List<OnePlayerGameState> states;
    
    /**
     * Constructor.
     * 
     * @param states
     *            game states; one for each individual player; must contain at least two states and no null values
     */
    public MultiplayerGameState(@NonNull List<OnePlayerGameState> states) {
        super();

        this.states = states;

        // check the contents
        if (this.states.size() < 2) {
            throw new IllegalArgumentException("A multiplayer game must have at least two players; was: "
                    + this.states.size());
        }
        if (this.states.contains(null)) {
            throw new NullPointerException("No null values allowed for player states; found one at index: "
                    + this.states.indexOf(null));
        }
    }

    /**
     * Constructor.
     * 
     * @param states
     *            game states; one for each individual player; must contain at least two states and no null values
     */
    public MultiplayerGameState(@NonNull OnePlayerGameState... states) {
        this(Collections.unmodifiableList(Arrays.asList(states)));
    }

    /** {@inheritDoc} */
    @Override
    public boolean isGameOver() {
        // The game is not over as long as there are at least two players still playing.
        int numberOfActivePlayers = 0;
        Iterator<OnePlayerGameState> iterator = states.iterator();
        
        while (numberOfActivePlayers < 2 && iterator.hasNext()) {
            if (!iterator.next().isGameOver()) {
                numberOfActivePlayers++;
            }
        }
        return numberOfActivePlayers < 2;
    }
    
    /** @return the number of players in the game represented by this game state*/
    public int getNumberOfPlayers() {
        return this.states.size();
    }

    /**
     * Returns the state for a given player.
     * 
     * @param i player index, must be at least 0 and less than the number of players
     * @return state
     * @throws IndexOutOfBoundsException if i is out of bounds (less than 0 or at least the number of players)
     */
    public OnePlayerGameState getStateForPlayer(int i) {
        return this.states.get(i);
    }
}