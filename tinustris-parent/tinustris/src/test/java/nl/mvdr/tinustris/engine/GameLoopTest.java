package nl.mvdr.tinustris.engine;

import nl.mvdr.tinustris.gui.DummyRenderer;
import nl.mvdr.tinustris.input.DummyGameEngine;
import nl.mvdr.tinustris.input.DummyInputController;

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
        Thread.sleep(3000);
        gameLoop.stop();
        // sleep a little longer, to give the game loop thread time to clean up and log that it is finished
        Thread.sleep(500);
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
