package nl.mvdr.tinustris.gui;

import lombok.Getter;
import nl.mvdr.tinustris.model.GameState;

/**
 * Dummy renderer, which simply stores the last game state it has received.
 * 
 * @author Martijn van de Rijdt
 */
@Getter
public class DummyRenderer implements GameRenderer {
    /** Game state from the last time render was called. Initially null. */
    private GameState lastRenderedState;
    
    /** {@inheritDoc} */
    @Override
    public void render(GameState gameState) {
        this.lastRenderedState = gameState;
    }
}
