package nl.mvdr.tinustris.gui;

import javafx.embed.swing.JFXPanel;
import lombok.extern.slf4j.Slf4j;
import nl.mvdr.tinustris.model.DummyGameState;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class for {@link LabelRenderer}.
 * 
 * @author Martijn van de Rijdt
 */
@Slf4j
public class LabelRendererTest {
    /** Setup method. */
    @Before
    public void setUp() {
        // Force JavaFX graphics initialisation; this is required by the Image constructor.
        // When running the actual application this is performed by Application.launch().
        new JFXPanel();
    }
    
    /** Tests {@link LabelRenderer#render(nl.mvdr.tinustris.model.GameState)}. */
    @Test
    public void testRender() {
        LabelRenderer<DummyGameState> renderer = createLabelRenderer();
        
        renderer.render(DummyGameState.GAME_NOT_OVER);
        
        String text = renderer.getText();
        log.info(text);
        Assert.assertNotNull(text);
        Assert.assertNotEquals("", text);
    }
    
    /** Tests {@link LabelRenderer#render(nl.mvdr.tinustris.model.GameState)} in case the text stays the same. */
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
    
    /** Tests {@link LabelRenderer#render(nl.mvdr.tinustris.model.GameState)} when a null value of GameState is passed in. */
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
