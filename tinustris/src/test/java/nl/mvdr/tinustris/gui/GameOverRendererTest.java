package nl.mvdr.tinustris.gui;

import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import lombok.extern.slf4j.Slf4j;
import nl.mvdr.tinustris.model.OnePlayerGameState;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class for {@link GameOverRenderer}.
 * 
 * @author Martijn van de Rijdt
 */
@Slf4j
public class GameOverRendererTest {
    /** Setup method. */
    @Before
    public void setUp() {
        // Force JavaFX graphics initialisation; this is required by the Image constructor.
        // When running the actual application this is performed by Application.launch().
        new JFXPanel();
    }
    
    /** Test case where the game is not over. */
    @Test
    public void testNotTopped() {
        OnePlayerGameState state = new OnePlayerGameState();
        GameOverRenderer renderer = createRenderer();
        Group group = new Group(renderer);
        group.setVisible(false);
        
        renderer.render(state);
        
        Assert.assertFalse(group.isVisible());
    }
    
    /** Test case where the parent is already visible. */
    @Test
    public void testNotToppedVisible() {
        OnePlayerGameState state = new OnePlayerGameState();
        GameOverRenderer renderer = createRenderer();
        Group group = new Group(renderer);
        group.setVisible(true);
        
        renderer.render(state);
        
        Assert.assertFalse(group.isVisible());
    }
    
    /** Test case where the game is over. */
    @Test
    public void testTopped() {
        OnePlayerGameState state = createToppedGameState();
        GameOverRenderer renderer = createRenderer();
        Group group = new Group(renderer);
        group.setVisible(false);
        
        renderer.render(state);
        
        Assert.assertTrue(group.isVisible());
    }
    
    /** Test case where the parent is already visible. */
    @Test
    public void testToppedVisible() {
        OnePlayerGameState state = createToppedGameState();
        GameOverRenderer renderer = createRenderer();
        Group group = new Group(renderer);
        group.setVisible(true);
        
        renderer.render(state);
        
        Assert.assertTrue(group.isVisible());
    }

    /**
     * Creates a new renderer.
     * 
     * @return renderer
     */
    private GameOverRenderer createRenderer() {
        return new GameOverRenderer() {
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
        };
    }
    
    /**
     * Creates a topped game state.
     * 
     * @return game state
     */
    private OnePlayerGameState createToppedGameState() {
        return new OnePlayerGameState() {
            /** 
             * {@inheritDoc}
             * 
             * Dummy implementation which always returns true.
             */
            @Override
            public boolean isTopped() {
                return true;
            }
        };
    }
}
