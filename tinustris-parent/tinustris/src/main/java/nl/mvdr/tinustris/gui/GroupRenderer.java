package nl.mvdr.tinustris.gui;

import java.util.Arrays;

import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.shape.Rectangle;
import nl.mvdr.tinustris.model.GameState;
import nl.mvdr.tinustris.model.Point;
import nl.mvdr.tinustris.model.Tetromino;

/**
 * Implementation of {@link GameRenderer} which renders a graphical representation of the game state to a JavaFX group.
 * 
 * @author Martijn van de Rijdt
 */
public class GroupRenderer implements GameRenderer<Group> {
    /** Size of a tetromino block. */
    private static final int BLOCK_SIZE = 30;
    /** Size for the arc of a tetromino block. */
    private static final int ARC_SIZE = 10;

    /** {@inheritDoc} */
    @Override
    public void render(final Group node, final GameState gameState) {
        int height = gameState.getHeight();
        
        final Group grid = new Group();
        for (int x = 0; x != gameState.getWidth(); x++) {
            for (int y = 0; y != height; y++) {
                Tetromino tetromino = gameState.getBlock(x, y);
                if (tetromino != null) {
                    Rectangle block = createBlock(x, y, height, tetromino, BlockStyle.GRID);
                    grid.getChildren().add(block);
                }
            }
        }

        final Group ghost = new Group();
        for (Point point: gameState.getGhostPoints()) {
            Rectangle block = createBlock(point.getX(), point.getY(), height, gameState.getCurrentBlock(),
                    BlockStyle.GHOST);
            ghost.getChildren().add(block);
        }
        
        final Group activeBlock = new Group();
        for (Point point: gameState.getCurrentActiveBlockPoints()) {
            Rectangle block = createBlock(point.getX(), point.getY(), height, gameState.getCurrentBlock(),
                    BlockStyle.ACTIVE);
            activeBlock.getChildren().add(block);
        }
        
        runOnJavaFXThread(new Runnable() {
            /** {@inheritDoc} */
            @Override
            public void run() {
                node.getChildren().setAll(Arrays.asList(grid, ghost, activeBlock));
            }
        });
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
