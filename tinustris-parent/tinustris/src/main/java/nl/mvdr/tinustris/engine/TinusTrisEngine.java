package nl.mvdr.tinustris.engine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import nl.mvdr.tinustris.input.Input;
import nl.mvdr.tinustris.input.InputState;
import nl.mvdr.tinustris.input.InputStateHistory;
import nl.mvdr.tinustris.model.Action;
import nl.mvdr.tinustris.model.GameState;
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
    private static final int INPUT_FRAMES = 10;

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
        List<Action> actions = determineActions(previousState, inputState);
        GameState result = updateInputStateAndFrameCounter(previousState, inputState);
        for (Action action: actions) {
            result = executeAction(result, action);
        }
        return result;
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
        if (previousState.getNumFramesSinceLastDownMove() + 1 == FRAMES_BETWEEN_DROPS) {
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
    
    /**
     * Returns a new game state based on the given previous state.
     * 
     * The input history is updated with the given input state and the number of frames is increased. All other values
     * are the same as in the given state.
     * 
     * @param previousState previous game state
     * @param inputState input state for the current frame
     * @return game state with updated input history and frame counter, otherwise unchanged
     */
    private GameState updateInputStateAndFrameCounter(GameState previousState, InputState inputState) {
        InputStateHistory inputStateHistory = previousState.getInputStateHistory().next(inputState);
        int numFramesSinceLastTick = previousState.getNumFramesSinceLastDownMove() + 1;
        return new GameState(previousState.getGrid(), previousState.getWidth(),
                previousState.getCurrentBlock(), previousState.getCurrentBlockLocation(),
                previousState.getCurrentBlockOrientation(), previousState.getNextBlock(),
                numFramesSinceLastTick, inputStateHistory, previousState.getBlockCounter());
    }
    
    /**
     * Executes the given action on the given game state.
     * 
     * @param state base game state
     * @param action action to be performed
     * @return updated game state
     */
    private GameState executeAction(GameState state, Action action) {
        GameState result;
        if (action == Action.MOVE_DOWN) {
            result = executeMoveDown(state);
        } else if (action == Action.MOVE_LEFT) {
            result = executeMoveLeft(state);
        } else if (action == Action.MOVE_RIGHT){
            result = executeMoveRight(state);
        } else if (action == Action.INSTANT_DROP) {
            result = executeInstantDrop(state);
        } else if (action == Action.TURN_LEFT) {
            result = executeTurnLeft(state);
        } else if (action == Action.TURN_RIGHT) {
            result = executeTurnRight(state);
        } else if (action == Action.HOLD) {
            result = executeHold(state);
        } else {
            throw new IllegalArgumentException("Unexpected action: " + action);
        }
        return result;
    }

    /**
     * Executes the down action.
     * 
     * @param state game state
     * @return updated game state
     */
    private GameState executeMoveDown(GameState state) {
        GameState result;
        
        if (state.canMoveDown()) {
            result = moveDown(state);
        } else {
            result = lockBlock(state);
        }
        
        return result;
    }

    /**
     * Moves the current block down one position on the given state.
     * 
     * This method does not check that state.canMoveDown() is true.
     * 
     * @param state game state
     * @return updated state
     */
    private GameState moveDown(GameState state) {
        Point location = state.getCurrentBlockLocation().translate(0, -1);
        return new GameState(state.getGrid(), state.getWidth(), state.getCurrentBlock(), location,
                state.getCurrentBlockOrientation(), state.getNextBlock(), 0, state.getInputStateHistory(),
                state.getBlockCounter());
    }

    /**
     * Locks the current block in its current position.
     * 
     * @param state game state
     * @return updated game state
     */
    private GameState lockBlock(GameState state) {
        List<Tetromino>grid = new ArrayList<>(state.getGrid());
        for (Point point : state.getCurrentActiveBlockPoints()) {
            int index = state.toGridIndex(point);
            grid.set(index, state.getCurrentBlock());
        }
        grid = Collections.unmodifiableList(grid);

        Tetromino block = state.getNextBlock();
        Point location = state.getBlockSpawnLocation();
        Tetromino nextBlock = generator.get(state.getBlockCounter() + 2);
        int blockCounter = state.getBlockCounter() + 1;

        return new GameState(grid, state.getWidth(), block, location, state.getCurrentBlockOrientation(),
                nextBlock, 0, state.getInputStateHistory(), blockCounter);
    }
    
    /**
     * Executes the left action.
     * 
     * @param state game state
     * @return updated game state
     */
    private GameState executeMoveLeft(GameState state) {
        GameState result;
        if (state.canMoveLeft()) {
            Point location = state.getCurrentBlockLocation().translate(-1, 0);
            result = new GameState(state.getGrid(), state.getWidth(), state.getCurrentBlock(), location,
                    state.getCurrentBlockOrientation(), state.getNextBlock(), state.getNumFramesSinceLastDownMove(),
                    state.getInputStateHistory(), state.getBlockCounter());
        } else {
            result = state;
        }
        return result;
    }

    /**
     * Executes the right action.
     * 
     * @param state game state
     * @return updated game state
     */
    private GameState executeMoveRight(GameState state) {
        GameState result;
        if (state.canMoveRight()) {
            Point location = state.getCurrentBlockLocation().translate(1, 0);
            result = new GameState(state.getGrid(), state.getWidth(), state.getCurrentBlock(), location,
                    state.getCurrentBlockOrientation(), state.getNextBlock(), state.getNumFramesSinceLastDownMove(),
                    state.getInputStateHistory(), state.getBlockCounter());
        } else {
            result = state;
        }
        return result;
    }
    
    /**
     * Executes the instant drop action.
     * 
     * @param state game state
     * @return updated game state
     */
    private GameState executeInstantDrop(GameState state) {
        GameState result = state;
        while (result.canMoveDown()) {
            result = moveDown(result);
        }
        result = lockBlock(result);
        return result;
    }
    
    /**
     * Executes the turn left action.
     * 
     * @param state game state
     * @return updated game state
     */
    private GameState executeTurnLeft(GameState state) {
        GameState result;
        // TODO
        result = state;
        return result;
    }
    
    /**
     * Executes the turn right action.
     * 
     * @param state game state
     * @return updated game state
     */
    private GameState executeTurnRight(GameState state) {
        GameState result;
        // TODO
        result = state;
        return result;
    }
    
    /**
     * Executes the hold action.
     * 
     * @param state game state
     * @return updated game state
     */
    private GameState executeHold(GameState state) {
        GameState result;
        // TODO
        result = state;
        return result;
    }
}
