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
    /** Test method for {@link ConfigurationImpl#ConfigurationImpl(java.util.List, GraphicsStyle, Behavior, int))}. */
    @Test
    public void testConstructor() {
        ConfigurationImpl configuration = new ConfigurationImpl(Collections.singletonList(() -> ""),
                GraphicsStyle.defaultStyle(), Behavior.defaultBehavior(), 0);

        log.info(configuration.toString());
    }

    /** Test method for {@link ConfigurationImpl#ConfigurationImpl(java.util.List, GraphicsStyle, Behavior, int))}. */
    @Test(expected = NullPointerException.class)
    public void testConstructorNullPlayerConfiguration() {
        new ConfigurationImpl(null, GraphicsStyle.defaultStyle(), Behavior.defaultBehavior(), 0);
    }
    
    /** Test method for {@link ConfigurationImpl#ConfigurationImpl(java.util.List, GraphicsStyle, Behavior, int))}. */
    @Test(expected = NullPointerException.class)
    public void testConstructorNullStyle() {
        new ConfigurationImpl(Collections.singletonList(() -> ""), null, Behavior.defaultBehavior(), 0);
    }

    /** Test method for {@link ConfigurationImpl#ConfigurationImpl(java.util.List, GraphicsStyle, Behavior, int))}. */
    @Test(expected = NullPointerException.class)
    public void testConstructorNullBehavior() {
        new ConfigurationImpl(Collections.singletonList(() -> ""), GraphicsStyle.defaultStyle(), null, 0);
    }
}
