package nl.mvdr.tinustris.engine;

import nl.mvdr.tinustris.gui.DummyRenderer;
import nl.mvdr.tinustris.input.DummyGameEngine;
import nl.mvdr.tinustris.input.DummyInputController;
import nl.mvdr.tinustris.input.InputState;
import nl.mvdr.tinustris.model.DummyGameState;

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
        GameLoop<DummyGameState> gameLoop = new GameLoop<>(new DummyInputController(), new DummyGameEngine(),
                new DummyRenderer<DummyGameState>());
        
        gameLoop.start();
        Thread.sleep(2000);
        gameLoop.stop();
        // sleep a little longer, to give the game loop thread time to clean up and log that it is finished
        Thread.sleep(50);
    }
    
    /**
     * Starts, pauses, unpauses then stops the game loop.
     * 
     * @throws InterruptedException
     *             unexpected exception
     */
    @Test
    public void testStartPauseUnpauseStop() throws InterruptedException {
        GameLoop<DummyGameState> gameLoop = new GameLoop<>(new DummyInputController(), new DummyGameEngine(),
                new DummyRenderer<DummyGameState>());
        
        gameLoop.start();
        // sleep to give the game loop thread a chance to get started
        Thread.sleep(50);
        gameLoop.pause();
        Thread.sleep(50);
        gameLoop.unpause();
        Thread.sleep(50);
        gameLoop.stop();
        // sleep to give the game loop thread time to clean up and log that it is finished
        Thread.sleep(50);
    }
    
    /**
     * Starts, pauses, unpauses then stops the game loop.
     * 
     * @throws InterruptedException
     *             unexpected exception
     */
    @Test
    public void testStartToggleToggleStop() throws InterruptedException {
        GameLoop<DummyGameState> gameLoop = new GameLoop<>(new DummyInputController(), new DummyGameEngine(),
                new DummyRenderer<DummyGameState>());
        
        gameLoop.start();
        // sleep to give the game loop thread a chance to get started
        Thread.sleep(50);
        gameLoop.togglePaused();
        Thread.sleep(50);
        gameLoop.togglePaused();
        Thread.sleep(50);
        gameLoop.stop();
        // sleep to give the game loop thread time to clean up and log that it is finished
        Thread.sleep(50);
    }
    
    /**
     * Starts, pauses, then stops the game loop.
     * 
     * @throws InterruptedException
     *             unexpected exception
     */
    @Test
    public void testStopGameWhilePaused() throws InterruptedException {
        GameLoop<DummyGameState> gameLoop = new GameLoop<>(new DummyInputController(), new DummyGameEngine(), new DummyRenderer<DummyGameState>());
        
        gameLoop.start();
        // sleep to give the game loop thread a chance to get started
        Thread.sleep(50);
        gameLoop.pause();
        Thread.sleep(50);
        gameLoop.stop();
        // sleep to give the game loop thread time to clean up and log that it is finished
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
            public DummyGameState computeNextState(DummyGameState previousState, InputState inputState) {
                // return game state that is game over
                return new DummyGameState(true);
            }
        };
        
        GameLoop<DummyGameState> gameLoop = new GameLoop<>(new DummyInputController(), engine,
                new DummyRenderer<DummyGameState>());
        
        gameLoop.start();
        // sleep to give the game loop thread time to clean up and log that it is finished
        Thread.sleep(50);
    }
    
    /** Tests the constructor with a null value for the input controller. */
    @Test(expected = NullPointerException.class)
    public void testNullController() {
        new GameLoop<>(null, new DummyGameEngine(), new DummyRenderer<DummyGameState>());
    }
    
    /** Tests the constructor with a null value for the game engine. */
    @Test(expected = NullPointerException.class)
    public void testNullEngine() {
        new GameLoop<>(new DummyInputController(), null, new DummyRenderer<DummyGameState>());
    }

    
    /** Tests the constructor with a null value for the renderer. */
    @Test(expected = NullPointerException.class)
    public void testNullRenderer() {
        new GameLoop<>(new DummyInputController(), new DummyGameEngine(), null);
    }
}
