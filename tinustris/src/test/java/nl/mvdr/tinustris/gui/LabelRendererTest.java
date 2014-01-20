package nl.mvdr.tinustris.gui;

import lombok.extern.slf4j.Slf4j;
import nl.mvdr.tinustris.model.DummyGameState;
import nl.mvdr.tinustris.model.OnePlayerGameState;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test class for {@link LabelRenderer}.
 * 
 * @author Martijn van de Rijdt
 */
@Slf4j
public class LabelRendererTest {
    /** Tests {@link LabelRenderer#render(OnePlayerGameState)}. */
    @Test
    public void testRender() {
        LabelRenderer<DummyGameState> renderer = createLabelRenderer();
        
        renderer.render(DummyGameState.GAME_NOT_OVER);
        
        String text = renderer.getText();
        log.info(text);
        Assert.assertNotNull(text);
        Assert.assertNotEquals("", text);
    }
    
    /** Tests {@link LabelRenderer#render(OnePlayerGameState)} in case the text stays the same. */
    @Test
    public void testRenderSameValue() {
        LabelRenderer<DummyGameState> renderer = createLabelRenderer();
        DummyGameState state = DummyGameState.GAME_NOT_OVER;
        renderer.setText(state.toString());
        
        renderer.render(state);
        
        String text = renderer.getText();
        log.info(text);
        Assert.assertNotNull(text);
        Assert.assertNotEquals("", text);
    }
    
    /** Tests {@link LabelRenderer#render(OnePlayerGameState)} when a null value of GameState is passed in. */
    @Test(expected = NullPointerException.class)
    public void testNullState() {
        LabelRenderer<DummyGameState> renderer = createLabelRenderer();
        
        renderer.render(null);
    }
    
    /**
     * Creates a new label renderer.
     * 
     * @return renderer
     */
    private LabelRenderer<DummyGameState> createLabelRenderer() {
        return new LabelRenderer<DummyGameState>() {
            /** 
             * Mock implementation which just executes the runnable on the current thread.
             * 
             * @param runnable runnable to be executed
             */
            @Override
            protected void runOnJavaFXThread(Runnable runnable) {
                log.info("Executing runnable: " + runnable);
                runnable.run();
            }

            /**
             * Mock implementation which returns the state's toString.
             * 
             * @return string representation of the state
             */
            @Override
            protected String toText(DummyGameState state) {
                return state.toString();
            }
        };
    }
}
