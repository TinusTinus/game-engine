package nl.mvdr.tinustris.engine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import nl.mvdr.tinustris.input.Input;
import nl.mvdr.tinustris.input.InputState;
import nl.mvdr.tinustris.input.InputStateHistory;
import nl.mvdr.tinustris.model.Action;
import nl.mvdr.tinustris.model.GameState;
import nl.mvdr.tinustris.model.Orientation;
import nl.mvdr.tinustris.model.Point;
import nl.mvdr.tinustris.model.Tetromino;

/**
 * Implementation of {@link GameEngine}.
 * 
 * @author Martijn van de Rijdt
 */
public class TinusTrisEngine implements GameEngine {
    /** Number of frames between drops. */
    // TODO have this be variable, depending on current level
    private static final int FRAMES_BETWEEN_DROPS = 60;
    /**
     * Number of frames the input is ignored while the user is holding down a button.
     * 
     * Say this value is 30 and the user is holding the left button. The active block will now move left once every
     * thirty frames.
     */
    // TODO come up with a better name for this constant?
    private static final int INPUT_FRAMES = 30;

    /** Tetromino generator. */
    private final TetrominoGenerator generator;
    
    /**  Constructor. */
    public TinusTrisEngine() {
        super();
        this.generator = new RandomTetrominoGenerator();
    }
    
    /** {@inheritDoc} */
    @Override
    public GameState initGameState() {
        List<Tetromino> grid = new ArrayList<>(GameState.DEFAULT_WIDTH * GameState.DEFAULT_HEIGHT);
        while (grid.size() != GameState.DEFAULT_WIDTH * GameState.DEFAULT_HEIGHT) {
            grid.add(null);
        }
        grid = Collections.unmodifiableList(grid);        
        GameState gameState = new GameState(grid, GameState.DEFAULT_WIDTH, generator.get(0), generator.get(1));
        return gameState;
    }

    
    /** {@inheritDoc} */
    @Override
    public GameState computeNextState(GameState previousState, InputState inputState) {
        // TODO implement for realsies
        determineActions(previousState, inputState);

        List<Tetromino> grid;
        int width = previousState.getWidth();
        Tetromino block;
        Point location;
        Orientation orientation = previousState.getCurrentBlockOrientation();
        Tetromino nextBlock;
        int numFramesSinceLastTick = previousState.getNumFramesSinceLastTick() + 1;
        InputStateHistory inputStateHistory = previousState.getInputStateHistory().next(inputState);
        int blockCounter;
        
        if (numFramesSinceLastTick == FRAMES_BETWEEN_DROPS) {
            if (previousState.canMoveDown()) {
                block = previousState.getCurrentBlock();
                grid = previousState.getGrid();
                location = previousState.getCurrentBlockLocation().translate(0, -1);
                nextBlock = previousState.getNextBlock();
                blockCounter = previousState.getBlockCounter();
            } else {
                block = previousState.getNextBlock();
                grid = new ArrayList<>(previousState.getGrid());
                for (Point point: previousState.getCurrentActiveBlockPoints()) {
                    int index = previousState.toGridIndex(point);
                    grid.set(index, previousState.getCurrentBlock());
                }
                grid = Collections.unmodifiableList(grid);
                location = previousState.getBlockSpawnLocation();
                nextBlock = generator.get(previousState.getBlockCounter() + 2);
                blockCounter = previousState.getBlockCounter() + 1;
            }
            numFramesSinceLastTick = 0;
        } else {
            block = previousState.getCurrentBlock();
            grid = previousState.getGrid();
            location = previousState.getCurrentBlockLocation();
            nextBlock = previousState.getNextBlock();
            blockCounter = previousState.getBlockCounter();
        }
        
        return new GameState(grid, width, block, location, orientation, nextBlock, numFramesSinceLastTick,
                inputStateHistory, blockCounter);
    }

    /**
     * Determines which actions should be performed.
     * 
     * @param previousState previous game state
     * @param inputState input state
     * @return actions
     */
    private List<Action> determineActions(GameState previousState, InputState inputState) {
        List<Action> actions = new ArrayList<>(Action.values().length + 1);
        if (previousState.getNumFramesSinceLastTick() + 1 == FRAMES_BETWEEN_DROPS) {
            actions.add(Action.MOVE_DOWN);
        }
        for (Input input: Input.values()) {
            if (inputState.isPressed(input)
                    && previousState.getInputStateHistory().getNumberOfFrames(input) % INPUT_FRAMES == 0) {
                actions.add(input.getAction());
            }
        }
        return actions;
    }
}
