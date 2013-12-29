package nl.mvdr.tinustris.gui;

import nl.mvdr.tinustris.model.GameState;

/**
 * User interface component displaying (an aspect of) the game state.
 * 
 * @author Martijn van de Rijdt
 */
public interface GameRenderer {
    /**
     * Renders the game state.
     * 
     * @param gameState
     *            game state to be displayed
     */
    void render(GameState gameState);
}
