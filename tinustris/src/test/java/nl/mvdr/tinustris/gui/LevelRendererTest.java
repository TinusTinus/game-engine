package nl.mvdr.tinustris.gui;

import java.util.Optional;

import javafx.embed.swing.JFXPanel;
import nl.mvdr.tinustris.input.InputStateHistory;
import nl.mvdr.tinustris.model.OnePlayerGameState;
import nl.mvdr.tinustris.model.Tetromino;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class for {@link LinesRenderer}.
 * 
 * @author Martijn van de Rijdt
 */
public class LevelRendererTest {
    /** Setup method. */
    @Before
    public void setUp() {
        // Force JavaFX graphics initialisation; this is required by the Image constructor.
        // When running the actual application this is performed by Application.launch().
        new JFXPanel();
    }
    
    /** Tests {@link LinesRenderer#render(OnePlayerGameState)}. */
    @Test
    public void testRender() {
        LevelRenderer renderer = createLevelRenderer();
        OnePlayerGameState state = new OnePlayerGameState();
        
        renderer.render(state);
        
        Assert.assertEquals("0", renderer.getText());
    }
    
    /** Tests {@link LinesRenderer#render(OnePlayerGameState)}. */
    @Test
    public void testRenderPositiveLevel() {
        LevelRenderer renderer = createLevelRenderer();
        OnePlayerGameState state = new OnePlayerGameState(new OnePlayerGameState().getGrid(), 4, Optional.empty(),
                Optional.empty(), Optional.empty(), Tetromino.L, 0, 0, 0, InputStateHistory.NEW, 0, 365, 0, 36, 0, 0);
        
        renderer.render(state);
        
        Assert.assertEquals("36", renderer.getText());
    }
    
    /** Tests {@link LinesRenderer#render(OnePlayerGameState)} when a null value of GameState is passed in. */
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
