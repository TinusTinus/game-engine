package nl.mvdr.tinustris.gui;

import javafx.scene.shape.Rectangle;
import nl.mvdr.tinustris.model.Block;

/**
 * Creates nodes to represent blocks.
 * 
 * @author Martijn van de Rijdt
 */
@FunctionalInterface
interface BlockCreator {
    /**
     * Creates a node to represent the given block.
     * 
     * @param xCoordinate
     *            x coordinate on screen
     * @param y
     *            y coordinate on screen
     * @param size
     *            size of the block
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
    Rectangle createBlock(double xCoordinate, double yCoordinate, double size, Block block, BlockStyle style,
            int numFramesUntilLinesDisappear, int numFramesSinceLastLock);
}
