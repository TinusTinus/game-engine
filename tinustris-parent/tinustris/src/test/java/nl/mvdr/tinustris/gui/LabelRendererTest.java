package nl.mvdr.tinustris.gui;

import javafx.scene.control.Label;
import lombok.extern.slf4j.Slf4j;
import nl.mvdr.tinustris.model.GameState;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test class for {@link LabelRenderer}.
 * 
 * @author Martijn van de Rijdt
 */
@Slf4j
public class LabelRendererTest {
    /** Tests {@link LabelRenderer#render(Label, GameState)}. */
    @Test
    public void testRender() {
        LabelRenderer renderer = createLabelRenderer();
        Label label = new Label();
        GameState state = new GameState();
        
        renderer.render(label, state);
        
        String text = label.getText();
        log.info(text);
        Assert.assertNotNull(text);
        Assert.assertNotEquals("", text);
    }
    
    /** Tests {@link LabelRenderer#render(Label, GameState)} when a null value of GameState is passed in. */
    @Test(expected = NullPointerException.class)
    public void testNullState() {
        LabelRenderer renderer = createLabelRenderer();
        
        renderer.render(new Label(), null);
    }
    
    /** Tests {@link LabelRenderer#render(Label, GameState)} when a null value of Label is passed in. */
    @Test(expected = NullPointerException.class)
    public void testNullLabel() {
        LabelRenderer renderer = createLabelRenderer();
        
        renderer.render(null, new GameState());
    }

    /**
     * Creates a new label renderer.
     * 
     * @return renderer
     */
    private LabelRenderer createLabelRenderer() {
        return new LabelRenderer() {
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
