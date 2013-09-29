package nl.mvdr.tinustris.gui;

import javafx.scene.control.Label;
import nl.mvdr.tinustris.model.GameState;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test class for {@link LabelRenderer}.
 * 
 * @author Martijn van de Rijdt
 */
public class LabelRendererTest {
    /** Tests {@link LabelRenderer#render(Label, GameState)}. */
    @Test
    public void test() {
        LabelRenderer renderer = createLabelRenderer();
        Label label = new Label();
        GameState state = new GameState();
        
        renderer.render(label, state);
        
        Assert.assertNotNull(label.getText());
    }

    /**
     * Creates a new label renderer.
     * 
     * @return renderer
     */
    private LabelRenderer createLabelRenderer() {
        return new LabelRenderer() {
            /** 
             * Mock implementation which just executes the runnable on the current thread,
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
