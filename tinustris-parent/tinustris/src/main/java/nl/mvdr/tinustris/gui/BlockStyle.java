package nl.mvdr.tinustris.gui;

import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.Paint;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import lombok.AccessLevel;
import lombok.NonNull;
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
    ACTIVE(1, null, false), 
    /** Blocks that have already been dropped down. */
    GRID(1, null, true),
    /**
     * Ghost blocks, that is, the blocks that indicate where the currently active block would land if dropped
     * straight down.
     */
    GHOST(.1, Color.WHITE, false);

    /** Opacity. */
    private final double opacity;
    /** Stroke for the block. May be null (for no stroke). */
    private final Paint stroke;
    /** Indicates whether the block should be shown a shade darker than normal. */
    private final boolean darker;
    
    /**
     * Applies this style to the given block. This method sets the fill and the opacity of the given block. 
     * 
     * @param block block to be styled
     * @param tetromino tetromino represented by the given block
     */
    void apply(@NonNull Rectangle block, @NonNull Tetromino tetromino) {
        // opacity
        block.setOpacity(opacity);
        
        //stroke
        block.setStrokeWidth(2);
        block.setStrokeType(StrokeType.INSIDE);
        block.setStroke(stroke);
        
        // fill
        Color color = getColor(tetromino);
        if (darker) {
            color = color.darker();
        }
        Paint fill = new RadialGradient(0,
                1,
                block.getX() + block.getWidth() / 4,
                block.getY() + block.getHeight() / 4,
                20,
                false,
                CycleMethod.NO_CYCLE,
                new Stop(0, color.brighter()),
                new Stop(1, color.darker()));
        block.setFill(fill);
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
