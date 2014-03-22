package nl.mvdr.tinustris.gui;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import nl.mvdr.tinustris.model.MultiplayerGameState;
import nl.mvdr.tinustris.model.OnePlayerGameState;

/**
 * Renderer for a game state belonging to a specific player in a multiplayer game.
 * 
 * @author Martijn van de Rijdt
 */
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class MultiplayerGameRenderer implements GameRenderer<MultiplayerGameState> {
    /** Renderer for the one player game. All {@link #render(MultiplayerGameState)} calls are deferred to this instance. */
    private final @NonNull GameRenderer<OnePlayerGameState> renderer;
    /**
     * Index of the player whose state is to be rendered by this renderer. Must be a valid index in all multiplayer game
     * states passed into {@link #render(MultiplayerGameState)}.
     */
    private final int playerIndex;

    /** 
     * {@inheritDoc} 
     * 
     * The value of {@link playerIndex} must be a valid index in the value of gameState.
     */
    @Override
    public void render(@NonNull MultiplayerGameState gameState) {
        OnePlayerGameState state = gameState.getStateForPlayer(playerIndex);
        this.renderer.render(state);
    }
}
