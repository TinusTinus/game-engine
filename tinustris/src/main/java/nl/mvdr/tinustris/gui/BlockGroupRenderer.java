package nl.mvdr.tinustris.gui;

import java.util.List;

import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Node;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import nl.mvdr.tinustris.model.Block;
import nl.mvdr.tinustris.model.OnePlayerGameState;

/**
 * JavaFX node that contains a group of blocks.
 * 
 * @author Martijn van de Rijdt
 */
@RequiredArgsConstructor
abstract class BlockGroupRenderer extends Group implements GameRenderer<OnePlayerGameState> {
    /** Size of a tetromino block. */
    static final int BLOCK_SIZE = 30;
    
    /** Block creator. */
    private final BlockCreator blockCreator;
    
    /** {@inheritDoc} */
    @Override
    public void render(@NonNull OnePlayerGameState gameState) {
        final List<Group> groups = createGroups(gameState);
        
        if (groups.stream().anyMatch(value -> value != null)) {
            // (re-)render
            runOnJavaFXThread(() -> update(groups));
        }
    }
    
    /**
     * Creates all groups of blocks based on the given game state. In case a group would be equal to the group already
     * rendered, a null value may be inserted into the list. In this case the old group is not rerendered.
     * 
     * @param gameState new game state
     * @return list of groups; may contain null values
     */
    abstract List<Group> createGroups(OnePlayerGameState gameState);
    
    /**
     * Runs the given runnable on the JavaFX thread.
     * 
     * @param runnable
     *            runnable
     */
    // Default visibility as an extension point for unit tests.
    void runOnJavaFXThread(Runnable runnable) {
        Platform.runLater(runnable);
    }
    
    /**
     * Updates the view. This method must be called from the JavaFX thread.
     * 
     * @param groups
     *            list of groups; elements may be null, in which case the corresponding child group is not updated
     */
    private void update(List<Group> groups) {
        if (getChildren().isEmpty()) {
            // first frame
            getChildren().addAll(groups);
        } else {
            // update
            for (int i = 0; i != groups.size(); i++) {
                Group group = groups.get(i);
                if (group != null) {
                    getChildren().set(i, groups.get(i));
                }
            }
        }
    }

    /**
     * Creates a block.
     * 
     * @param x
     *            x coordinate in the grid
     * @param y
     *            y coordinate in the grid
     * @param height
     *            height of the (visible part of the) grid
     * @param block
     *            block to be drawn
     * @param style
     *            style in which to render the block
     * @param numFramesUntilLinesDisappear
     *            the numFramesUntilLinesDisappear property from the game state
     * @param numFramesSinceLastLock
     *            the numFramesSinceLastLock property from the game state
     * @return new block
     */
    Node createBlock(int x, int y, int height, Block block, BlockStyle style,
            int numFramesUntilLinesDisappear, int numFramesSinceLastLock) {
        int xCoordinate = x * BLOCK_SIZE;
        int yCoordinate = (height - y - 1) * BLOCK_SIZE;
        
        return blockCreator.createBlock(xCoordinate, yCoordinate, BLOCK_SIZE, block, style,
                numFramesUntilLinesDisappear, numFramesSinceLastLock);
    }
}
