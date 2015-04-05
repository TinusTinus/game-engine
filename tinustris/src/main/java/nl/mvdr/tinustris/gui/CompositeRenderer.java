package nl.mvdr.tinustris.gui;

import java.util.List;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import nl.mvdr.game.gui.GameRenderer;
import nl.mvdr.game.state.GameState;

/**
 * Game renderer which merely defers to a number of other renderers.
 * 
 * <S> game state type
 * 
 * @author Martijn van de Rijdt
 */
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class CompositeRenderer<S extends GameState> implements GameRenderer<S> {
    /** Renderers. */
    private final List<GameRenderer<S>> renderers;
    
    /** {@inheritDoc} */
    @Override
    public void render(@NonNull S gameState) {
        renderers.forEach(renderer -> renderer.render(gameState));
    }
}
