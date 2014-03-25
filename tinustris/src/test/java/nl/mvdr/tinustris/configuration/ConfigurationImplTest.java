package nl.mvdr.tinustris.configuration;

import java.util.Collections;

import lombok.extern.slf4j.Slf4j;
import nl.mvdr.tinustris.gui.GraphicsStyle;

import org.junit.Test;

/**
 * Test class for {@link ConfigurationImpl}.
 * 
 * @author Martijn van de Rijdt
 */
@Slf4j
public class ConfigurationImplTest {
    /** Test method for {@link ConfigurationImpl#ConfigurationImpl(int, GraphicsStyle, java.util.List, Behavior, int)}. */
    @Test
    public void testConstructor() {
        ConfigurationImpl configuration = new ConfigurationImpl(1, GraphicsStyle.defaultStyle(),
                Collections.emptyList(), Behavior.defaultBehavior(), 0);

        log.info(configuration.toString());
    }

    /** Test method for {@link ConfigurationImpl#ConfigurationImpl(int, GraphicsStyle, java.util.List, Behavior, int)}. */
    @Test(expected = NullPointerException.class)
    public void testConstructorNullStyle() {
        new ConfigurationImpl(1, null, Collections.emptyList(), Behavior.defaultBehavior(), 0);
    }
    
    /** Test method for {@link ConfigurationImpl#ConfigurationImpl(int, GraphicsStyle, java.util.List, Behavior, int)}. */
    @Test(expected = NullPointerException.class)
    public void testConstructorNullInputConfigu() {
        new ConfigurationImpl(1, GraphicsStyle.defaultStyle(), null, Behavior.defaultBehavior(), 0);
    }
    
    /** Test method for {@link ConfigurationImpl#ConfigurationImpl(int, GraphicsStyle, java.util.List, Behavior, int)}. */
    @Test(expected = NullPointerException.class)
    public void testConstructorNullBehavior() {
        new ConfigurationImpl(1, GraphicsStyle.defaultStyle(), Collections.emptyList(), null, 0);
    }
}
