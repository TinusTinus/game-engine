package nl.mvdr.tinustris.gui;

import java.util.List;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import nl.mvdr.tinustris.model.GameState;

/**
 * Game renderer which merely defers to a number of other renderers.
 * 
 * @author Martijn van de Rijdt
 */
@RequiredArgsConstructor
class CompositeRenderer implements GameRenderer {
    /** Renderers. */
    private final List<GameRenderer> renderers;
    
    /** {@inheritDoc} */
    @Override
    public void render(@NonNull GameState gameState) {
        for (GameRenderer renderer: renderers) {
            renderer.render(gameState);
        }
    }
}
