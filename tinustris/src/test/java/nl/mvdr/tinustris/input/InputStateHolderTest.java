package nl.mvdr.tinustris.input;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import nl.mvdr.tinustris.model.FrameAndInputStatesContainer;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test case for {@link InputStateHolder}.
 * 
 * @author Martijn van de Rijdt
 */
public class InputStateHolderTest {
    /** Test the {@link InputStateHolder#getInputState()} method. */
    @Test
    public void testGetInputStateDefaultValue() {
        InputStateHolder holder = new InputStateHolder(true, 1);
        
        InputState state = holder.getInputState();
        
        Stream.of(Input.values())
            .forEach(input -> Assert.assertFalse(state.isPressed(input)));
    }
    
    /** Test the {@link InputStateHolder#getInputState(int)} method. */
    @Test
    public void testGetInputStateIndexedDefaultValue() {
        InputStateHolder holder = new InputStateHolder(true, 1);
        
        InputState state = holder.getInputState(123);
        
        Stream.of(Input.values())
            .forEach(input -> Assert.assertFalse(state.isPressed(input)));
    }

    /** Test the {@link InputStateHolder#getInputState()} method. */
    @Test
    public void testGetInputState() {
        InputStateHolder holder = new InputStateHolder(true, 1);
        InputState state = input -> true;
        holder.putState(0, state);
        
        Assert.assertSame(state, holder.getInputState());
    }
    
    /** Test the {@link InputStateHolder#getInputState(int)} method. */
    @Test
    public void testGetInputStateIndexed() {
        InputStateHolder holder = new InputStateHolder(true, 1);
        InputState state = input -> true;
        holder.putState(0, state);
        
        Assert.assertSame(state, holder.getInputState(0));
    }
    
    /** Test the {@link InputStateHolder#getInputState(int)} method. */
    @Test
    public void testGetInputStateIndexedGreater() {
        InputStateHolder holder = new InputStateHolder(true, 1);
        InputState state = input -> true;
        holder.putState(0, state);
        
        Assert.assertSame(state, holder.getInputState(1));
    }
    
    /** Test the {@link InputStateHolder#getInputState(int)} method. */
    @Test
    public void testGetInputStateIndexedLess() {
        InputStateHolder holder = new InputStateHolder(true, 1);
        InputState state = input -> true;
        holder.putState(1, state);
        
        Assert.assertNotEquals(state, holder.getInputState(0));
    }
    
    /** Test the {@link InputStateHolder#accept(FrameAndInputStatesContainer)} method. */
    @Test
    public void testInputForOtherPlayer() {
        InputStateHolder holder = new InputStateHolder(true, 1);
        InputState state = i -> true;
        @SuppressWarnings("serial")
        Map<Integer, InputState> inputStates = new HashMap<Integer, InputState>() {{
            put(3, state);
        }};
        
        holder.accept(new FrameAndInputStatesContainer(0, inputStates));
        
        Assert.assertNotEquals(state, holder.getInputState());
    }
    
    /** Test the {@link InputStateHolder#accept(FrameAndInputStatesContainer)} method. */
    @Test
    public void testInputForOwnPlayer() {
        InputStateHolder holder = new InputStateHolder(true, 1);
        InputState state = i -> true;
        @SuppressWarnings("serial")
        Map<Integer, InputState> inputStates = new HashMap<Integer, InputState>() {{
            put(1, state);
        }};
        
        holder.accept(new FrameAndInputStatesContainer(0, inputStates));
        
        Assert.assertSame(state, holder.getInputState());
    }
}
