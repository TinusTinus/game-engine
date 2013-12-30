package nl.mvdr.tinustris.gui;

import nl.mvdr.tinustris.input.InputStateHistory;
import nl.mvdr.tinustris.model.GameState;
import nl.mvdr.tinustris.model.Tetromino;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test class for {@link LinesRenderer}.
 * 
 * @author Martijn van de Rijdt
 */
public class LinesRendererTest {
    /** Tests {@link LinesRenderer#render(GameState)}. */
    @Test
    public void testRender() {
        LinesRenderer renderer = createLinesRenderer();
        GameState state = new GameState();
        
        renderer.render(state);
        
        Assert.assertEquals("0", renderer.getText());
    }
    
    /** Tests {@link LinesRenderer#render(GameState)}. */
    @Test
    public void testRenderPositiveLines() {
        LinesRenderer renderer = createLinesRenderer();
        GameState state = new GameState(new GameState().getGrid(), 4, null, null, null, Tetromino.L, 0, 0, 0,
                new InputStateHistory(), 0, 365);
        
        renderer.render(state);
        
        Assert.assertEquals("365", renderer.getText());
    }
    
    /** Tests {@link LinesRenderer#render(GameState)} when a null value of GameState is passed in. */
    @Test(expected = NullPointerException.class)
    public void testNullState() {
        LinesRenderer renderer = createLinesRenderer();
        
        renderer.render(null);
    }
    
    /**
     * Creates a new renderer.
     * 
     * @return renderer
     */
    private LinesRenderer createLinesRenderer() {
        return new LinesRenderer() {
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
