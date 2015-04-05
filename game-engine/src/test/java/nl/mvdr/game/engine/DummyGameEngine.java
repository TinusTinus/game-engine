package nl.mvdr.game.engine;

import java.util.List;

import nl.mvdr.game.input.DummyInput;
import nl.mvdr.game.input.InputState;
import nl.mvdr.game.state.DummyGameState;

/**
 * Dummy implementation of {@link GameEngine}.
 * 
 * @author Martijn van de Rijdt
 */
public class DummyGameEngine implements GameEngine<DummyGameState, DummyInput> {
    /** {@inheritDoc} */
    @Override
    public DummyGameState initGameState() {
        return DummyGameState.GAME_NOT_OVER;
    }

    /** {@inheritDoc} */
    @Override
    public DummyGameState computeNextState(DummyGameState previousState, List<InputState<DummyInput>> inputStates) {
        return previousState;
    }
}
