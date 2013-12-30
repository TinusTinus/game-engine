package nl.mvdr.tinustris.engine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@RequiredArgsConstructor
public class TinustrisEngine implements GameEngine {
    /**
     * Number of frames the input is ignored while the user is holding down a button.
     * 
     * Say this value is 30 and the user is holding the left button. The active block will now move left once every
     * thirty frames.
     */
    private static final int INPUT_FRAMES = 10;
    
    /** Tetromino generator. */
    @NonNull
    private final TetrominoGenerator generator;
    /** Speed curve. */
    @NonNull
    private final SpeedCurve curve;
    
    /**  Constructor. */
    public TinustrisEngine() {
        this(new RandomTetrominoGenerator(), new GameBoySpeedCurve());
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
        GameState result = updateInputStateAndCounters(previousState, inputState);
        if (previousState.getNumFramesUntilLinesDisappear() == 1) {
            result = removeLines(result);
        }
        
        if (previousState.getCurrentBlock() == null && previousState.getNumFramesUntilLinesDisappear() <= 1
                && curve.computeARE(previousState) < previousState.getNumFramesSinceLastLock()) {
            result = spawnNextBlock(result);
        }
        
        if (result.getCurrentBlock() != null) {
            List<Action> actions = determineActions(previousState, inputState);
            for (Action action : actions) {
                result = executeAction(result, action);
            }
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
        List<Action> actions = new ArrayList<>();
        
        // process gravity
        int internalGravity = curve.computeInternalGravity(previousState);
        if (256 / internalGravity <= previousState.getNumFramesSinceLastDownMove()) {
            int cells = Math.round(internalGravity / 256);
            if (cells == 0) {
                cells = 1;
            }
            for (int i = 0; i != cells; i++) {
                actions.add(Action.GRAVITY_DROP);
            }
        }
        
        // process player input
        for (Input input: Input.values()) {
            if (inputState.isPressed(input)
                    && previousState.getInputStateHistory().getNumberOfFrames(input) % INPUT_FRAMES == 0) {
                actions.add(input.getAction());
            }
        }
        
        // process lock delay
        int lockDelay = curve.computeLockDelay(previousState);
        if (lockDelay <= previousState.getNumFramesSinceLastMove()) {
            actions.add(Action.LOCK);
        }
        
        return actions;
    }
    
    /**
     * Returns a new game state based on the given previous state.
     * 
     * The input history is updated with the given input state and the frame counters are updated. All other values
     * are the same as in the given state.
     * 
     * @param previousState previous game state
     * @param inputState input state for the current frame
     * @return game state with updated input history and frame counter, otherwise unchanged
     */
    private GameState updateInputStateAndCounters(GameState previousState, InputState inputState) {
        InputStateHistory inputStateHistory = previousState.getInputStateHistory().next(inputState);
        int numFramesSinceLastTick = previousState.getNumFramesSinceLastDownMove() + 1;
        int numFramesSinceLastLock = previousState.getNumFramesSinceLastLock() + 1;
        int numFramesSinceLastMove = previousState.getNumFramesSinceLastMove() + 1;
        int numFramesUntilLinesDisappear = Math.max(0, previousState.getNumFramesUntilLinesDisappear() - 1);
        return new GameState(previousState.getGrid(), previousState.getWidth(), previousState.getCurrentBlock(),
                previousState.getCurrentBlockLocation(), previousState.getCurrentBlockOrientation(),
                previousState.getNextBlock(), numFramesSinceLastTick, numFramesSinceLastLock, numFramesSinceLastMove, 
                inputStateHistory, previousState.getBlockCounter(), previousState.getLines(),
                numFramesUntilLinesDisappear);
    }
    
    /**
     * Executes the given action on the given game state.
     * 
     * @param state base game state
     * @param action action to be performed
     * @return updated game state
     */
    private GameState executeAction(GameState state, Action action) {
        if (log.isTraceEnabled()) {
            log.trace("State before executing action {}: {}", action, state);
        }
        
        GameState result;
        if (action == Action.MOVE_DOWN) {
            result = executeMoveDown(state);
        } else if (action == Action.GRAVITY_DROP) {
            result = executeGravityDrop(state);
        } else if (action == Action.LOCK) {
            result = executeLock(state);
        } else if (action == Action.MOVE_LEFT) {
            result = executeMoveLeft(state);
        } else if (action == Action.MOVE_RIGHT){
            result = executeMoveRight(state);
        } else if (action == Action.HARD_DROP) {
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
        
        if (log.isTraceEnabled()) {
            log.trace("State after executing action {}: {}", action, result);
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
     * Executes the gravity drop action.
     * 
     * @param state game state
     * @return updated game state
     */
    private GameState executeGravityDrop(GameState state) {
        GameState result;
        if (state.canMoveDown()) {
            result = moveDown(state);
        } else {
            // do nothing
            result = state;
        }
        return result;
    }
    
    /**
     * Executes the lock block action.
     * 
     * @param state game state
     * @return updated game state
     */
    private GameState executeLock(GameState state) {
        GameState result;
        if (!state.canMoveDown()) {
            result = lockBlock(state);
        } else {
            // do nothing
            result = state;
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
                state.getCurrentBlockOrientation(), state.getNextBlock(), 0, state.getNumFramesSinceLastLock(), 0,
                state.getInputStateHistory(), state.getBlockCounter(), state.getLines());
    }

    /**
     * Locks the current block in its current position.
     * 
     * @param state game state
     * @return updated game state
     */
    private GameState lockBlock(GameState state) {
        int width = state.getWidth();
        int height = state.getHeight();
        
        // Update the grid: old grid plus current location of the active block.
        List<Tetromino>grid = new ArrayList<>(state.getGrid());
        for (Point point : state.getCurrentActiveBlockPoints()) {
            int index = state.toGridIndex(point);
            grid.set(index, state.getCurrentBlock());
        }
        grid = Collections.unmodifiableList(grid);
        
        // Check for newly formed lines.
        int linesScored = countLines(width, height, grid);
        log.info("Lines scored: " + linesScored);
        
        // Create the new game state.
        Tetromino block;
        Tetromino nextBlock;
        Point location;
        Orientation orientation;
        int blockCounter;
        int numFramesUntilLinesDisappear;
        if (0 < linesScored || 0 < curve.computeARE(state)) {
            block = null;
            nextBlock = state.getNextBlock();
            location = null;
            orientation = null;
            blockCounter = state.getBlockCounter();
            if (0 < linesScored) {
                numFramesUntilLinesDisappear = curve.computeLineClearDelay(state);
            } else {
                numFramesUntilLinesDisappear = 0;
            }
        } else {
            block = state.getNextBlock();
            nextBlock = generator.get(state.getBlockCounter() + 2);
            location = state.getBlockSpawnLocation();
            orientation = Orientation.getDefault();
            blockCounter = state.getBlockCounter() + 1;
            numFramesUntilLinesDisappear = 0;
        }
        int lines = state.getLines() + linesScored;

        GameState result = new GameState(grid, width, block, location, orientation, nextBlock, 0, 0, 0,
                state.getInputStateHistory(), blockCounter, lines, numFramesUntilLinesDisappear);
        
        if (linesScored != 0 && log.isDebugEnabled()) {
            log.debug(result.toString());
        }
        
        return result;
    }

    /**
     * Counts the number of full lines in the grid.
     * 
     * @param width
     *            width of the grid
     * @param height
     *            height of the grid
     * @param grid
     *            list containing the grid
     * @return number of lines; between 0 and 4
     */
    private int countLines(int width, int height, List<Tetromino> grid) {
        int linesScored = 0;
        for (int line = height - 1; 0 <= line; line--) {
            boolean filled = true;
            int x = 0;
            while (filled && x != width) {
                filled = grid.get(x + line * width) != null;
                x++;
            }
            
            if (filled) {
                linesScored++;
            }
        }
        return linesScored;
    }

    /**
     * Removes any full lines from the grid and drops down the lines above them.
     * 
     * @param width
     *            width of the grid
     * @param height
     *            height of the grid
     * @param grid
     *            list containing the grid
     * @return updated copy of the game state
     */
    private GameState removeLines(GameState state) {
        int width = state.getWidth();
        int height = state.getHeight();
        List<Tetromino> grid = new ArrayList<>(state.getGrid());

        // Update the grid by removing all full lines.
        for (int line = height - 1; 0 <= line; line--) {
            if (state.isFullLine(line)) {
                // Line found.
                // Drop all the lines above it down.
                for (int y = line; y != height - 1; y++) {
                    for (int x = 0; x != width; x++) {
                        Tetromino tetromino = grid.get(x + (y + 1) * width);
                        grid.set(x + y * width, tetromino);
                    }
                }
                
                // Make the top line empty.
                for (int x = 0; x != width; x++) {
                    grid.set(x + (height - 1) * width, null);
                }
            }
        }
        
        return new GameState(grid, width, state.getCurrentBlock(), state.getCurrentBlockLocation(),
                state.getCurrentBlockOrientation(), state.getNextBlock(), state.getNumFramesSinceLastDownMove(), state.getNumFramesSinceLastLock(), state.getNumFramesSinceLastMove(),
                state.getInputStateHistory(), state.getBlockCounter(), state.getLines());
    }
    
    /**
     * Spawns a new block.
     * 
     * @param state state
     * @return new state
     */
    private GameState spawnNextBlock(GameState state) {
      Tetromino block = state.getNextBlock();
      Tetromino nextBlock = generator.get(state.getBlockCounter() + 2);
      Point location = state.getBlockSpawnLocation();
      Orientation orientation = Orientation.getDefault();
      int blockCounter = state.getBlockCounter() + 1;
      
        return new GameState(state.getGrid(), state.getWidth(), block, location, orientation, nextBlock, 0,
                state.getNumFramesSinceLastLock(), 0, state.getInputStateHistory(), blockCounter, state.getLines());
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
            result = moveLeft(state);
        } else {
            // do nothing
            result = state;
        }
        return result;
    }

    /**
     * Moves the current block left one position on the given state.
     * 
     * This method does not check that state.canMoveLeft() is true.
     * 
     * @param state game state
     * @return updated state
     */
    private GameState moveLeft(GameState state) {
        GameState result;
        Point location = state.getCurrentBlockLocation().translate(-1, 0);
        result = new GameState(state.getGrid(), state.getWidth(), state.getCurrentBlock(), location,
                state.getCurrentBlockOrientation(), state.getNextBlock(), state.getNumFramesSinceLastDownMove(),
                state.getNumFramesSinceLastLock(), 0, state.getInputStateHistory(), state.getBlockCounter(),
                state.getLines());
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
            result = moveRight(state);
        } else {
            // do nothing
            result = state;
        }
        return result;
    }

    /**
     * Moves the current block right one position on the given state.
     * 
     * This method does not check that state.canMoveRight() is true.
     * 
     * @param state game state
     * @return updated state
     */
    private GameState moveRight(GameState state) {
        GameState result;
        Point location = state.getCurrentBlockLocation().translate(1, 0);
        result = new GameState(state.getGrid(), state.getWidth(), state.getCurrentBlock(), location,
                state.getCurrentBlockOrientation(), state.getNextBlock(), state.getNumFramesSinceLastDownMove(),
                state.getNumFramesSinceLastLock(), 0, state.getInputStateHistory(), state.getBlockCounter(),
                state.getLines());
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
        GameState stateAfterTurn = turnLeft(state);
        return fixStateAfterAction(state, stateAfterTurn);
    }

    /**
     * Turns the current active block counter-clockwise.
     * 
     * This method does not check whether the resulting game state is valid!
     * 
     * @param state state
     * @return updated game state (may be invalid)
     */
    private GameState turnLeft(GameState state) {
        Orientation orientation = state.getCurrentBlockOrientation().getNextCounterClockwise();
        return new GameState(state.getGrid(), state.getWidth(), state.getCurrentBlock(),
                state.getCurrentBlockLocation(), orientation, state.getNextBlock(),
                state.getNumFramesSinceLastDownMove(), state.getNumFramesSinceLastLock(), 0,
                state.getInputStateHistory(), state.getBlockCounter(), state.getLines());
    }
    
    /**
     * Executes the turn right action.
     * 
     * @param state game state
     * @return updated game state
     */
    private GameState executeTurnRight(GameState state) {
        GameState stateAfterTurn = turnRight(state);
        return fixStateAfterAction(state, stateAfterTurn);
    }
    
    /**
     * Turns the current active block clockwise.
     * 
     * This method does not check whether the resulting game state is valid!
     * 
     * @param state state
     * @return updated game state (may be invalid)
     */
    private GameState turnRight(GameState state) {
        Orientation orientation = state.getCurrentBlockOrientation().getNextClockwise();
        return new GameState(state.getGrid(), state.getWidth(), state.getCurrentBlock(),
                state.getCurrentBlockLocation(), orientation, state.getNextBlock(),
                state.getNumFramesSinceLastDownMove(), state.getNumFramesSinceLastLock(), 0,
                state.getInputStateHistory(), state.getBlockCounter(), state.getLines());
    }

    /**
     * Executes the hold action.
     * 
     * @param state game state
     * @return updated game state
     */
    private GameState executeHold(GameState state) {
        Tetromino block = state.getNextBlock();
        Tetromino nextBlock = state.getCurrentBlock();
        GameState stateAfterHold = new GameState(state.getGrid(), state.getWidth(), block,
                state.getCurrentBlockLocation(), state.getCurrentBlockOrientation(), nextBlock,
                state.getNumFramesSinceLastDownMove(), state.getNumFramesSinceLastLock(), 0,
                state.getInputStateHistory(), state.getBlockCounter(), state.getLines());
        return fixStateAfterAction(state, stateAfterHold);
    }
    
    /**
     * Fixes the state after an action that may have left the game in an invalid state.
     * 
     * @param originalState
     *            original game state
     * @param stateAfterAction
     *            game state after execution of the action; this game state is allowed to be invalid (that is, a game 
     *            over state or a state where the active block is partially or completely out of bounds)
     * @return new game state
     */
    private GameState fixStateAfterAction(GameState originalState, GameState stateAfterAction) {
        GameState result;
        if (stateAfterAction.isCurrentBlockWithinBounds() && !stateAfterAction.isTopped()) {
            // no problemo!
            result = stateAfterAction;
        } else {
            // state is not valid
            if (stateAfterAction.canMoveRight()) {
                result = moveRight(stateAfterAction);
            } else if (stateAfterAction.canMoveLeft()) {
                result = moveLeft(stateAfterAction);
            } else {
                // impossible to fix; cancel the action
                result = originalState;
            }
        }
        return result;
    }
}
