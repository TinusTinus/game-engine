package nl.mvdr.game.gui;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import nl.mvdr.game.state.DummyGameState;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test class for {@link CompositeRenderer}.
 * 
 * @author Martijn van de Rijdt
 */
public class CompositeRendererTest {
    /** Tests {@link CompositeRenderer#render(nl.mvdr.game.state.GameState)}. */
    @Test
    public void testRenderEmptyList() {
        CompositeRenderer<DummyGameState> renderer = new CompositeRenderer<>(Collections.emptyList());
        
        renderer.render(DummyGameState.GAME_NOT_OVER);
    }
    
    /** Tests {@link CompositeRenderer#render(nl.mvdr.game.state.GameState)}. */
    @Test
    public void testRenderOneRenderer() {
        DummyRenderer<DummyGameState> dummyRenderer = new DummyRenderer<>();
        List<GameRenderer<DummyGameState>> renderers = Collections.singletonList(dummyRenderer);
        CompositeRenderer<DummyGameState> renderer = new CompositeRenderer<>(renderers);
        
        renderer.render(DummyGameState.GAME_NOT_OVER);
        
        Assert.assertSame(DummyGameState.GAME_NOT_OVER, dummyRenderer.getLastRenderedState());
    }
    
    /** Tests {@link CompositeRenderer#render(nl.mvdr.game.state.GameState)}. */
    @Test
    public void testRenderTwoRenderers() {
        DummyRenderer<DummyGameState> dummyRenderer0 = new DummyRenderer<>();
        DummyRenderer<DummyGameState> dummyRenderer1 = new DummyRenderer<>();
        List<GameRenderer<DummyGameState>> renderers = Arrays.asList(dummyRenderer0, dummyRenderer1);
        CompositeRenderer<DummyGameState> renderer = new CompositeRenderer<>(renderers);
        
        renderer.render(DummyGameState.GAME_NOT_OVER);
        
        Assert.assertSame(DummyGameState.GAME_NOT_OVER, dummyRenderer0.getLastRenderedState());
        Assert.assertSame(DummyGameState.GAME_NOT_OVER, dummyRenderer1.getLastRenderedState());
    }
    
    /** Tests {@link LabelRenderer#render(nl.mvdr.game.state.GameState)} when a null value of GameState is passed in. */
    @Test(expected = NullPointerException.class)
    public void testNullState() {
        CompositeRenderer<DummyGameState> renderer = new CompositeRenderer<>(Collections.emptyList());
        
        renderer.render(null);
    }
}
