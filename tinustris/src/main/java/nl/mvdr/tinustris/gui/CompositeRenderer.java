package nl.mvdr.tinustris.gui;

import java.util.Collections;
import java.util.List;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import nl.mvdr.tinustris.model.GameState;

/**
 * Game renderer which merely defers to a number of other renderers.
 * 
 * <S> game state type
 * 
 * @author Martijn van de Rijdt
 */
@RequiredArgsConstructor
class CompositeRenderer<S extends GameState> implements GameRenderer<S> {
    /** Renderers. */
    private final List<GameRenderer<S>> renderers;
    
    /** Convenience constructor. */
    CompositeRenderer() {
        this(Collections.<GameRenderer<S>>emptyList());
    }
    
    /** {@inheritDoc} */
    @Override
    public void render(@NonNull S gameState) {
        for (GameRenderer<S> renderer: renderers) {
            renderer.render(gameState);
        }
    }
}
