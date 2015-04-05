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
    /** Tests the {@link InputStateHolder#getInputState()} method. */
    @Test
    public void testGetInputStateDefaultValue() {
        InputStateHolder holder = new InputStateHolder(true);
        
        InputState<Input> state = holder.getInputState();
        
        Stream.of(Input.values())
            .forEach(input -> Assert.assertFalse(state.isPressed(input)));
    }
    
    /** Tests the {@link InputStateHolder#getInputState(int)} method. */
    @Test
    public void testGetInputStateIndexedDefaultValue() {
        InputStateHolder holder = new InputStateHolder(true);
        
        InputState<Input> state = holder.getInputState(123);
        
        Stream.of(Input.values())
            .forEach(input -> Assert.assertFalse(state.isPressed(input)));
    }

    /** Tests the {@link InputStateHolder#getInputState()} method. */
    @Test
    public void testGetInputState() {
        InputStateHolder holder = new InputStateHolder(true);
        InputState<Input> state = input -> true;
        holder.putState(0, state);
        
        Assert.assertSame(state, holder.getInputState());
    }
    
    /** Tests the {@link InputStateHolder#getInputState(int)} method. */
    @Test
    public void testGetInputStateIndexed() {
        InputStateHolder holder = new InputStateHolder(true);
        InputState<Input> state = input -> true;
        holder.putState(0, state);
        
        Assert.assertSame(state, holder.getInputState(0));
    }
    
    /** Tests the {@link InputStateHolder#getInputState(int)} method. */
    @Test
    public void testGetInputStateIndexedGreater() {
        InputStateHolder holder = new InputStateHolder(true);
        InputState<Input> state = input -> true;
        holder.putState(0, state);
        
        Assert.assertSame(state, holder.getInputState(1));
    }
    
    /** Tests the {@link InputStateHolder#getInputState(int)} method. */
    @Test
    public void testGetInputStateIndexedLess() {
        InputStateHolder holder = new InputStateHolder(true);
        InputState<Input> state = input -> true;
        holder.putState(1, state);
        
        Assert.assertNotEquals(state, holder.getInputState(0));
    }
    
    /** Tests the {@link InputStateHolder#allInputsKnownUntil(int)} method. */
    @Test
    public void testAllInputsKnownZero() {
        InputStateHolder holder = new InputStateHolder(true);
        
        Assert.assertTrue(holder.allInputsKnownUntil(0));
    }
    
    /** Tests the {@link InputStateHolder#allInputsKnownUntil(int)} method. */
    @Test
    public void testAllInputsKnownNegative() {
        InputStateHolder holder = new InputStateHolder(true);
        
        Assert.assertTrue(holder.allInputsKnownUntil(-1));
    }

    /** Tests the {@link InputStateHolder#allInputsKnownUntil(int)} method. */
    @Test
    public void testAllInputsKnownEmptyOne() {
        InputStateHolder holder = new InputStateHolder(true);
        
        Assert.assertFalse(holder.allInputsKnownUntil(1));
    }
    
    /** Tests the {@link InputStateHolder#allInputsKnownUntil(int)} method. */
    @Test
    public void testAllInputsKnownNoneMissing() {
        InputStateHolder holder = new InputStateHolder(true);
        holder.putState(0, input -> false);
        holder.putState(1, input -> false);
        holder.putState(2, input -> false);
        
        Assert.assertTrue(holder.allInputsKnownUntil(3));
    }
    
    /** Tests the {@link InputStateHolder#allInputsKnownUntil(int)} method. */
    @Test
    public void testAllInputsKnownOldIndex() {
        InputStateHolder holder = new InputStateHolder(true);
        holder.putState(0, input -> false);
        holder.putState(1, input -> false);
        holder.putState(2, input -> false);
        
        Assert.assertTrue(holder.allInputsKnownUntil(2));
    }
    
    /** Tests the {@link InputStateHolder#allInputsKnownUntil(int)} method. */
    @Test
    public void testAllInputsKnownMiddleMissingMissing() {
        InputStateHolder holder = new InputStateHolder(true);
        holder.putState(0, input -> false);
        holder.putState(2, input -> false);
        
        Assert.assertFalse(holder.allInputsKnownUntil(3));
    }
    
    /** Tests the {@link InputStateHolder#allInputsKnownUntil(int)} method. */
    @Test
    public void testAllInputsKnownLastMissing() {
        InputStateHolder holder = new InputStateHolder(true);
        holder.putState(0, input -> false);
        holder.putState(1, input -> false);
        
        Assert.assertFalse(holder.allInputsKnownUntil(3));
    }
    
    /** Tests the {@link InputStateHolder#putState(int, InputState)} in case a null value is passed in. */
    @Test(expected = NullPointerException.class)
    public void testPutNullState() {
        InputStateHolder holder = new InputStateHolder(true);
        
        holder.putState(0, null);
    }
}
