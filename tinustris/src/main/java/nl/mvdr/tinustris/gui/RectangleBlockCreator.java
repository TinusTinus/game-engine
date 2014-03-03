package nl.mvdr.tinustris.gui;

import javafx.scene.shape.Rectangle;
import nl.mvdr.tinustris.model.Block;

/**
 * Implementation of {@link BlockCreator}, which creates 2D Rectangles.
 * 
 * @author Martijn van de Rijdt
 */
public class RectangleBlockCreator implements BlockCreator {
    /** Size for the arc of a tetromino block. */
    static final int ARC_SIZE = 10;
    
    /** {@inheritDoc} */
    @Override
    public Rectangle createBlock(double xCoordinate, double yCoordinate, double size, Block block, BlockStyle style,
            int numFramesUntilLinesDisappear, int numFramesSinceLastLock) {
        Rectangle result = new Rectangle(xCoordinate, yCoordinate, size, size);
        
        result.setArcWidth(ARC_SIZE);
        result.setArcHeight(ARC_SIZE);
        
        style.apply(result, block, numFramesUntilLinesDisappear, numFramesSinceLastLock);
        
        return result;
    }

}
