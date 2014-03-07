package nl.mvdr.tinustris.gui;

import javafx.scene.shape.Rectangle;
import nl.mvdr.tinustris.model.Block;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test class for {@link RectangleBlockCreator}.
 * 
 * @author Martijn van de Rijdt
 */
public class RectangleBlockCreatorTest {
    /** Test method for {@link RectangleBlockCreator#createBlock(double, double, double, nl.mvdr.tinustris.model.Block, BlockStyle, int, int)}. */
    @Test
    public void testCreate() {
        RectangleBlockCreator creator = new RectangleBlockCreator();
        
        Rectangle rectangle = creator.createBlock(1, 2, 3, Block.L, BlockStyle.ACTIVE, 3, 2);
        
        Assert.assertNotNull(rectangle);
    }
}
