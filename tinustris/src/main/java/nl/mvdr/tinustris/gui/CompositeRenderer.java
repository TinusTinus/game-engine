package nl.mvdr.tinustris.gui;

import java.util.Arrays;
import java.util.List;

import lombok.NonNull;
import nl.mvdr.tinustris.model.GameState;

/**
 * Game renderer which merely defers to a number of other renderers.
 * 
 * @author Martijn van de Rijdt
 */
class CompositeRenderer implements GameRenderer {
    /** Renderers. */
    private final List<GameRenderer> renderers;
    
    /**
     * Constructor.
     * 
     * @param gameRenderers renderers
     */
    public CompositeRenderer(GameRenderer...gameRenderers) {
        super();
        this.renderers = Arrays.asList(gameRenderers);
    }
    
    /** {@inheritDoc} */
    @Override
    public void render(@NonNull GameState gameState) {
        for (GameRenderer renderer: renderers) {
            renderer.render(gameState);
        }
    }
}
