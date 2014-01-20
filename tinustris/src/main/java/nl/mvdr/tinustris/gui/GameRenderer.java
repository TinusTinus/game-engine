package nl.mvdr.tinustris.gui;

import nl.mvdr.tinustris.model.GameState;

/**
 * User interface component displaying (an aspect of) the game state.
 * 
 * @param <S> game state type
 * 
 * @author Martijn van de Rijdt
 */
public interface GameRenderer<S extends GameState> {
    /**
     * Renders the given game state. Does not need to be called from the JavaFX thread.
     * 
     * @param gameState
     *            game state to be displayed
     */
    void render(S gameState);
}
