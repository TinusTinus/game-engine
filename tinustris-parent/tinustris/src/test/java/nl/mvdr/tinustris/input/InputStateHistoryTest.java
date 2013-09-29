package nl.mvdr.tinustris.input;

import java.util.Collections;
import java.util.EnumSet;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test class for {@link InputStateHistory}.
 * 
 * @author Martijn van de Rijdt
 */
public class InputStateHistoryTest {
    /** Test method for {@link InputStateHistory#InputStateHistory()}. */
    @Test
    public void testConstructor() {
        InputStateHistory history = new InputStateHistory();
        
        for (Input input: Input.values()) {
            Assert.assertEquals(input.toString(), 0, history.getNumberOfFrames(input));
        }
    }
    
    /** Test method for {@link InputStateHistory#next(InputState)} in case nothing is pressed. */
    @Test
    public void testGetNumberOfFramesNothingPressed() {
        InputStateHistory history = new InputStateHistory();
        InputState inputState = new InputStateImpl(Collections.<Input>emptySet());
        
        history = history.next(inputState);
        
        for (Input input: Input.values()) {
            Assert.assertEquals(input.toString(), 0, history.getNumberOfFrames(input));
        }
    }
    
    /** Test method for {@link InputStateHistory#next(InputState)} in case nothing is pressed. */
    @Test
    public void testGetNumberOfFramesNothingPressedTwice() {
        InputStateHistory history = new InputStateHistory();
        InputState inputState = new InputStateImpl(Collections.<Input>emptySet());
        
        history = history.next(inputState);
        history = history.next(inputState);
        
        for (Input input: Input.values()) {
            Assert.assertEquals(input.toString(), 0, history.getNumberOfFrames(input));
        }
    }
    
    /** Test method for {@link InputStateHistory#next(InputState)} in case every button is pressed. */
    @Test
    public void testGetNumberOfFramesEverythingPressed() {
        InputStateHistory history = new InputStateHistory();
        InputState inputState = new InputStateImpl(EnumSet.allOf(Input.class));
        
        history = history.next(inputState);
        
        for (Input input: Input.values()) {
            Assert.assertEquals(input.toString(), 1, history.getNumberOfFrames(input));
        }
    }
    
    /** Test method for {@link InputStateHistory#next(InputState)} in case every button is pressed. */
    @Test
    public void testGetNumberOfFramesEverythingPressedTwice() {
        InputStateHistory history = new InputStateHistory();
        InputState inputState = new InputStateImpl(EnumSet.allOf(Input.class));
        
        history = history.next(inputState);
        history = history.next(inputState);

        for (Input input: Input.values()) {
            Assert.assertEquals(input.toString(), 2, history.getNumberOfFrames(input));
        }
    }
    
    /** Test method for {@link InputStateHistory#next(InputState)} in case every button is pressed, then let go. */
    @Test
    public void testGetNumberOfFramesEverythingPressedThenLetGo() {
        InputStateHistory history = new InputStateHistory();
        
        history = history.next(new InputStateImpl(EnumSet.allOf(Input.class)));
        history = history.next(new InputStateImpl(Collections.<Input>emptySet()));
        
        for (Input input: Input.values()) {
            Assert.assertEquals(input.toString(), 0, history.getNumberOfFrames(input));
        }
    }
    
    /** Test method for {@link InputStateHistory#next(InputState)} in case a single button is pressed. */
    @Test
    public void testGetNumberOfFramesOneButtonPressed() {
        InputStateHistory history = new InputStateHistory();
        EnumSet<Input> inputs = EnumSet.noneOf(Input.class);
        inputs.add(Input.TURN_RIGHT);
        InputState inputState = new InputStateImpl(inputs);
        
        history = history.next(inputState);
        
        Assert.assertEquals(0, history.getNumberOfFrames(Input.FASTER_DROP));
        Assert.assertEquals(0, history.getNumberOfFrames(Input.HOLD));
        Assert.assertEquals(0, history.getNumberOfFrames(Input.INSTANT_DROP));
        Assert.assertEquals(0, history.getNumberOfFrames(Input.LEFT));
        Assert.assertEquals(0, history.getNumberOfFrames(Input.RIGHT));
        Assert.assertEquals(0, history.getNumberOfFrames(Input.TURN_LEFT));
        Assert.assertEquals(1, history.getNumberOfFrames(Input.TURN_RIGHT));
    }
}
