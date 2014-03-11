package nl.mvdr.tinustris.gui;

import java.util.Arrays;

import nl.mvdr.tinustris.model.MultiplayerGameState;
import nl.mvdr.tinustris.model.OnePlayerGameState;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test class for {@link MultiplayerGameRenderer}.
 * 
 * @author Martijn van de Rijdt
 */
public class MultiplayerGameRendererTest {
    /** Tests the constructor. */
    @Test
    public void testConstructor() {
        new MultiplayerGameRenderer(new DummyRenderer<OnePlayerGameState>(), 2);
    }
    
    /** Tests the constructor. */
    @Test(expected = NullPointerException.class)
    public void testConstructorNullValue() {
        new MultiplayerGameRenderer(null, 2);
    }
    
    /** Test case for {@link MultiplayerGameRenderer#render(MultiplayerGameState)}. */
    @Test
    public void testRender0() {
        DummyRenderer<OnePlayerGameState> dummyRenderer = new DummyRenderer<>();
        MultiplayerGameRenderer renderer = new MultiplayerGameRenderer(dummyRenderer, 0);
        OnePlayerGameState state0 = new OnePlayerGameState();
        OnePlayerGameState state1 = new OnePlayerGameState();
        MultiplayerGameState gameState = new MultiplayerGameState(Arrays.asList(state0, state1), Arrays.asList(2, 1));
        
        renderer.render(gameState);
        
        Assert.assertSame(state0, dummyRenderer.getLastRenderedState());
    }
    
    /** Test case for {@link MultiplayerGameRenderer#render(MultiplayerGameState)}. */
    @Test
    public void testRender1() {
        DummyRenderer<OnePlayerGameState> dummyRenderer = new DummyRenderer<>();
        MultiplayerGameRenderer renderer = new MultiplayerGameRenderer(dummyRenderer, 1);
        OnePlayerGameState state0 = new OnePlayerGameState();
        OnePlayerGameState state1 = new OnePlayerGameState();
        MultiplayerGameState gameState = new MultiplayerGameState(Arrays.asList(state0, state1), Arrays.asList(2, 1));
        
        renderer.render(gameState);
        
        Assert.assertSame(state1, dummyRenderer.getLastRenderedState());
    }
    
    /** Test case for {@link MultiplayerGameRenderer#render(MultiplayerGameState)}. */
    @Test(expected = NullPointerException.class)
    public void testRenderNull() {
        DummyRenderer<OnePlayerGameState> dummyRenderer = new DummyRenderer<>();
        MultiplayerGameRenderer renderer = new MultiplayerGameRenderer(dummyRenderer, 1);
        
        renderer.render(null);
    }
    
    /** Test case for {@link MultiplayerGameRenderer#render(MultiplayerGameState)}. */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testRenderNegativeIndex() {
        DummyRenderer<OnePlayerGameState> dummyRenderer = new DummyRenderer<>();
        MultiplayerGameRenderer renderer = new MultiplayerGameRenderer(dummyRenderer, -1);
        OnePlayerGameState state0 = new OnePlayerGameState();
        OnePlayerGameState state1 = new OnePlayerGameState();
        MultiplayerGameState gameState = new MultiplayerGameState(Arrays.asList(state0, state1), Arrays.asList(2, 1));
        
        renderer.render(gameState);
    }
    
    /** Test case for {@link MultiplayerGameRenderer#render(MultiplayerGameState)}. */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testRenderTooighIndex() {
        DummyRenderer<OnePlayerGameState> dummyRenderer = new DummyRenderer<>();
        MultiplayerGameRenderer renderer = new MultiplayerGameRenderer(dummyRenderer, 2);
        OnePlayerGameState state0 = new OnePlayerGameState();
        OnePlayerGameState state1 = new OnePlayerGameState();
        MultiplayerGameState gameState = new MultiplayerGameState(Arrays.asList(state0, state1), Arrays.asList(2, 1));
        
        renderer.render(gameState);
    }
}
