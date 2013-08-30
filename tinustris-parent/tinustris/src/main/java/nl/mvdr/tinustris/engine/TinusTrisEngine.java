package nl.mvdr.tinustris.engine;

import nl.mvdr.tinustris.input.InputState;
import nl.mvdr.tinustris.model.GameState;
import nl.mvdr.tinustris.model.Point;

/**
 * Implementation of {@link GameEngine}.
 * 
 * @author Martijn van de Rijdt
 */
public class TinusTrisEngine implements GameEngine {
    /** {@inheritDoc} */
    @Override
    public GameState computeNextState(GameState previousState, InputState inputState) {
        // TODO implement for realsies

        int numFramesSinceLastTick = previousState.getNumFramesSinceLastTick() + 1;
        Point newLocation = previousState.getCurrentBlockLocation();
        if (numFramesSinceLastTick == 60 && previousState.canMoveDown()) {
            newLocation = previousState.getCurrentBlockLocation().translate(0, -1);
            numFramesSinceLastTick = 0;
        }

        return new GameState(previousState.getGrid(), previousState.getWidth(), previousState.getCurrentBlock(),
                newLocation, previousState.getCurrentBlockOrientation(), previousState.getNextBlock(),
                numFramesSinceLastTick);
    }
}
