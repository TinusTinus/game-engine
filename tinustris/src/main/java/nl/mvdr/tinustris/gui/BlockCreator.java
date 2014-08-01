package nl.mvdr.tinustris.gui;

import javafx.scene.Node;
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
     * @param yCoordinate
     *            y coordinate on screen
     * @param size
     *            size of the block
     * @param block
     *            block to be drawn
     * @param style
     *            style in which to render the block
     * @param numFramesUntilLinesDisappear
     *            the numFramesUntilLinesDisappear property from the game state: the number of frames until the
     *            currently disappearing lines disappear; used for correctly drawing the disappearing block animation
     * @param numFramesSinceLastLock
     *            the numFramesSinceLastLock property from the game state: the number of frames since the last time a
     *            block locked in place; used for correctly drawing the disappearing block animation
     * @return new block
     */
    Node createBlock(double xCoordinate, double yCoordinate, double size, Block block, BlockStyle style,
            int numFramesUntilLinesDisappear, int numFramesSinceLastLock);
}
