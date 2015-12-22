package nl.mvdr.game.logging;

import org.junit.Test;

/**
 * Test class for {@link Logging}.
 * 
 * @author Martijn van de Rijdt
 */
public class LoggingTest {
    /** Test case for {@link Logging#logVersionInfo()}. */
    @Test
    public void testLogVersionInfo() {
        Logging logging = new DummyLogging();
        
        logging.logVersionInfo();
    }
}
