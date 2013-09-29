package nl.mvdr.tinustris.engine;

import nl.mvdr.tinustris.input.InputState;
import nl.mvdr.tinustris.model.GameState;

/**
 * Game engine interface.
 * 
 * @author Martijn van de Rijdt
 */
public interface GameEngine {
    /**
     * Initialises the game state.
     * 
     * @return new game state
     */
    public GameState initGameState();
    
    /**
     * Computes the next game state based on the previous one and the state of the controls.
     * 
     * @param previousState
     *            previous game state
     * @param inputState
     *            input state
     * @return new game state
     */
    GameState computeNextState(GameState previousState, InputState inputState);
}
