package nl.mvdr.tinustris.gui;

import java.util.Iterator;
import java.util.List;

import nl.mvdr.tinustris.model.GameState;
import nl.mvdr.tinustris.model.Tetromino;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.shape.Rectangle;
import lombok.NonNull;

/**
 * JavaFX node that contains a group of blocks.
 * 
 * @author Martijn van de Rijdt
 */
abstract class BlockGroupRenderer extends Group implements GameRenderer {
    /** Size of a tetromino block. */
    static final int BLOCK_SIZE = 30;
    /** Size for the arc of a tetromino block. */
    static final int ARC_SIZE = 10;
    
    /** {@inheritDoc} */
    @Override
    public void render(@NonNull GameState gameState) {
        final List<Group> groups = createGroups(gameState);
        
        if (!containsAllNullValues(groups)) {
            // (re-)render
            runOnJavaFXThread(new Runnable() {
                /** {@inheritDoc} */
                @Override
                public void run() {
                    update(groups);
                }
            });
        }
    }
    
    /**
     * Creates all groups of blocks based on the given game state. In case a group would be equal to the group already
     * rendered, a null value may be inserted into the list. In this case the old group is not rerendered.
     * 
     * @param gameState new game state
     * @return list of groups; may contain null values
     */
    abstract List<Group> createGroups(GameState gameState);
    
    /**
     * Checks wheter the list contains only null values.
     * 
     * @param list list
     * @return whether the list contains only null values
     */
    private boolean containsAllNullValues(List<?> list) {
        boolean result = true;
        Iterator<?> iterator = list.iterator();
        while (result && iterator.hasNext()) {
            result = iterator.next() == null;
        }
        return result;
    }
    
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
    void update(List<Group> groups) {
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
     *            height of the grid
     * @param tetromino
     *            tetromino to be represented by the block
     * @param style
     *            style in which to render the block 
     * @return new block
     */
    Rectangle createBlock(int x, int y, int height, Tetromino tetromino, BlockStyle style) {
        int xCoordinate = x * BLOCK_SIZE;
        int yCoordinate = height * BLOCK_SIZE - 3 * BLOCK_SIZE - y * BLOCK_SIZE;
        
        Rectangle result = new Rectangle(xCoordinate, yCoordinate, BLOCK_SIZE, BLOCK_SIZE);
        
        result.setArcWidth(ARC_SIZE);
        result.setArcHeight(ARC_SIZE);
        
        style.apply(result, tetromino);
        
        return result;
    }
}
