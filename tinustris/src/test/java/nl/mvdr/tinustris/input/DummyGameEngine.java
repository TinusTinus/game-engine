package nl.mvdr.tinustris.input;

import nl.mvdr.tinustris.engine.GameEngine;
import nl.mvdr.tinustris.model.GameState;

/**
 * Dummy implementation of {@link GameEngine}.
 * 
 * @author Martijn van de Rijdt
 */
public class DummyGameEngine implements GameEngine {
    /** {@inheritDoc} */
    @Override
    public GameState initGameState() {
        return new GameState();
    }

    /** {@inheritDoc} */
    @Override
    public GameState computeNextState(GameState previousState, InputState inputState) {
        return previousState;
    }
}
