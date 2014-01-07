package nl.mvdr.tinustris.gui;

import nl.mvdr.tinustris.model.GameState;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test class for {@link CompositeRenderer}.
 * 
 * @author Martijn van de Rijdt
 */
public class CompositeRendererTest {
    /** Tests {@link CompositeRenderer#render(GameState)}. */
    @Test
    public void testRenderEmptyList() {
        CompositeRenderer renderer = new CompositeRenderer();
        GameState state = new GameState();
        
        renderer.render(state);
    }
    
    /** Tests {@link CompositeRenderer#render(GameState)}. */
    @Test
    public void testRenderOneRenderer() {
        DummyRenderer dummyRenderer = new DummyRenderer();
        CompositeRenderer renderer = new CompositeRenderer(dummyRenderer);
        GameState state = new GameState();
        
        renderer.render(state);
        
        Assert.assertEquals(state, dummyRenderer.getLastRenderedState());
    }
    
    /** Tests {@link CompositeRenderer#render(GameState)}. */
    @Test
    public void testRenderTwoRenderers() {
        DummyRenderer dummyRenderer0 = new DummyRenderer();
        DummyRenderer dummyRenderer1 = new DummyRenderer();
        CompositeRenderer renderer = new CompositeRenderer(dummyRenderer0, dummyRenderer1);
        GameState state = new GameState();
        
        renderer.render(state);
        
        Assert.assertEquals(state, dummyRenderer0.getLastRenderedState());
        Assert.assertEquals(state, dummyRenderer1.getLastRenderedState());
    }
    
    /** Tests {@link LabelRenderer#render(GameState)} when a null value of GameState is passed in. */
    @Test(expected = NullPointerException.class)
    public void testNullState() {
        CompositeRenderer renderer = new CompositeRenderer();
        
        renderer.render(null);
    }
}
