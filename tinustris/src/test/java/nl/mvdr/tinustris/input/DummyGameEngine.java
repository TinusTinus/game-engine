package nl.mvdr.tinustris.input;

import nl.mvdr.tinustris.engine.GameEngine;
import nl.mvdr.tinustris.model.DummyGameState;

/**
 * Dummy implementation of {@link GameEngine}.
 * 
 * @author Martijn van de Rijdt
 */
public class DummyGameEngine implements GameEngine<DummyGameState> {
    /** {@inheritDoc} */
    @Override
    public DummyGameState initGameState() {
        return new DummyGameState();
    }

    /** {@inheritDoc} */
    @Override
    public DummyGameState computeNextState(DummyGameState previousState, InputState inputState) {
        return previousState;
    }
}
