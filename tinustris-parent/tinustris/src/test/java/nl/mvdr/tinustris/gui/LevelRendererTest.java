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
public class LevelRendererTest {
    /** Tests {@link LinesRenderer#render(GameState)}. */
    @Test
    public void testRender() {
        LevelRenderer renderer = createLevelRenderer();
        GameState state = new GameState();
        
        renderer.render(state);
        
        Assert.assertEquals("0", renderer.getText());
    }
    
    /** Tests {@link LinesRenderer#render(GameState)}. */
    @Test
    public void testRenderPositiveLevel() {
        LevelRenderer renderer = createLevelRenderer();
        GameState state = new GameState(new GameState().getGrid(), 4, null, null, null, Tetromino.L, 0, 0, 0,
                new InputStateHistory(), 0, 365);
        
        renderer.render(state);
        
        Assert.assertEquals("36", renderer.getText());
    }
    
    /** Tests {@link LinesRenderer#render(GameState)} when a null value of GameState is passed in. */
    @Test(expected = NullPointerException.class)
    public void testNullState() {
        LevelRenderer renderer = createLevelRenderer();
        
        renderer.render(null);
    }
    
    /**
     * Creates a new renderer.
     * 
     * @return renderer
     */
    private LevelRenderer createLevelRenderer() {
        return new LevelRenderer() {
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
