package nl.mvdr.tinustris.gui;

import java.util.EnumMap;
import java.util.Map;

import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.Paint;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape3D;
import javafx.scene.shape.StrokeType;
import javafx.util.Duration;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import nl.mvdr.tinustris.engine.GameLoop;
import nl.mvdr.tinustris.model.Block;

/**
 * Graphical styles of blocks.
 * 
 * @author Martijn van de Rijdt
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
enum BlockStyle {
    /** Currently active blocks; that is, the tetromino that is currently being controlled by the player. */
    ACTIVE(1, null, false, false), 
    /** Blocks that have already been dropped down. */
    GRID(1, null, true, false),
    /**
     * Ghost blocks, that is, the blocks that indicate where the currently active block would land if dropped
     * straight down.
     */
    GHOST(.1, Color.WHITE, false, false),
    /** Grid block that is part of a line and about to disappear. */
    DISAPPEARING(1, null, false, true),
    /** Next block to appear. */
    NEXT(1, null, false, false);

    /** The number of milliseconds in a second. */
    private static final int MILLISECONDS_PER_SECOND = 1000;
    /** Color mapping for each type of tetromino. */
    @SuppressWarnings("serial") // this map is for internal use only, will not be serialised
    private static final Map<Block, Color> COLORS = new EnumMap<Block, Color>(Block.class) {{
        put(Block.I, Color.CYAN);
        put(Block.O, Color.YELLOW);
        put(Block.T, Color.PURPLE);
        put(Block.S, Color.GREEN);
        put(Block.Z, Color.RED);
        put(Block.J, Color.BLUE);
        put(Block.L, Color.ORANGE);
        put(Block.GARBAGE, Color.GRAY);
    }};
    
    /** Opacity. */
    private final double opacity;
    /** Stroke for the block. May be null (for a stroke equal to the tetromino's color). */
    private final Paint stroke;
    /** Indicates whether the block should be shown a shade darker than normal. */
    private final boolean darker;
    /** Indicates whether the block should fade out. */
    private final boolean disappearingAnimation;

    /**
     * Applies this style to the given block. This method sets the fill, the opacity and stroke properties of the given
     * block.
     * 
     * @param rectangle
     *            block to be styled
     * @param block
     *            block
     * @param numFramesUntilLinesDisappear
     *            the numFramesUntilLinesDisappear property from the game state; used for the block disappearing
     *            animation
     * @param numFramesSinceLastLock
     *            the numFramesSinceLastLock property from the game state; used for the block disappearing animation
     */
    void apply(@NonNull Rectangle rectangle, @NonNull Block block, int numFramesUntilLinesDisappear,
            int numFramesSinceLastLock) {
        rectangle.setOpacity(opacity);
        applyStroke(rectangle, block);
        applyFill(rectangle, block);
        applyAnimation(rectangle, numFramesUntilLinesDisappear, numFramesSinceLastLock);
    }
    
    /**
     * Applies this style to the given block. This method sets the the opacity and color properties of the given
     * block.
     * 
     * @param shape
     *            block to be styled
     * @param block
     *            block
     * @param numFramesUntilLinesDisappear
     *            the numFramesUntilLinesDisappear property from the game state; used for the block disappearing
     *            animation
     * @param numFramesSinceLastLock
     *            the numFramesSinceLastLock property from the game state; used for the block disappearing animation
     */
    void apply(@NonNull Shape3D shape, @NonNull Block block, int numFramesUntilLinesDisappear,
            int numFramesSinceLastLock) {
        // Unfortunately opacity is currently not supported in JavaFX 3D graphics.
        // See: https://javafx-jira.kenai.com/browse/RT-28874
        // For now, just hide blocks which are supposed to be opaque (in practice: ghost blocks).
        if (opacity < 1) {
            shape.setVisible(false);;
        }
        applyColor(shape, block);
        applyAnimation(shape, numFramesUntilLinesDisappear, numFramesSinceLastLock);
    }

    /**
     * Sets the block's stroke property.
     * 
     * @param rectangle
     *            block to be styled
     * @param block
     *            block
     */
    private void applyStroke(Rectangle rectangle, Block block) {
        rectangle.setStrokeWidth(2);
        rectangle.setStrokeType(StrokeType.INSIDE);
        if (stroke != null) {
            rectangle.setStroke(stroke);
        } else {
            rectangle.setStroke(COLORS.get(block));
        }
    }

    /**
     * Sets the block's fill property.
     * 
     * @param rectangle
     *            block to be styled
     * @param block
     *            block
     */
    private void applyFill(Rectangle rectangle, Block block) {
        Color color = getColor(block);
        Paint fill = new RadialGradient(0,
                1,
                rectangle.getX() + rectangle.getWidth() / 4,
                rectangle.getY() + rectangle.getHeight() / 4,
                20,
                false,
                CycleMethod.NO_CYCLE,
                new Stop(0, color.brighter()),
                new Stop(1, color.darker()));
        rectangle.setFill(fill);
    }

    /**
     * Sets the box's color.
     * 
     * @param shape
     *            block to be styled
     * @param block
     *            block
     */
    private void applyColor(Shape3D shape, Block block) {
        Color color = getColor(block).brighter();
        shape.setMaterial(new PhongMaterial(color));
    }
    
    /**
     * Retrieves the correct color for the given block.
     * 
     * @param block block
     * @return color
     */
    private Color getColor(Block block) {
        Color color = COLORS.get(block);
        if (darker) {
            color = color.darker();
        }
        return color;
    }

    /**
     * Adds any needed animation to the block.
     * 
     * @param rectangle
     *            block to be styled
     * @param numFramesUntilLinesDisappear
     *            number of frames until the block should be fully invisible
     * @param numFramesSinceLastLock
     *            number of frames since the time the block started disappearing
    */
    private void applyAnimation(Rectangle rectangle, int numFramesUntilLinesDisappear, int numFramesSinceLastLock) {
        if (disappearingAnimation) {
            int duration = framesToMilliseconds(numFramesUntilLinesDisappear);
            FadeTransition fadeTransition = new FadeTransition(Duration.millis(duration), rectangle);
            fadeTransition.setFromValue((double) numFramesUntilLinesDisappear
                    / (double) (numFramesUntilLinesDisappear + numFramesSinceLastLock));
            fadeTransition.setToValue(0);
            fadeTransition.play();
        }
    }
    
    /**
     * Adds any needed animation to the block.
     * 
     * @param shape
     *            block to be styled
     * @param numFramesUntilLinesDisappear
     *            number of frames until the block should be fully invisible
     * @param numFramesSinceLastLock
     *            number of frames since the time the block started disappearing
    */
    private void applyAnimation(Shape3D shape, int numFramesUntilLinesDisappear, int numFramesSinceLastLock) {
        if (disappearingAnimation) {
            int duration = framesToMilliseconds(numFramesUntilLinesDisappear);
            ScaleTransition transition = new ScaleTransition(Duration.millis(duration), shape);
            transition.setFromY(shape.getScaleY() * ((double) numFramesUntilLinesDisappear
                    / (double) (numFramesUntilLinesDisappear + numFramesSinceLastLock)));
            transition.setToY(0);
            transition.play();
        }
    }

    /**
     * Converts a number of frames to milliseconds.
     * 
     * @param frames frames
     * @return time in seconds
     */
    private int framesToMilliseconds(int frames) {
        return frames * MILLISECONDS_PER_SECOND / (int) GameLoop.GAME_HERTZ;
    }
}
