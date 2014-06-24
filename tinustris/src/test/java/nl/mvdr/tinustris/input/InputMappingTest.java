package nl.mvdr.tinustris.input;

import net.java.games.input.Component;
import net.java.games.input.Component.POV;
import net.java.games.input.Component.Identifier.Axis;
import net.java.games.input.Component.Identifier.Button;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test class for {@link InputMapping}.
 * 
 * @author Martijn van de Rijdt
 */
public class InputMappingTest {
    /** Test method for the {@link InputMapping#toString} method. */
    @Test
    public void testToStringNotDPad() {
        Component component = new DummyComponent(new Button("dummy button"));
        InputMapping mapping = new InputMapping(component, 0);
        
        Assert.assertEquals("Dummy", mapping.toString());
    }
    
    /** Test method for the {@link InputMapping#toString} method. */
    @Test
    public void testToStringDPadUp() {
        Component component = new DummyComponent(Axis.POV);
        InputMapping mapping = new InputMapping(component, POV.UP);
        
        Assert.assertEquals("D-pad up", mapping.toString());
    }
    
    /** Test method for the {@link InputMapping#toString} method. */
    @Test
    public void testToStringDPadUpRight() {
        Component component = new DummyComponent(Axis.POV);
        InputMapping mapping = new InputMapping(component, POV.UP_RIGHT);
        
        Assert.assertEquals("D-pad up-right", mapping.toString());
    }
    
    /** Test method for the {@link InputMapping#toString} method. */
    @Test
    public void testToStringDPadRight() {
        Component component = new DummyComponent(Axis.POV);
        InputMapping mapping = new InputMapping(component, POV.RIGHT);
        
        Assert.assertEquals("D-pad right", mapping.toString());
    }
    
    /** Test method for the {@link InputMapping#toString} method. */
    @Test
    public void testToStringDPadDownRight() {
        Component component = new DummyComponent(Axis.POV);
        InputMapping mapping = new InputMapping(component, POV.DOWN_RIGHT);
        
        Assert.assertEquals("D-pad down-right", mapping.toString());
    }
    
    /** Test method for the {@link InputMapping#toString} method. */
    @Test
    public void testToStringDPadDown() {
        Component component = new DummyComponent(Axis.POV);
        InputMapping mapping = new InputMapping(component, POV.DOWN);
        
        Assert.assertEquals("D-pad down", mapping.toString());
    }
    
    /** Test method for the {@link InputMapping#toString} method. */
    @Test
    public void testToStringDPadDownLeft() {
        Component component = new DummyComponent(Axis.POV);
        InputMapping mapping = new InputMapping(component, POV.DOWN_LEFT);
        
        Assert.assertEquals("D-pad down-left", mapping.toString());
    }
    
    /** Test method for the {@link InputMapping#toString} method. */
    @Test
    public void testToStringDPadLeft() {
        Component component = new DummyComponent(Axis.POV);
        InputMapping mapping = new InputMapping(component, POV.LEFT);
        
        Assert.assertEquals("D-pad left", mapping.toString());
    }
    
    /** Test method for the {@link InputMapping#toString} method. */
    @Test
    public void testToStringDPadUpLeft() {
        Component component = new DummyComponent(Axis.POV);
        InputMapping mapping = new InputMapping(component, POV.UP_LEFT);
        
        Assert.assertEquals("D-pad up-left", mapping.toString());
    }
    
    /** Test method for the {@link InputMapping#toString} method. */
    @Test
    public void testToStringDPadUnexpectedValue() {
        Component component = new DummyComponent(Axis.POV);
        InputMapping mapping = new InputMapping(component, 3.14f);
        
        Assert.assertEquals("D-pad", mapping.toString());
    }
}
