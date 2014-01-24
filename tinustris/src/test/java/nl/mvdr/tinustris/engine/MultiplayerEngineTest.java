package nl.mvdr.tinustris.engine;

import lombok.extern.slf4j.Slf4j;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test class for {@link MultiplayerEngine}.
 * 
 * @author Martijn van de Rijdt
 */
@Slf4j
public class MultiplayerEngineTest {
    /** Tests the constructor. */
    @Test
    public void testConstructorTwoPlayers() {
        new MultiplayerEngine(2);
    }
    
    /** Tests the constructor. */
    @Test
    public void testConstructorThreePlayers() {
        new MultiplayerEngine(3);
    }
    
    /** Tests the constructor. */
    @Test
    public void testConstructorFourPlayers() {
        new MultiplayerEngine(4);
    }
    
    /** Tests the constructor. */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorNegativeNumberOfPlayers() {
        new MultiplayerEngine(-2);
    }
    
    /** Tests the constructor. */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorNoPlayers() {
        new MultiplayerEngine(0);
    }
    
    /** Tests the constructor. */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorOnePlayer() {
        new MultiplayerEngine(1);
    }
    
    /** Test the {@link MultiplayerEngine#toString()} method. */
    @Test
    public void testToString() {
        MultiplayerEngine engine = new MultiplayerEngine(2);
        
        String string = engine.toString();
        
        log.info(string);
        Assert.assertNotNull(string);
        Assert.assertNotEquals("", string);
    }
}
