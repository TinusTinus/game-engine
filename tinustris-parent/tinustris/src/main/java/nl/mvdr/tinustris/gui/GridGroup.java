package nl.mvdr.tinustris.gui;

import java.util.Arrays;
import java.util.Set;

import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.shape.Rectangle;
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

    /** Previous game state, currently being displayed. Initially null. */
    private GameState previousState = null;

    /** {@inheritDoc} */
    public void render(final GameState gameState) {
        int height = gameState.getHeight();

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
            for (int x = 0; x != gameState.getWidth(); x++) {
                for (int y = 0; y != height; y++) {
                    Tetromino tetromino = gameState.getBlock(x, y);
                    if (tetromino != null) {
                        Rectangle block = createBlock(x, y, height, tetromino, BlockStyle.GRID);
                        grid.getChildren().add(block);
                    }
                }
            }
        }

        final Group ghost;
        Set<Point> ghostPoints = gameState.getGhostPoints();
        if (previousState != null && previousState.getGhostPoints().equals(ghostPoints)) {
            // Ghost location is unchanged; no need to update.
            ghost = null;
        } else {
            // This is the first frame, or the ghost has changed.
            // Render the group.
            ghost = new Group();
            for (Point point : ghostPoints) {
                Rectangle block = createBlock(point.getX(), point.getY(), height, gameState.getCurrentBlock(),
                        BlockStyle.GHOST);
                ghost.getChildren().add(block);
            }
        }
        
        final Group activeBlock;
        Set<Point> currentActiveBlockPoints = gameState.getCurrentActiveBlockPoints();
        if (previousState != null && previousState.getCurrentActiveBlockPoints().equals(currentActiveBlockPoints)) {
            // Active block location is unchanged; no need to update.
            activeBlock = null;
        } else {
            // This is the first frame, or the active block's location has changed.
            // Render the group.
            activeBlock = new Group();
            for (Point point : currentActiveBlockPoints) {
                Rectangle block = createBlock(point.getX(), point.getY(), height, gameState.getCurrentBlock(),
                        BlockStyle.ACTIVE);
                activeBlock.getChildren().add(block);
            }
        }
        
        if (grid != null || ghost != null || activeBlock != null) {
            runOnJavaFXThread(new Runnable() {
                /** {@inheritDoc} */
                @Override
                public void run() {
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
            });
        }
        
        previousState = gameState;
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
