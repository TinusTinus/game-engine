package nl.mvdr.tinustris.gui;

import java.util.EnumMap;
import java.util.Map;

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

    /** Color mapping for each type of tetromino. */
    @SuppressWarnings("serial") // this map is for internal use only, will not be serialised
    private static final Map<Tetromino, Color> COLORS = new EnumMap<Tetromino, Color>(Tetromino.class) {{
        put(Tetromino.I, Color.CYAN);
        put(Tetromino.O, Color.YELLOW);
        put(Tetromino.T, Color.PURPLE);
        put(Tetromino.S, Color.GREEN);
        put(Tetromino.Z, Color.RED);
        put(Tetromino.J, Color.BLUE);
        put(Tetromino.L, Color.ORANGE);
    }};
    
    /** Opacity. */
    private final double opacity;
    /** Stroke for the block. May be null (for no stroke). */
    private final Paint stroke;
    /** Indicates whether the block should be shown a shade darker than normal. */
    private final boolean darker;

    /**
     * Applies this style to the given block. This method sets the fill, the opacity and stroke properties of the given
     * block.
     * 
     * @param block
     *            block to be styled
     * @param tetromino
     *            tetromino represented by the given block
     */
    void apply(@NonNull Rectangle block, @NonNull Tetromino tetromino) {
        // opacity
        block.setOpacity(opacity);
        
        // stroke
        block.setStrokeWidth(2);
        block.setStrokeType(StrokeType.INSIDE);
        block.setStroke(stroke);
        
        // fill
        Color color = COLORS.get(tetromino);
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
}
