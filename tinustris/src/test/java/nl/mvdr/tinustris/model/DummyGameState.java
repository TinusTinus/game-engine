package nl.mvdr.tinustris.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * Dummy implementation of {@link GameState}.
 * 
 * @author Martijn van de Rijdt
 */
@RequiredArgsConstructor
@ToString
@Getter
public class DummyGameState implements GameState {
    /** Whether the game is over. */
    private final boolean gameOver;
    
    /** Constructor. */
    public DummyGameState() {
        this(false);
    }
}
