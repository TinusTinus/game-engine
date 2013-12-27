package nl.mvdr.tinustris.gui;

import java.util.Arrays;
import java.util.Set;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import nl.mvdr.tinustris.model.GameState;
import nl.mvdr.tinustris.model.Point;
import nl.mvdr.tinustris.model.Tetromino;

/**
 * JavaFX node that contains the graphical representation of the grid.
 * 
 * @author Martijn van de Rijdt
 */
public class GridGroup extends Group {
    /** Size of a tetromino block. */
    private static final int BLOCK_SIZE = 30;
    /** Size for the arc of a tetromino block. */
    private static final int ARC_SIZE = 10;
    /** The number of milliseconds in a second. */
    private static final int MILLISECONDS_PER_SECOND = 1000;

    /** Previous game state, currently being displayed. Initially null. */
    private GameState previousState = null;

    /**
     * Renders the given game state. Does NOT need to be called from the JavaFX thread.
     * 
     * @param gameState game state to be rendered
     */
    public void render(final GameState gameState) {
        // create groups if state has changed; otherwise these groups may remain null
        final Group grid = createGridGroup(gameState);
        final Group ghost = createGhostGroup(gameState);
        final Group activeBlock = createActiveBlockGroup(gameState);
        
        if (grid != null || ghost != null || activeBlock != null) {
            // (re-)render
            runOnJavaFXThread(new Runnable() {
                /** {@inheritDoc} */
                @Override
                public void run() {
                    update(grid, ghost, activeBlock);
                }
            });
        }
        
        previousState = gameState;
    }

    /**
     * Creates the group representing the static grid (blocks that have already been locked in place.
     * 
     * @param gameState new game state
     * @return null if the group does not need to be updated; otherwise, the newly created group
     */
    private Group createGridGroup(final GameState gameState) {
        final Group grid;
        // Use == instead of equals since it's much more efficient and there is a low chance of collisions.
        if (previousState != null && previousState.getGrid() == gameState.getGrid()) {
            // Grid is unchanged; no need to update.
            grid = null;
        } else {
            // This is the first frame, or the grids aren't the same object.
            // In the latter case they might still be equal, but whatever.
            // Render the group.
            grid = new Group();
            int height = gameState.getHeight();
            for (int y = 0; y != height; y++) {
                boolean isFullLine = gameState.isFullLine(y);
                for (int x = 0; x != gameState.getWidth(); x++) {
                    Tetromino tetromino = gameState.getBlock(x, y);
                    if (tetromino != null) {
                        Rectangle block = createBlock(x, y, height, tetromino, BlockStyle.GRID);
                        
                        if (isFullLine) {
                            // add an animation to make the line disappear slowly
                            // TODO get fps from the same constant used in the main game loop
                            int duration = GameState.FRAMES_LINES_STAY * MILLISECONDS_PER_SECOND / 60;
                            FadeTransition ft = new FadeTransition(Duration.millis(duration), block);
                            ft.setFromValue(1);
                            ft.setToValue(0);
                            ft.play();
                        }
                        
                        grid.getChildren().add(block);
                    }
                }
            }
        }
        return grid;
    }

    /**
     * Creates the group representing the ghost.
     * 
     * @param gameState new game state
     * @return null if the group does not need to be updated; otherwise, the newly created group
     */
    private Group createGhostGroup(final GameState gameState) {
        final Group ghost;
        Set<Point> ghostPoints = gameState.getGhostPoints();
        if (previousState != null && previousState.getGhostPoints().equals(ghostPoints)) {
            // Ghost location is unchanged; no need to update.
            ghost = null;
        } else {
            // This is the first frame, or the ghost has changed.
            // Render the group.
            ghost = new Group();
            int height = gameState.getHeight();
            for (Point point : ghostPoints) {
                Rectangle block = createBlock(point.getX(), point.getY(), height, gameState.getCurrentBlock(),
                        BlockStyle.GHOST);
                ghost.getChildren().add(block);
            }
        }
        return ghost;
    }

    /**
     * Creates the group representing the currently active block.
     * 
     * @param gameState new game state
     * @return null if the group does not need to be updated; otherwise, the newly created group
     */
    private Group createActiveBlockGroup(final GameState gameState) {
        final Group activeBlock;
        Set<Point> currentActiveBlockPoints = gameState.getCurrentActiveBlockPoints();
        if (previousState != null && previousState.getCurrentActiveBlockPoints().equals(currentActiveBlockPoints)) {
            // Active block location is unchanged; no need to update.
            activeBlock = null;
        } else {
            // This is the first frame, or the active block's location has changed.
            // Render the group.
            activeBlock = new Group();
            int height = gameState.getHeight();
            for (Point point : currentActiveBlockPoints) {
                Rectangle block = createBlock(point.getX(), point.getY(), height, gameState.getCurrentBlock(),
                        BlockStyle.ACTIVE);
                activeBlock.getChildren().add(block);
            }
        }
        return activeBlock;
    }
    
    /**
     * Updates the view. This method must be called from the JavaFX thread.
     * 
     * @param grid
     *            group representing the static grid; may be null, which indicates that the grid does not need to be
     *            updated
     * @param ghost
     *            group representing the ghost blocks; may be null, which indicates that the ghost does not need to be
     *            updated
     * @param activeBlock
     *            group representing the active block; may be null, which indicates that the active block does not need
     *            to be updated
     */
    private void update(final Group grid, final Group ghost, final Group activeBlock) {
        if (getChildren().isEmpty()) {
            // first frame
            getChildren().addAll(Arrays.asList(grid, ghost, activeBlock));
        } else {
            // update
            if (grid != null) {
                getChildren().set(0, grid);
            }
            if (ghost != null) {
                getChildren().set(1, ghost);
            }
            if (activeBlock != null) {
                getChildren().set(2, activeBlock);
            }
        }
    }


    /**
     * Runs the given runnable on the JavaFX thread.
     * 
     * @param runnable
     *            runnable
     */
    private void runOnJavaFXThread(Runnable runnable) {
        Platform.runLater(runnable);
    }

    /**
     * Creates a block.
     * 
     * @param x
     *            x coordinate in the grid
     * @param y
     *            y coordinate in the grid
     * @param height
     *            height of the grid
     * @param tetromino
     *            tetromino to be represented by the block
     * @param style
     *            style in which to render the block 
     * @return new block
     */
    private Rectangle createBlock(int x, int y, int height, Tetromino tetromino, BlockStyle style) {
        int xCoordinate = x * BLOCK_SIZE;
        int yCoordinate = height * BLOCK_SIZE - 3 * BLOCK_SIZE - y * BLOCK_SIZE;
        
        Rectangle result = new Rectangle(xCoordinate, yCoordinate, BLOCK_SIZE, BLOCK_SIZE);
        
        result.setArcWidth(ARC_SIZE);
        result.setArcHeight(ARC_SIZE);
        
        style.apply(result, tetromino);
        
        return result;
    }
}
