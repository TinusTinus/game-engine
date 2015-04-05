package nl.mvdr.tinustris.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import nl.mvdr.game.state.GameState;

/**
 * Dummy implementation of {@link GameState}.
 * 
 * @author Martijn van de Rijdt
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum DummyGameState implements GameState {
    /** Dummy game state where the game is not yet over. */
    GAME_NOT_OVER(false),
    /** Dummy game state where the game is over. */
    GAME_OVER(true);
    
    /** Whether the game is over. */
    private final boolean gameOver;
}
