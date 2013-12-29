package nl.mvdr.tinustris.gui;

import javafx.scene.control.Label;
import nl.mvdr.tinustris.model.GameState;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test class for {@link GridGroup}.
 * 
 * @author Martijn van de Rijdt
 */
public class GridGroupTest {
    /** Tests {@link GridGroup#render(Label, GameState)}. */
    @Test
    public void testRender() {
        GridGroup renderer = createGridGroup();
        GameState state = new GameState();
        
        renderer.render(state);
        
        Assert.assertFalse(renderer.getChildren().isEmpty());
    }
    
    /** Tests {@link GridGroup#render(Label, GameState)} when a null value of GameState is passed in. */
    @Test(expected = NullPointerException.class)
    public void testNullState() {
        GridGroup renderer = createGridGroup();
        
        renderer.render(null);
    }
    
    /**
     * Creates a new renderer.
     * 
     * @return renderer
     */
    private GridGroup createGridGroup() {
        return new GridGroup() {
            /** 
             * Mock implementation which just executes the runnable on the current thread.
             * 
             * @param runnable runnable to be executed
             */
            @Override
            protected void runOnJavaFXThread(Runnable runnable) {
                runnable.run();
            }
        };
    }
}
