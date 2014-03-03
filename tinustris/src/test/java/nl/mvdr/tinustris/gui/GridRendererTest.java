package nl.mvdr.tinustris.gui;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.Label;
import javafx.scene.shape.Rectangle;
import lombok.extern.slf4j.Slf4j;
import nl.mvdr.tinustris.model.Block;
import nl.mvdr.tinustris.model.OnePlayerGameState;
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
public class GridRendererTest {
    /** Tests {@link GridRenderer#render(Label, OnePlayerGameState)}. */
    @Test
    public void testRenderSimpleState() {
        GridRenderer renderer = createGridGroup();
        OnePlayerGameState state = new OnePlayerGameState();
        
        renderer.render(state);
        
        Assert.assertFalse(renderer.getChildren().isEmpty());
    }
    
    /** Tests {@link GridRenderer#render(Label, OnePlayerGameState)} with the same game state twice. */
    @Test
    public void testRenderSimpleStateTwice() {
        GridRenderer renderer = createGridGroup();
        OnePlayerGameState state = new OnePlayerGameState();
        
        renderer.render(state);
        renderer.render(state);
        
        Assert.assertFalse(renderer.getChildren().isEmpty());
    }
    
    /** Tests {@link GridRenderer#render(Label, OnePlayerGameState)}. */
    @Test
    public void testRender() {
        GridRenderer renderer = createGridGroup();
        OnePlayerGameState gameState = createNontrivialGameState();
        log.info(gameState.toString());
        
        renderer.render(gameState);
        
        Assert.assertFalse(renderer.getChildren().isEmpty());
    }

    /** Tests {@link GridRenderer#render(Label, OnePlayerGameState)} with the same game state twice. */
    @Test
    public void testRenderTwice() {
        GridRenderer renderer = createGridGroup();
        OnePlayerGameState state = createNontrivialGameState();
        log.info(state.toString());
        
        renderer.render(state);
        renderer.render(state);
        
        Assert.assertFalse(renderer.getChildren().isEmpty());
    }
    
    /** Tests {@link GridRenderer#render(Label, OnePlayerGameState)} with two different game states. */
    @Test
    public void testRenderTwiceWithDifferentStates() {
        GridRenderer renderer = createGridGroup();
        
        renderer.render(new OnePlayerGameState());
        renderer.render(createNontrivialGameState());
        
        Assert.assertFalse(renderer.getChildren().isEmpty());
    }
    
    /** Tests {@link GridRenderer#render(Label, OnePlayerGameState)} with a full line. */
    @Test
    public void testRenderWithFullLine() {
        GridRenderer renderer = createGridGroup();
        
        renderer.render(createGameStateWithFullLine());
        
        Assert.assertFalse(renderer.getChildren().isEmpty());
    }
    
    /** Tests {@link GridRenderer#render(Label, OnePlayerGameState)} when a null value of GameState is passed in. */
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
        return new GridRenderer((x, y, size, block, style, framesUnitl, framesSince) -> new Rectangle()) {
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
    private OnePlayerGameState createNontrivialGameState() {
        List<Block> grid = new ArrayList<>();
        while (grid.size() != 220) {
            grid.add(null);
        }
        // add a single block at (2, 0)
        grid.set(2, Block.S);
        OnePlayerGameState gameState = new OnePlayerGameState(grid, 10, Tetromino.O, new Point(5, 10), Orientation.getDefault(),
                Tetromino.I);
        return gameState;
    }
    
    /**
     * Creates a game state, containing a full line in the grid.
     * 
     * @return game state
     */
    private OnePlayerGameState createGameStateWithFullLine() {
        List<Block> grid = new ArrayList<>(220);
        for (int i = 0; i != 10; i++) {
            grid.add(Block.S);
        }
        while (grid.size() != 220) {
            grid.add(null);
        }
        OnePlayerGameState gameState = new OnePlayerGameState(grid, 10, Tetromino.O, new Point(5, 10), Orientation.getDefault(),
                Tetromino.I);
        return gameState;
    }
}
