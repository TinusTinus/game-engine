package nl.mvdr.tinustris.gui;

import nl.mvdr.tinustris.model.GameState;

import org.junit.Test;

/**
 * Test class for {@link NextBlockRenderer}.
 * 
 * @author Martijn van de Rijdt
 */
public class NextBlockRendererTest {
    /** Tests {@link NextBlockRenderer#render(GameState)}. */
    @Test
    public void testRender() {
        NextBlockRenderer renderer = createNextBlockRenderer();
        GameState state = new GameState();
        
        renderer.render(state);
    }
    
    /** Tests {@link NextBlockRenderer#render(GameState)}. */
    @Test
    public void testRenderTwice() {
        NextBlockRenderer renderer = createNextBlockRenderer();
        GameState state = new GameState();
        
        renderer.render(state);
        renderer.render(state);
    }
    
    /** Tests {@link NextBlockRenderer#render(GameState)} when a null value of GameState is passed in. */
    @Test(expected = NullPointerException.class)
    public void testNullState() {
        NextBlockRenderer renderer = createNextBlockRenderer();
        
        renderer.render(null);
    }
    
    /**
     * Creates a new renderer.
     * 
     * @return renderer
     */
    private NextBlockRenderer createNextBlockRenderer() {
        return new NextBlockRenderer() {
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
