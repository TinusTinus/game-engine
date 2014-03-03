package nl.mvdr.tinustris.gui;

import javafx.scene.shape.Box;
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
    public void testApplyRectangle() {
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
    public void testApplyNullTetrominoRectangle() {
        Rectangle rectangle = new Rectangle(10, 10);
        BlockStyle.ACTIVE.apply(rectangle, null, 25, 0);
    }
    
    /** Tests {@link BlockStyle#apply(Rectangle, Block)} for a null rectangle value. */
    @Test(expected = NullPointerException.class)
    public void testApplyNullRectangleRectangle() {
        BlockStyle.ACTIVE.apply((Rectangle)null, Block.I, 25, 0);
    }
    
    /** Tests {@link BlockStyle#apply(Rectangle, Block)} for all possible styles and tetrominoes. */
    @Test
    public void testApplyBox() {
        Box box = new Box();
        
        for (BlockStyle style: BlockStyle.values()) {
            for (Block block: Block.values()) {
                log.info("Applying style {} for block {}.", style, block);
                style.apply(box, block, 25, 0);
            }
        }
    }
    
    /** Tests {@link BlockStyle#apply(Rectangle, Block)} for a null tetromino value. */
    @Test(expected = NullPointerException.class)
    public void testApplyNullTetrominoBox() {
        Box box = new Box();
        BlockStyle.ACTIVE.apply(box, null, 25, 0);
    }
    
    /** Tests {@link BlockStyle#apply(Rectangle, Block)} for a null rectangle value. */
    @Test(expected = NullPointerException.class)
    public void testApplyNullRectangleBox() {
        BlockStyle.ACTIVE.apply((Box)null, Block.I, 25, 0);
    }
}
