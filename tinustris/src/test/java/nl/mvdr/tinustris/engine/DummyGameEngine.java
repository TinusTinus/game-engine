package nl.mvdr.tinustris.engine;

import java.util.List;

import nl.mvdr.game.input.InputState;
import nl.mvdr.tinustris.input.Input;
import nl.mvdr.tinustris.model.DummyGameState;

/**
 * Dummy implementation of {@link GameEngine}.
 * 
 * @author Martijn van de Rijdt
 */
public class DummyGameEngine implements GameEngine<DummyGameState, Input> {
    /** {@inheritDoc} */
    @Override
    public DummyGameState initGameState() {
        return DummyGameState.GAME_NOT_OVER;
    }

    /** {@inheritDoc} */
    @Override
    public DummyGameState computeNextState(DummyGameState previousState, List<InputState<Input>> inputStates) {
        return previousState;
    }
}
