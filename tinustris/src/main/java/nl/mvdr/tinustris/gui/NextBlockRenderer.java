package nl.mvdr.tinustris.gui;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
    /** Previous value for the next tetromino field, currently being displayed. Initially empty. */
    private Optional<Tetromino> previousValue;
    
    /**
     * Constructor.
     * 
     * @param blockCreator creator
     */
    NextBlockRenderer(BlockCreator blockCreator) {
        super(blockCreator);
        previousValue = Optional.empty();
    }
    
    /** {@inheritDoc} */
    @Override
    public void render(OnePlayerGameState gameState) {
        super.render(gameState);
        previousValue = Optional.of(gameState.getNext());
    }
    
    /** {@inheritDoc} */
    @Override
    List<Optional<Group>> createGroups(OnePlayerGameState gameState) {
        Optional<Group> group;
        
        Tetromino nextBlock = gameState.getNext();
        if (previousValue.filter(t -> t == nextBlock).isPresent()) {
            // Active block location is unchanged; no need to update.
            group = Optional.empty();
        } else {
            // This is the first frame, or the active block's location has changed.
            // Render the group.
            group = Optional.of(new Group());
            for (Point point : nextBlock.getPoints(Orientation.getDefault())) {
                // for aesthetics, center the tetromino
                if (nextBlock != Tetromino.O) {
                    point = point.translate(0, -1);
                }
                Node node = createBlock(point.getX(), point.getY(), 4, nextBlock.getBlock(), BlockStyle.NEXT,
                        gameState.getNumFramesUntilLinesDisappear(), gameState.getNumFramesSinceLastLock());
                group.get().getChildren().add(node);
            }
        }
        
        return Collections.singletonList(group);
    }
}
