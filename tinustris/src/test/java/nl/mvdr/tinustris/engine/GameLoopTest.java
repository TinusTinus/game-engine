package nl.mvdr.tinustris.engine;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.IntStream;

import nl.mvdr.tinustris.gui.DummyRenderer;
import nl.mvdr.tinustris.input.DummyInputController;
import nl.mvdr.tinustris.input.InputState;
import nl.mvdr.tinustris.model.DummyGameState;
import nl.mvdr.tinustris.model.FrameAndInputStatesContainer;
import nl.mvdr.tinustris.model.SingleGameStateHolder;
import nl.mvdr.tinustris.netcode.DummyInputPublisher;

import org.junit.Assert;
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
        GameLoop<DummyGameState> gameLoop = new GameLoop<>(
                Collections.singletonList(new DummyInputController()), new DummyGameEngine(), new DummyRenderer<>(),
                new SingleGameStateHolder<>(), Collections.<Consumer<FrameAndInputStatesContainer>>emptyList());
        
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
        GameLoop<DummyGameState> gameLoop = new GameLoop<>(
                Collections.singletonList(new DummyInputController()), new DummyGameEngine(), new DummyRenderer<>(),
                new SingleGameStateHolder<>(), Collections.<Consumer<FrameAndInputStatesContainer>>emptyList());
        
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
        GameLoop<DummyGameState> gameLoop = new GameLoop<>(
                Collections.singletonList(new DummyInputController()), new DummyGameEngine(), new DummyRenderer<>(),
                new SingleGameStateHolder<>(), Collections.<Consumer<FrameAndInputStatesContainer>>emptyList());
        
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
        GameLoop<DummyGameState> gameLoop = new GameLoop<>(
                Collections.singletonList(new DummyInputController()), new DummyGameEngine(), new DummyRenderer<>(),
                new SingleGameStateHolder<>(), Collections.<Consumer<FrameAndInputStatesContainer>>emptyList());
        
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
            public DummyGameState computeNextState(DummyGameState previousState, List<InputState> inputStates) {
                // return game state that is game over
                return DummyGameState.GAME_OVER;
            }
        };

        GameLoop<DummyGameState> gameLoop = new GameLoop<>(
                Collections.singletonList(new DummyInputController()), engine, new DummyRenderer<DummyGameState>(),
                new SingleGameStateHolder<>(), Collections.<Consumer<FrameAndInputStatesContainer>> emptyList());
        
        gameLoop.start();
        // sleep to give the game loop thread time to clean up and log that it is finished
        Thread.sleep(50);
    }

    /**
     * Starts a game loop with a remote input controller and a local one. Only the local inputs should be published.
     * 
     * @throws InterruptedException
     *             unexpected exception
     */
    @Test
    public void testPublishOnlyLocalInput() throws InterruptedException {
        DummyInputPublisher publisher = new DummyInputPublisher();
        GameLoop<DummyGameState> gameLoop = new GameLoop<>(
                Arrays.asList(new DummyInputController(false), new DummyInputController(true)), new DummyGameEngine(),
                new DummyRenderer<>(), new SingleGameStateHolder<>(), Collections.singletonList(publisher));
        
        gameLoop.start();
        Thread.sleep(2000);
        gameLoop.stop();
        // sleep a little longer, to give the game loop thread time to clean up and log that it is finished
        Thread.sleep(50);
        
        IntStream.range(0, publisher.getPublishedStates().size())
            .forEach(i -> Assert.assertEquals("Unexpected frame index", i, publisher.getPublishedStates().get(i).getFrame()));
        publisher.getPublishedStates().stream()
            .flatMap(container -> container.getInputStates().keySet().stream())
            .mapToInt(Integer::valueOf)
            .forEach(i -> Assert.assertEquals("Expected only states for player 1.", 1, i));
    }

    
    /** Tests the constructor with a null value for the input controller. */
    @Test(expected = NullPointerException.class)
    public void testNullController() {
        new GameLoop<>(null, new DummyGameEngine(), new DummyRenderer<>(), new SingleGameStateHolder<>(),
                Collections.<Consumer<FrameAndInputStatesContainer>>emptyList());
    }

    /** Tests the constructor with a null value for the game engine. */
    @Test(expected = NullPointerException.class)
    public void testNullEngine() {
        new GameLoop<DummyGameState>(Collections.singletonList(new DummyInputController()), null,
                new DummyRenderer<>(), new SingleGameStateHolder<>(),
                Collections.<Consumer<FrameAndInputStatesContainer>> emptyList());
    }

    /** Tests the constructor with a null value for the renderer. */
    @Test(expected = NullPointerException.class)
    public void testNullRenderer() {
        new GameLoop<>(Collections.singletonList(new DummyInputController()), new DummyGameEngine(),
                null, new SingleGameStateHolder<>(), Collections.<Consumer<FrameAndInputStatesContainer>> emptyList());
    }

    /** Tests the constructor with a null value for the holder. */
    @Test(expected = NullPointerException.class)
    public void testNullHolder() {
        new GameLoop<>(Collections.singletonList(new DummyInputController()), new DummyGameEngine(),
                new DummyRenderer<>(), null, Collections.<Consumer<FrameAndInputStatesContainer>>emptyList());
    }
    
    /** Tests the constructor with a null value for the publisher. */
    @Test(expected = NullPointerException.class)
    public void testNullPublisher() {
        new GameLoop<>(Collections.singletonList(new DummyInputController()), new DummyGameEngine(),
                new DummyRenderer<>(), new SingleGameStateHolder<>(), null);
    }
}
