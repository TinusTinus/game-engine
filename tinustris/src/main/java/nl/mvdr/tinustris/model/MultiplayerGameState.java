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
    public MultiplayerGameState(@NonNull OnePlayerGameState... states) {
        super();

        this.states = Collections.unmodifiableList(Arrays.asList(states));

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
}
