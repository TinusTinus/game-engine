package nl.mvdr.tinustris.gui;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import javafx.scene.Group;
import javafx.scene.Node;
import nl.mvdr.tinustris.model.Block;
import nl.mvdr.tinustris.model.OnePlayerGameState;
import nl.mvdr.tinustris.model.Point;

/**
 * JavaFX node that contains the graphical representation of the grid.
 * 
 * @author Martijn van de Rijdt
 */
class GridRenderer extends BlockGroupRenderer {
    /** Previous game state, currently being displayed. Initially null. */
    private OnePlayerGameState previousState = null;

    /**
     * Constructor.
     * 
     * @param blockCreator creator
     */
    GridRenderer(BlockCreator blockCreator) {
        super(blockCreator);
    }
    
    /** {@inheritDoc} */
    @Override
    public void render(OnePlayerGameState gameState) {
        super.render(gameState);
        previousState = gameState;
    }
    
    /** {@inheritDoc} */
    @Override
    List<Group> createGroups(OnePlayerGameState gameState) {
        // create groups if state has changed; otherwise these groups may remain null
        Group grid = createGridGroup(gameState);
        Group ghost = createGhostGroup(gameState);
        Group activeBlock = createActiveBlockGroup(gameState);
        
        final List<Group> groups = Arrays.asList(grid, ghost, activeBlock);
        return groups;
    }

    /**
     * Creates the group representing the static grid (blocks that have already been locked in place.
     * 
     * @param gameState new game state
     * @return null if the group does not need to be updated; otherwise, the newly created group
     */
    private Group createGridGroup(final OnePlayerGameState gameState) {
        Group grid;
        // Use == instead of equals since it's much more efficient and there is a low chance of collisions.
        if (previousState != null && previousState.getGrid() == gameState.getGrid()) {
            // Grid is unchanged; no need to update.
            grid = null;
        } else {
            // This is the first frame, or the grids aren't the same object.
            // In the latter case they might still be equal, but whatever.
            // Render the group.
            grid = new Group();
            int height = gameState.getHeight() - OnePlayerGameState.VANISH_ZONE_HEIGHT;
            for (int y = 0; y != height; y++) {
                BlockStyle style;
                if (gameState.isFullLine(y)) {
                    style = BlockStyle.DISAPPEARING;
                } else {
                    style = BlockStyle.GRID;
                }
                
                for (int x = 0; x != gameState.getWidth(); x++) {
                    Block block = gameState.getBlock(x, y);
                    if (block != null) {
                        Node node = createBlock(x, y, height, block, style,
                                gameState.getNumFramesUntilLinesDisappear(), gameState.getNumFramesSinceLastLock());
                        grid.getChildren().add(node);
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
    private Group createGhostGroup(final OnePlayerGameState gameState) {
        Group ghost;
        Set<Point> ghostPoints = gameState.getGhostPoints();
        if (previousState != null && previousState.getGhostPoints().equals(ghostPoints)) {
            // Ghost location is unchanged; no need to update.
            ghost = null;
        } else {
            // This is the first frame, or the ghost has changed.
            // Render the group.
            ghost = new Group();
            int height = gameState.getHeight() - OnePlayerGameState.VANISH_ZONE_HEIGHT;
            for (Point point : ghostPoints) {
                Node node = createBlock(point.getX(), point.getY(), height,
                        gameState.getActiveTetromino().getBlock(), BlockStyle.GHOST,
                        gameState.getNumFramesUntilLinesDisappear(), gameState.getNumFramesSinceLastLock());
                ghost.getChildren().add(node);
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
    private Group createActiveBlockGroup(final OnePlayerGameState gameState) {
        Group activeBlock;
        Set<Point> currentActiveBlockPoints = gameState.getCurrentActiveBlockPoints();
        if (previousState != null && previousState.getCurrentActiveBlockPoints().equals(currentActiveBlockPoints)) {
            // Active block location is unchanged; no need to update.
            activeBlock = null;
        } else {
            // This is the first frame, or the active block's location has changed.
            // Render the group.
            activeBlock = new Group();
            int height = gameState.getHeight() - OnePlayerGameState.VANISH_ZONE_HEIGHT;
            for (Point point : currentActiveBlockPoints) {
                Node node = createBlock(point.getX(), point.getY(), height,
                        gameState.getActiveTetromino().getBlock(), BlockStyle.ACTIVE,
                        gameState.getNumFramesUntilLinesDisappear(), gameState.getNumFramesSinceLastLock());
                activeBlock.getChildren().add(node);
            }
        }
        return activeBlock;
    }
}
