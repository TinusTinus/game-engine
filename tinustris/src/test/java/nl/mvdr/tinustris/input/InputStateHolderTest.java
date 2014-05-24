package nl.mvdr.tinustris.input;

import java.util.stream.Stream;

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
}
