package nl.mvdr.tinustris.engine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.mvdr.tinustris.engine.level.ClassicLevelSystem;
import nl.mvdr.tinustris.engine.level.LevelSystem;
import nl.mvdr.tinustris.engine.speedcurve.SpeedCurve;
import nl.mvdr.tinustris.engine.speedcurve.TinustrisSpeedCurve;
import nl.mvdr.tinustris.input.Input;
import nl.mvdr.tinustris.input.InputState;
import nl.mvdr.tinustris.model.Action;
import nl.mvdr.tinustris.model.Block;
import nl.mvdr.tinustris.model.OnePlayerGameState;
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
public class OnePlayerEngine implements GameEngine<OnePlayerGameState> {
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
    /** Leveling system. */
    @NonNull
    private final LevelSystem levelSystem;
    
    /**  Constructor. */
    public OnePlayerEngine() {
        this(new RandomTetrominoGenerator(), new TinustrisSpeedCurve(), new ClassicLevelSystem());
    }
    
    /** {@inheritDoc} */
    @Override
    public OnePlayerGameState initGameState() {
        List<Block> grid = Collections.nCopies(OnePlayerGameState.DEFAULT_WIDTH * OnePlayerGameState.DEFAULT_HEIGHT, null);
        OnePlayerGameState gameState = new OnePlayerGameState(grid, OnePlayerGameState.DEFAULT_WIDTH, generator.get(0),
                generator.get(1));
        gameState = gameState.withLevel(this.levelSystem.computeLevel(gameState, gameState));
        return gameState;
    }
    
    /** {@inheritDoc} */
    @Override
    public OnePlayerGameState computeNextState(OnePlayerGameState previousState, List<InputState> inputStates) {
        if (inputStates.size() != 1) {
            throw new IllegalArgumentException("Expected 1 input state, got " + inputStates.size());
        }
        InputState inputState = inputStates.get(0);
        
        OnePlayerGameState result;
        
        if (!previousState.isGameOver()) {
            
            result = updateInputStateAndCounters(previousState, inputState);

            if (previousState.getNumFramesUntilLinesDisappear() == 1) {
                result = removeLines(result);
            }

            if (previousState.getActiveTetromino() == null && previousState.getNumFramesUntilLinesDisappear() <= 1
                    && curve.computeARE(previousState) < previousState.getNumFramesSinceLastLock()) {
                result = spawnNextBlock(result);
            }

            if (result.getActiveTetromino() != null) {
                List<Action> actions = determineActions(previousState, result, inputState);
                for (Action action : actions) {
                    result = executeAction(result, action);
                }
            }

            result = result.withLevel(this.levelSystem.computeLevel(previousState, result));
            
        } else {
            // game over, no need to update the game state anymore
            result = previousState;
        }
        
        return result;
    }

    /**
     * Determines which actions should be performed.
     * 
     * @param previousState previous game state
     * @param resultState result state
     * @param inputState input state
     * @return actions
     */
    private List<Action> determineActions(OnePlayerGameState previousState, OnePlayerGameState resultState,
            InputState inputState) {
        List<Action> actions = new ArrayList<>();
        
        // process gravity
        int internalGravity = curve.computeInternalGravity(resultState);
        if (256 / internalGravity <= resultState.getNumFramesSinceLastDownMove()) {
            int cells = Math.round(internalGravity / 256);
            cells = Math.max(cells, 1);
            
            actions.addAll(Collections.nCopies(cells, Action.GRAVITY_DROP));
        }
        
        // process player input
        actions.addAll(
                Arrays.asList(Input.values())
                    .stream()
                    .filter(inputState::isPressed)
                    .filter(input -> previousState.getInputStateHistory().getNumberOfFrames(input) % INPUT_FRAMES == 0)
                    .map(Input::getAction)
                    .collect(Collectors.toList()));
        
        // process lock delay
        int lockDelay = curve.computeLockDelay(resultState);
        if (lockDelay < resultState.getNumFramesSinceLastMove()) {
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
    private OnePlayerGameState updateInputStateAndCounters(OnePlayerGameState previousState, InputState inputState) {
        return previousState.withInputStateHistory(previousState.getInputStateHistory().next(inputState))
                .withNumFramesSinceLastDownMove(previousState.getNumFramesSinceLastDownMove() + 1)
                .withNumFramesSinceLastLock(previousState.getNumFramesSinceLastLock() + 1)
                .withNumFramesSinceLastMove(previousState.getNumFramesSinceLastMove() + 1)
                .withNumFramesUntilLinesDisappear(Math.max(0, previousState.getNumFramesUntilLinesDisappear() - 1));
    }
    
    /**
     * Executes the given action on the given game state.
     * 
     * @param state base game state
     * @param action action to be performed
     * @return updated game state
     */
    private OnePlayerGameState executeAction(OnePlayerGameState state, Action action) {
        if (log.isTraceEnabled()) {
            log.trace("State before executing action {}: {}", action, state);
        }
        
        OnePlayerGameState result;
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
    private OnePlayerGameState executeMoveDown(OnePlayerGameState state) {
        OnePlayerGameState result;
        
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
    private OnePlayerGameState executeGravityDrop(OnePlayerGameState state) {
        OnePlayerGameState result;
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
    private OnePlayerGameState executeLock(OnePlayerGameState state) {
        OnePlayerGameState result;
        if (state.getActiveTetromino() != null && !state.canMoveDown()) {
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
    private OnePlayerGameState moveDown(OnePlayerGameState state) {
        return state.withCurrentBlockLocation(state.getCurrentBlockLocation().translate(0, -1))
                .withNumFramesSinceLastDownMove(0)
                .withNumFramesSinceLastMove(0);
    }

    /**
     * Locks the current block in its current position.
     * 
     * @param state game state; must have an active block
     * @return updated game state
     */
    private OnePlayerGameState lockBlock(OnePlayerGameState state) {
        int width = state.getWidth();
        int height = state.getHeight();
        
        // Update the grid: old grid plus current location of the active block.
        List<Block> grid = new ArrayList<>(state.getGrid());
        for (Point point : state.getCurrentActiveBlockPoints()) {
            int index = state.toGridIndex(point);
            grid.set(index, state.getActiveTetromino().getBlock());
        }
        grid = Collections.unmodifiableList(grid);
        
        // Check for newly formed lines.
        int linesScored = countLines(width, height, grid);
        log.info("Lines scored: " + linesScored);
        
        // Create the new game state.
        Tetromino activeTetromino;
        Tetromino next;
        Point location;
        Orientation orientation;
        int blockCounter;
        int numFramesUntilLinesDisappear;
        if (0 < linesScored || 0 < curve.computeARE(state)) {
            activeTetromino = null;
            next = state.getNext();
            location = null;
            orientation = null;
            blockCounter = state.getBlockCounter();
            if (0 < linesScored) {
                numFramesUntilLinesDisappear = curve.computeLineClearDelay(state);
            } else {
                numFramesUntilLinesDisappear = 0;
            }
        } else {
            activeTetromino = state.getNext();
            next = generator.get(state.getBlockCounter() + 2);
            location = state.getBlockSpawnLocation();
            orientation = Orientation.getDefault();
            blockCounter = state.getBlockCounter() + 1;
            numFramesUntilLinesDisappear = 0;
        }
        int lines = state.getLines() + linesScored;

        OnePlayerGameState result = new OnePlayerGameState(grid, width, activeTetromino, location, orientation, next,
                0, 0, 0, state.getInputStateHistory(), blockCounter, lines, numFramesUntilLinesDisappear,
                state.getLevel());
        
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
    private int countLines(int width, int height, List<Block> grid) {
        long count = IntStream.range(0, height - 1)
            .filter(line -> isFullLine(line, width, grid))
            .count();
        // count should be at least 0 and less than 5, so we can safely cast to int
        return (int) count;
    }
    
    /**
     * Checks whether line y is a full line in the grid.
     *
     * @param y
     *            line index
     * @param width
     *            width of the grid
     * @param grid
     *            list containing the grid
     * @return whether this is a full line
     */
    private boolean isFullLine(int y, int width, List<Block> grid) {
        return IntStream.range(0, width)
            .allMatch(x -> grid.get(x + y * width) != null);
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
    private OnePlayerGameState removeLines(OnePlayerGameState state) {
        int width = state.getWidth();
        int height = state.getHeight();
        List<Block> grid = new ArrayList<>(state.getGrid());

        // Update the grid by removing all full lines.
        for (int line = height - 1; 0 <= line; line--) {
            if (state.isFullLine(line)) {
                // Line found.
                // Drop all the lines above it down.
                for (int y = line; y != height - 1; y++) {
                    for (int x = 0; x != width; x++) {
                        Block block = grid.get(x + (y + 1) * width);
                        grid.set(x + y * width, block);
                    }
                }
                
                // Make the top line empty.
                for (int x = 0; x != width; x++) {
                    grid.set(x + (height - 1) * width, null);
                }
            }
        }
        grid = Collections.unmodifiableList(grid);
        
        return state.withGrid(grid);
    }
    
    /**
     * Spawns a new block.
     * 
     * @param state state
     * @return new state
     */
    private OnePlayerGameState spawnNextBlock(OnePlayerGameState state) {
        Tetromino activeBlock = state.getNext();
        Tetromino next = generator.get(state.getBlockCounter() + 2);
        Point location = state.getBlockSpawnLocation();
        Orientation orientation = Orientation.getDefault();
        int blockCounter = state.getBlockCounter() + 1;

        return new OnePlayerGameState(state.getGrid(), state.getWidth(), activeBlock, location, orientation, next, 0,
                state.getNumFramesSinceLastLock(), 0, state.getInputStateHistory(), blockCounter, state.getLines(),
                state.getLevel());
    }

    /**
     * Executes the left action.
     * 
     * @param state game state
     * @return updated game state
     */
    private OnePlayerGameState executeMoveLeft(OnePlayerGameState state) {
        OnePlayerGameState result;
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
    private OnePlayerGameState moveLeft(OnePlayerGameState state) {
        return state.withCurrentBlockLocation(state.getCurrentBlockLocation().translate(-1, 0))
                .withNumFramesSinceLastMove(0);
    }

    /**
     * Executes the right action.
     * 
     * @param state game state
     * @return updated game state
     */
    private OnePlayerGameState executeMoveRight(OnePlayerGameState state) {
        OnePlayerGameState result;
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
    private OnePlayerGameState moveRight(OnePlayerGameState state) {
        return state.withCurrentBlockLocation(state.getCurrentBlockLocation().translate(1, 0))
                .withNumFramesSinceLastMove(0);
    }
    
    /**
     * Executes the instant drop action.
     * 
     * @param state game state
     * @return updated game state
     */
    private OnePlayerGameState executeInstantDrop(OnePlayerGameState state) {
        OnePlayerGameState result = state;
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
    private OnePlayerGameState executeTurnLeft(OnePlayerGameState state) {
        OnePlayerGameState stateAfterTurn = turnLeft(state);
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
    private OnePlayerGameState turnLeft(OnePlayerGameState state) {
        return state.withCurrentBlockOrientation(state.getCurrentBlockOrientation().getNextCounterClockwise())
                .withNumFramesSinceLastMove(0);
    }
    
    /**
     * Executes the turn right action.
     * 
     * @param state game state
     * @return updated game state
     */
    private OnePlayerGameState executeTurnRight(OnePlayerGameState state) {
        OnePlayerGameState stateAfterTurn = turnRight(state);
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
    private OnePlayerGameState turnRight(OnePlayerGameState state) {
        return state.withCurrentBlockOrientation(state.getCurrentBlockOrientation().getNextClockwise())
                .withNumFramesSinceLastMove(0);
    }

    /**
     * Executes the hold action.
     * 
     * @param state game state
     * @return updated game state
     */
    private OnePlayerGameState executeHold(OnePlayerGameState state) {
        OnePlayerGameState stateAfterHold = hold(state);
        return fixStateAfterAction(state, stateAfterHold);
    }

    /**
     * Performs a hold (swaps active and next tetrominoes).
     * 
     * This method does not check whether the resulting game state is valid! 
     * 
     * @param state state
     * @returnupdated game state (may be invalid)
     */
    private OnePlayerGameState hold(OnePlayerGameState state) {
        return state.withActiveTetromino(state.getNext())
                .withNext(state.getActiveTetromino())
                .withNumFramesSinceLastMove(0);
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
    private OnePlayerGameState fixStateAfterAction(OnePlayerGameState originalState,
            OnePlayerGameState stateAfterAction) {
        OnePlayerGameState result;
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
