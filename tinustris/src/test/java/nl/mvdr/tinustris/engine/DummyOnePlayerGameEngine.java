package nl.mvdr.tinustris.engine;

import java.util.List;

import lombok.extern.slf4j.Slf4j;
import nl.mvdr.tinustris.input.Input;
import nl.mvdr.tinustris.input.InputState;
import nl.mvdr.tinustris.model.OnePlayerGameState;

/**
 * Dummy implementation of {@link GameEngine} for {@link OnePlayerGameState}.
 * 
 * @author Martijn van de Rijdt
 */
@Slf4j
public class DummyOnePlayerGameEngine implements GameEngine<OnePlayerGameState> {
    /** {@inheritDoc} */
    @Override
    public OnePlayerGameState initGameState() {
        log.info("Initialising new game state.");
        return new OnePlayerGameState();
    }

    /** {@inheritDoc} */
    @Override
    public OnePlayerGameState computeNextState(OnePlayerGameState previousState, List<InputState<Input>> inputStates) {
        log.info("Computing next state.");
        return previousState;
    }

}
