package nl.mvdr.tinustris.gui;

import lombok.Getter;
import nl.mvdr.tinustris.model.GameState;

/**
 * Dummy renderer, which simply stores the last game state it has received.
 * 
 * @param <S> game state type
 * 
 * @author Martijn van de Rijdt
 */
@Getter
public class DummyRenderer<S extends GameState> implements GameRenderer<S> {
    /** Game state from the last time render was called. Initially null. */
    private S lastRenderedState;
    
    /** {@inheritDoc} */
    @Override
    public void render(S gameState) {
        this.lastRenderedState = gameState;
    }
}
