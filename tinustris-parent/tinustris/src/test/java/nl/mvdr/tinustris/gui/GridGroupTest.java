package nl.mvdr.tinustris.gui;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.Label;
import lombok.extern.slf4j.Slf4j;
import nl.mvdr.tinustris.model.GameState;
import nl.mvdr.tinustris.model.Orientation;
import nl.mvdr.tinustris.model.Point;
import nl.mvdr.tinustris.model.Tetromino;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test class for {@link GridRenderer}.
 * 
 * @author Martijn van de Rijdt
 */
@Slf4j
public class GridGroupTest {
    /** Tests {@link GridRenderer#render(Label, GameState)}. */
    @Test
    public void testRenderSimpleState() {
        GridRenderer renderer = createGridGroup();
        GameState state = new GameState();
        
        renderer.render(state);
        
        Assert.assertFalse(renderer.getChildren().isEmpty());
    }
    
    /** Tests {@link GridRenderer#render(Label, GameState)} with the same game state twice. */
    @Test
    public void testRenderSimpleStateTwice() {
        GridRenderer renderer = createGridGroup();
        GameState state = new GameState();
        
        renderer.render(state);
        renderer.render(state);
        
        Assert.assertFalse(renderer.getChildren().isEmpty());
    }
    
    /** Tests {@link GridRenderer#render(Label, GameState)}. */
    @Test
    public void testRender() {
        GridRenderer renderer = createGridGroup();
        GameState gameState = createNontrivialGameState();
        
        renderer.render(gameState);
        
        Assert.assertFalse(renderer.getChildren().isEmpty());
    }

    /** Tests {@link GridRenderer#render(Label, GameState)} with the same game state twice. */
    @Test
    public void testRenderTwice() {
        GridRenderer renderer = createGridGroup();
        GameState state = createNontrivialGameState();
        
        renderer.render(state);
        renderer.render(state);
        
        Assert.assertFalse(renderer.getChildren().isEmpty());
    }
    
    /** Tests {@link GridRenderer#render(Label, GameState)} with two different game states. */
    @Test
    public void testRenderTwiceWithDifferentStates() {
        GridRenderer renderer = createGridGroup();
        
        renderer.render(new GameState());
        renderer.render(createNontrivialGameState());
        
        Assert.assertFalse(renderer.getChildren().isEmpty());
    }
    
    /** Tests {@link GridRenderer#render(Label, GameState)} when a null value of GameState is passed in. */
    @Test(expected = NullPointerException.class)
    public void testNullState() {
        GridRenderer renderer = createGridGroup();
        
        renderer.render(null);
    }
    
    /**
     * Creates a new renderer.
     * 
     * @return renderer
     */
    private GridRenderer createGridGroup() {
        return new GridRenderer() {
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
     * Creates a nontrivial game state, containing a block in the grid, an active block and a ghost.
     * 
     * @return game state
     */
    private GameState createNontrivialGameState() {
        List<Tetromino> grid = new ArrayList<>();
        while (grid.size() != 220) {
            grid.add(null);
        }
        // add a single block at (2, 0)
        grid.set(2, Tetromino.S);
        GameState gameState = new GameState(grid, 10, Tetromino.O, new Point(5, 10), Orientation.getDefault(),
                Tetromino.I);
        log.info(gameState.toString());
        return gameState;
    }
}
