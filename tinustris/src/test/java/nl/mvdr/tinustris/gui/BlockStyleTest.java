package nl.mvdr.tinustris.gui;

import javafx.scene.shape.Rectangle;
import lombok.extern.slf4j.Slf4j;
import nl.mvdr.tinustris.model.Block;

import org.junit.Test;

/**
 * Test class for {@link BlockStyle}.
 * 
 * @author Martijn van de Rijdt
 */
@Slf4j
public class BlockStyleTest {
    /** Tests {@link BlockStyle#apply(Rectangle, Block)} for all possible styles and tetrominoes. */
    @Test
    public void testApply() {
        Rectangle rectangle = new Rectangle(10, 10);
        
        for (BlockStyle style: BlockStyle.values()) {
            for (Block block: Block.values()) {
                log.info("Applying style {} for block {}.", style, block);
                style.apply(rectangle, block, 25, 0);
            }
        }
    }
    
    /** Tests {@link BlockStyle#apply(Rectangle, Block)} for a null tetromino value. */
    @Test(expected = NullPointerException.class)
    public void testApplyNullTetromino() {
        Rectangle rectangle = new Rectangle(10, 10);
        BlockStyle.ACTIVE.apply(rectangle, null, 25, 0);
    }
    
    /** Tests {@link BlockStyle#apply(Rectangle, Block)} for a null rectangle value. */
    @Test(expected = NullPointerException.class)
    public void testApplyNullRectangle() {
        BlockStyle.ACTIVE.apply(null, Block.I, 25, 0);
    }
}
