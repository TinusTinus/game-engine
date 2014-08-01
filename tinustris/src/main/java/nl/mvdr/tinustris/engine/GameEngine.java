package nl.mvdr.tinustris.engine;

import java.util.List;

import nl.mvdr.tinustris.input.InputState;
import nl.mvdr.tinustris.model.GameState;

/**
 * Game engine interface.
 * 
 * @param <S> game state type
 * 
 * @author Martijn van de Rijdt
 */
public interface GameEngine<S extends GameState> {
    /**
     * Initialises the game state.
     * 
     * @return new game state
     */
    S initGameState();

    /**
     * Computes the next game state based on the previous one and the state of the controls.
     * 
     * @param previousState
     *            previous game state
     * @param inputStates
     *            input states for all players; the length of this list must match the number of players in the game
     * @return new game state
     */
    S computeNextState(S previousState, List<InputState> inputStates);
}
