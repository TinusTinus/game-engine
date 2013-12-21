package nl.mvdr.tinustris.gui;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import nl.mvdr.tinustris.model.Tetromino;

/**
 * Graphical styles of blocks.
 * 
 * @author Martijn van de Rijdt
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
enum BlockStyle {
    /** Currently active blocks; that is, the tetromino that is currently being controlled by the player. */
    ACTIVE(1, false), 
    /** Blocks that have already been dropped down. */
    GRID(1, true),
    /**
     * Ghost blocks, that is, the blocks that indicate where the currently active block would land if dropped
     * straight down.
     */
    GHOST(.1, false);

    /** Opacity. */
    private final double opacity;
    /** Indicates whether the block should be shown a shade darker than normal. */
    private final boolean darker;
    
    /**
     * Applies this style to the given block. This method sets the fill and the opacity of the given block. 
     * 
     * @param block block to be styled
     * @param tetromino tetromino represented by the given block
     */
    void apply(Rectangle block, Tetromino tetromino) {
        Color color = getColor(tetromino);
        if (darker) {
            color = color.darker();
        }
        block.setFill(color);
        
        block.setOpacity(opacity);
    }
    
    /**
     * Retrieves the color for the given tetromino.
     * 
     * @param tetromino tetromino
     * @return color
     */
    private static Color getColor(Tetromino tetromino) {
        Color result;
        if (tetromino == Tetromino.I) {
            result = Color.CYAN;
        } else if (tetromino == Tetromino.O) {
            result = Color.YELLOW;
        } else if (tetromino == Tetromino.T) {
            result = Color.PURPLE;
        } else if (tetromino == Tetromino.S) {
            result = Color.GREEN;
        } else if (tetromino == Tetromino.Z) {
            result = Color.RED;
        } else if (tetromino == Tetromino.J) {
            result = Color.BLUE;
        } else if (tetromino == Tetromino.L) {
            result = Color.ORANGE;
        } else {
            throw new IllegalArgumentException("Unexpected Tetromino: " + tetromino);
        }
        return result;
    }
}
