package nl.mvdr.tinustris.gui;

import javafx.embed.swing.JFXPanel;
import javafx.scene.shape.Rectangle;
import nl.mvdr.tinustris.model.OnePlayerGameState;

import org.junit.Before;
import org.junit.Test;

/**
 * Test class for {@link OnePlayerGameRenderer}.
 * 
 * @author Martijn van de Rijdt
 */
public class OnePlayerGameRendererTest {
    /** Setup method. */
    @Before
    public void setUp() {
        // Force JavaFX graphics initialisation; this is required by the Image constructor.
        // When running the actual application this is performed by Application.launch().
        new JFXPanel();
    }
    
    /**
     * Test case for {@link OnePlayerGameRenderer#OnePlayerGameRenderer(String, int, int, BlockCreator)} in case the
     * name parameter is null.
     */
    @Test(expected = NullPointerException.class)
    public void testNullName() {
        new OnePlayerGameRenderer(null, 1, 1, (x, y, size, block, style, framesLines, framesLock) -> new Rectangle());
    }
    
    /**
     * Test case for {@link OnePlayerGameRenderer#OnePlayerGameRenderer(String, int, int, BlockCreator)} in case the
     * block creator parameter is null.
     */
    @Test(expected = NullPointerException.class)
    public void testNullBlockCreator() {
        new OnePlayerGameRenderer("name", 1, 1, null);
    }
    
    /** Test case for {@link OnePlayerGameRenderer#OnePlayerGameRenderer(String, int, int, BlockCreator)}. */
    @Test
    public void testRender() {
        OnePlayerGameRenderer renderer = new OnePlayerGameRenderer("name", 1, 1,
                (x, y, size, block, style, framesLines, framesLock) -> new Rectangle());
        
        renderer.render(new OnePlayerGameState());
    }
}
