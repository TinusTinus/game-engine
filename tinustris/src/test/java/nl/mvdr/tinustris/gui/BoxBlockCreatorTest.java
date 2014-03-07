package nl.mvdr.tinustris.gui;

import javafx.scene.shape.Box;
import nl.mvdr.tinustris.model.Block;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test class for {@link BoxBlockCreator}.
 * 
 * @author Martijn van de Rijdt
 */
public class BoxBlockCreatorTest {
    /** Test method for {@link BoxBlockCreator#createBlock(double, double, double, nl.mvdr.tinustris.model.Block, BlockStyle, int, int)}. */
    @Test
    public void testCreate() {
        BoxBlockCreator creator = new BoxBlockCreator();
        
        Box box = creator.createBlock(1, 2, 3, Block.L, BlockStyle.ACTIVE, 3, 2);
        
        Assert.assertNotNull(box);
    }
}
