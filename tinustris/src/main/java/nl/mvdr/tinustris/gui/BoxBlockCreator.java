package nl.mvdr.tinustris.gui;

import javafx.scene.shape.Box;
import nl.mvdr.tinustris.model.Block;

/**
 * Implementation of {@link BlockCreator}, which creates 2D Rectangles.
 * 
 * @author Martijn van de Rijdt
 */
class BoxBlockCreator implements BlockCreator {
    /** Size for the arc of a tetromino block. */
    static final int ARC_SIZE = 10;
    
    /** {@inheritDoc} */
    @Override
    public Box createBlock(double xCoordinate, double yCoordinate, double size, Block block, BlockStyle style,
            int numFramesUntilLinesDisappear, int numFramesSinceLastLock) {
        Box result = new Box(size, size, size);
        result.setTranslateX(xCoordinate);
        result.setTranslateY(yCoordinate);
        
        style.apply(result, block, numFramesUntilLinesDisappear, numFramesSinceLastLock);
        
        return result;
    }

}
