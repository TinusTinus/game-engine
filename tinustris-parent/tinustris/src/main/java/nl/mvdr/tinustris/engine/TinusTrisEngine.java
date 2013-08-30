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
    /** Number of frames between drops. */
    // TODO have this be variable, depending on current level
    private static final int FRAMES_BETWEEN_DROPS = 60;

    /** {@inheritDoc} */
    @Override
    public GameState computeNextState(GameState previousState, InputState inputState) {
        // TODO implement for realsies

        int numFramesSinceLastTick = previousState.getNumFramesSinceLastTick() + 1;
        Point newLocation = previousState.getCurrentBlockLocation();
        if (numFramesSinceLastTick == FRAMES_BETWEEN_DROPS && previousState.canMoveDown()) {
            newLocation = previousState.getCurrentBlockLocation().translate(0, -1);
            numFramesSinceLastTick = 0;
        }

        return new GameState(previousState.getGrid(), previousState.getWidth(), previousState.getCurrentBlock(),
                newLocation, previousState.getCurrentBlockOrientation(), previousState.getNextBlock(),
                numFramesSinceLastTick);
    }
}
