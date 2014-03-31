package nl.mvdr.tinustris.gui;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
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
    /** Previous game state, currently being displayed. Initially empty. */
    private Optional<OnePlayerGameState> previousState;

    /**
     * Constructor.
     * 
     * @param blockCreator creator
     */
    GridRenderer(BlockCreator blockCreator) {
        super(blockCreator);
        previousState = Optional.empty();
    }
    
    /** {@inheritDoc} */
    @Override
    public void render(OnePlayerGameState gameState) {
        super.render(gameState);
        previousState = Optional.of(gameState);
    }
    
    /** {@inheritDoc} */
    @Override
    List<Optional<Group>> createGroups(OnePlayerGameState gameState) {
        // create groups if state has changed; otherwise these groups may remain null
        Optional<Group> grid = createGridGroup(gameState);
        Optional<Group> ghost = createGhostGroup(gameState);
        Optional<Group> activeBlock = createActiveBlockGroup(gameState);
        
        return Arrays.asList(grid, ghost, activeBlock);
    }

    /**
     * Creates the group representing the static grid (blocks that have already been locked in place.
     * 
     * @param gameState new game state
     * @return null if the group does not need to be updated; otherwise, the newly created group
     */
    private Optional<Group> createGridGroup(final OnePlayerGameState gameState) {
        Optional<Group> result;
        // Use == instead of equals since it's much more efficient and there is a low chance of collisions.
        if (previousState.filter(s -> s.getGrid() == gameState.getGrid()).isPresent()) {
            // Grid is unchanged; no need to update.
            result = Optional.empty();
        } else {
            // This is the first frame, or the grids aren't the same object.
            // In the latter case they might still be equal, but that is not very likely.
            // Render the group.
            result = Optional.of(new Group());
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
                        result.get().getChildren().add(node);
                    }
                }
            }
        }
        return result;
    }

    /**
     * Creates the group representing the ghost.
     * 
     * @param gameState new game state
     * @return null if the group does not need to be updated; otherwise, the newly created group
     */
    private Optional<Group> createGhostGroup(final OnePlayerGameState gameState) {
        Optional<Group> result;
        Set<Point> ghostPoints = gameState.getGhostPoints();
        if (previousState.filter(p -> p.getGhostPoints().equals(ghostPoints)).isPresent()) {
            // Ghost location is unchanged; no need to update.
            result = Optional.empty();
        } else {
            // This is the first frame, or the ghost has changed.
            // Render the group.
            result = Optional.of(new Group());
            int height = gameState.getHeight() - OnePlayerGameState.VANISH_ZONE_HEIGHT;
            for (Point point : ghostPoints) {
                Node node = createBlock(point.getX(), point.getY(), height,
                        gameState.getActiveTetromino().get().getBlock(), BlockStyle.GHOST,
                        gameState.getNumFramesUntilLinesDisappear(), gameState.getNumFramesSinceLastLock());
                result.get().getChildren().add(node);
            }
        }
        return result;
    }

    /**
     * Creates the group representing the currently active block.
     * 
     * @param gameState new game state
     * @return null if the group does not need to be updated; otherwise, the newly created group
     */
    private Optional<Group> createActiveBlockGroup(final OnePlayerGameState gameState) {
        Optional<Group> result;
        Set<Point> currentActiveBlockPoints = gameState.getCurrentActiveBlockPoints();
        if (previousState.filter(s -> s.getCurrentActiveBlockPoints().equals(currentActiveBlockPoints)).isPresent()) {
            // Active block location is unchanged; no need to update.
            result = Optional.empty();
        } else {
            // This is the first frame, or the active block's location has changed.
            // Render the group.
            result = Optional.of(new Group());
            int height = gameState.getHeight() - OnePlayerGameState.VANISH_ZONE_HEIGHT;
            for (Point point : currentActiveBlockPoints) {
                Node node = createBlock(point.getX(), point.getY(), height,
                        gameState.getActiveTetromino().get().getBlock(), BlockStyle.ACTIVE,
                        gameState.getNumFramesUntilLinesDisappear(), gameState.getNumFramesSinceLastLock());
                result.get().getChildren().add(node);
            }
        }
        return result;
    }
}
