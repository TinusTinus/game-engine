package nl.mvdr.tinustris.gui;

import javafx.scene.shape.Rectangle;
import lombok.extern.slf4j.Slf4j;
import nl.mvdr.tinustris.model.Tetromino;

import org.junit.Test;

/**
 * Test class for {@link BlockStyle}.
 * 
 * @author Martijn van de Rijdt
 */
@Slf4j
public class BlockStyleTest {
    /** Tests {@link BlockStyle#apply(Rectangle, Tetromino)} for all possible styles and tetrominoes. */
    @Test
    public void testApply() {
        Rectangle rectangle = new Rectangle(10, 10);
        
        for (BlockStyle style: BlockStyle.values()) {
            for (Tetromino tetromino: Tetromino.values()) {
                log.info("Applying style {} for tetromino {}.", style, tetromino);
                style.apply(rectangle, tetromino, 25);
            }
        }
    }
    
    /** Tests {@link BlockStyle#apply(Rectangle, Tetromino)} for a null tetromino value. */
    @Test(expected = NullPointerException.class)
    public void testApplyNullTetromino() {
        Rectangle rectangle = new Rectangle(10, 10);
        BlockStyle.ACTIVE.apply(rectangle, null, 25);
    }
    
    /** Tests {@link BlockStyle#apply(Rectangle, Tetromino)} for a null rectangle value. */
    @Test(expected = NullPointerException.class)
    public void testApplyNullRectangle() {
        BlockStyle.ACTIVE.apply(null, Tetromino.I, 25);
    }
}
