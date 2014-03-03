package nl.mvdr.tinustris.gui;

import java.util.Arrays;
import java.util.List;

import javafx.scene.Group;
import javafx.scene.Node;
import nl.mvdr.tinustris.model.OnePlayerGameState;
import nl.mvdr.tinustris.model.Orientation;
import nl.mvdr.tinustris.model.Point;
import nl.mvdr.tinustris.model.Tetromino;

/**
 * Shows the upcoming block.
 * 
 * @author Martijn van de Rijdt
 */
class NextBlockRenderer extends BlockGroupRenderer {
    /** Previous value for the next tetromino field, currently being displayed. Initially null. */
    private Tetromino previousValue = null;
    
    /** {@inheritDoc} */
    @Override
    public void render(OnePlayerGameState gameState) {
        super.render(gameState);
        previousValue = gameState.getNext();
    }
    
    /** {@inheritDoc} */
    @Override
    List<Group> createGroups(OnePlayerGameState gameState) {
        Group group;
        
        Tetromino nextBlock = gameState.getNext();
        if (previousValue != null && previousValue == nextBlock) {
            // Active block location is unchanged; no need to update.
            group = null;
        } else {
            // This is the first frame, or the active block's location has changed.
            // Render the group.
            group = new Group();
            for (Point point : nextBlock.getPoints(Orientation.getDefault())) {
                // for aesthetics, center the tetromino
                if (nextBlock != Tetromino.O) {
                    point = point.translate(0, -1);
                }
                Node node = createBlock(point.getX(), point.getY(), 4, nextBlock.getBlock(), BlockStyle.NEXT,
                        gameState.getNumFramesUntilLinesDisappear(), gameState.getNumFramesSinceLastLock());
                group.getChildren().add(node);
            }
        }
        
        return Arrays.asList(group);
    }
}
