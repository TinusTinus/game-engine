package nl.mvdr.tinustris.input;

import lombok.extern.slf4j.Slf4j;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test class for {@link InputStateHistory}.
 * 
 * @author Martijn van de Rijdt
 */
@Slf4j
public class InputStateHistoryTest {
    /** Test method for {@link InputStateHistory#NEW}. */
    @Test
    public void testNew() {
        InputStateHistory history = InputStateHistory.NEW;
        
        for (Input input: Input.values()) {
            Assert.assertEquals(input.toString(), 0, history.getNumberOfFrames(input));
        }
    }
    
    /** Test method for {@link InputStateHistory#next(InputState)} in case nothing is pressed. */
    @Test
    public void testGetNumberOfFramesNothingPressed() {
        InputState inputState = input -> false;
        
        InputStateHistory history = InputStateHistory.NEW.next(inputState);
        
        for (Input input: Input.values()) {
            Assert.assertEquals(input.toString(), 0, history.getNumberOfFrames(input));
        }
    }
    
    /** Test method for {@link InputStateHistory#next(InputState)} in case nothing is pressed. */
    @Test
    public void testGetNumberOfFramesNothingPressedTwice() {
        InputStateHistory history = InputStateHistory.NEW;
        InputState inputState = input -> false;
        
        history = history.next(inputState);
        history = history.next(inputState);
        
        for (Input input: Input.values()) {
            Assert.assertEquals(input.toString(), 0, history.getNumberOfFrames(input));
        }
    }
    
    /** Test method for {@link InputStateHistory#next(InputState)} in case every button is pressed. */
    @Test
    public void testGetNumberOfFramesEverythingPressed() {
        InputState inputState = input -> true;
        
        InputStateHistory history = InputStateHistory.NEW.next(inputState);
        
        for (Input input: Input.values()) {
            Assert.assertEquals(input.toString(), 1, history.getNumberOfFrames(input));
        }
    }
    
    /** Test method for {@link InputStateHistory#next(InputState)} in case every button is pressed. */
    @Test
    public void testGetNumberOfFramesEverythingPressedTwice() {
        InputStateHistory history = InputStateHistory.NEW;
        InputState inputState = input -> true;
        
        history = history.next(inputState);
        history = history.next(inputState);

        for (Input input: Input.values()) {
            Assert.assertEquals(input.toString(), 2, history.getNumberOfFrames(input));
        }
    }
    
    /** Test method for {@link InputStateHistory#next(InputState)} in case every button is pressed, then let go. */
    @Test
    public void testGetNumberOfFramesEverythingPressedThenLetGo() {
        InputStateHistory history = InputStateHistory.NEW;
        
        history = history.next(input -> true);
        history = history.next(input -> false);
        
        for (Input input: Input.values()) {
            Assert.assertEquals(input.toString(), 0, history.getNumberOfFrames(input));
        }
    }
    
    /** Test method for {@link InputStateHistory#next(InputState)} in case a single button is pressed. */
    @Test
    public void testGetNumberOfFramesOneButtonPressed() {
        InputState inputState = input -> input == Input.TURN_RIGHT;
        
        InputStateHistory history = InputStateHistory.NEW.next(inputState);
        
        Assert.assertEquals(0, history.getNumberOfFrames(Input.SOFT_DROP));
        Assert.assertEquals(0, history.getNumberOfFrames(Input.HOLD));
        Assert.assertEquals(0, history.getNumberOfFrames(Input.HARD_DROP));
        Assert.assertEquals(0, history.getNumberOfFrames(Input.LEFT));
        Assert.assertEquals(0, history.getNumberOfFrames(Input.RIGHT));
        Assert.assertEquals(0, history.getNumberOfFrames(Input.TURN_LEFT));
        Assert.assertEquals(1, history.getNumberOfFrames(Input.TURN_RIGHT));
    }
    
    /** Test method for {@link InputStateHistory#toString()}. */
    @Test
    public void testNewToString() {
        String string = InputStateHistory.NEW.toString();
        
        log.info(string);
        Assert.assertNotNull(string);
        Assert.assertNotEquals("", string);        
    }
    
    /** Test method for {@link InputStateHistory#toString()}. */
    @Test
    public void testToString() {
        InputState inputState = input -> true;
        InputStateHistory history = InputStateHistory.NEW.next(inputState);
        
        String string = history.toString();
        
        log.info(string);
        Assert.assertNotNull(string);
        Assert.assertNotEquals("", string);        
    }
}
