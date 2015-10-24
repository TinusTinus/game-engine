package nl.mvdr.game.gui;

import java.util.List;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import nl.mvdr.game.state.GameState;

/**
 * Game renderer which merely defers to a number of other renderers.
 * 
 * @param <S> game state type
 * 
 * @author Martijn van de Rijdt
 */
@RequiredArgsConstructor
public class CompositeRenderer<S extends GameState> implements GameRenderer<S> {
    /** Renderers. */
    private final List<GameRenderer<S>> renderers;
    
    /** {@inheritDoc} */
    @Override
    public void render(@NonNull S gameState) {
        renderers.forEach(renderer -> renderer.render(gameState));
    }
}
