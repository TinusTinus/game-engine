package nl.mvdr.tinustris.engine;

import java.util.ArrayList;
import java.util.List;

import nl.mvdr.tinustris.gui.DummyRenderer;
import nl.mvdr.tinustris.input.DummyGameEngine;
import nl.mvdr.tinustris.input.DummyInputController;
import nl.mvdr.tinustris.input.InputState;
import nl.mvdr.tinustris.model.GameState;
import nl.mvdr.tinustris.model.Tetromino;

import org.junit.Test;

/**
 * Test class for {@link GameLoop}.
 * 
 * @author Martijn van de Rijdt
 */
public class GameLoopTest {
    /**
     * Starts the game loop, lets it run for a few seconds, then stops it again.
     * 
     * @throws InterruptedException
     *             unexpected exception
     */
    @Test
    public void testStartAndStop() throws InterruptedException {
        GameLoop gameLoop = new GameLoop(new DummyInputController(), new DummyGameEngine(), new DummyRenderer());
        
        gameLoop.start();
        Thread.sleep(2000);
        gameLoop.stop();
        // sleep a little longer, to give the game loop thread time to clean up and log that it is finished
        Thread.sleep(50);
    }
    
    /**
     * Starts the game loop with a game engine that immediately triggers a game over.
     * 
     * @throws InterruptedException
     *             unexpected exception
     */
    @Test
    public void testToppedState() throws InterruptedException {
        DummyGameEngine engine = new DummyGameEngine() {
            /** {@inheritDoc} */
            @Override
            public GameState computeNextState(GameState previousState, InputState inputState) {
                // return game state with a full grid
                List<Tetromino> grid = new ArrayList<>(220);
                for (int i = 0; i != 220; i++) {
                    grid.add(Tetromino.S);
                }
                return new GameState(grid, 10, null, Tetromino.T);
            }
        };
        
        GameLoop gameLoop = new GameLoop(new DummyInputController(), engine, new DummyRenderer());
        
        gameLoop.start();
        // sleep a little longer, to give the game loop thread time to clean up and log that it is finished
        Thread.sleep(50);
    }
    
    /** Tests the constructor with a null value for the input controller. */
    @Test(expected = NullPointerException.class)
    public void testNullController() {
        new GameLoop(null, new DummyGameEngine(), new DummyRenderer());
    }
    
    /** Tests the constructor with a null value for the game engine. */
    @Test(expected = NullPointerException.class)
    public void testNullEngine() {
        new GameLoop(new DummyInputController(), null, new DummyRenderer());
    }

    
    /** Tests the constructor with a null value for the renderer. */
    @Test(expected = NullPointerException.class)
    public void testNullRenderer() {
        new GameLoop(new DummyInputController(), new DummyGameEngine(), null);
    }
}
