package nl.mvdr.tinustris.input;

import lombok.extern.slf4j.Slf4j;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.bridge.SLF4JBridgeHandler;

/**
 * Test class for {@link JInputController}.
 * 
 * This test class is an integration test. It is disabled by default, because it relies on JInput's native libraries.
 * These are not available by default. If you want to run this class, make sure that the java.library.path system
 * property contains target/natives in this project directory.
 * 
 * In Eclipse, you can do this by opening the Run Configuration, opening the arguments tab and pasting the following
 * into the "VM Arguments" text area:
 * 
 * <pre>
 * -Djava.library.path=${project_loc}/target/natives
 * </pre>
 * 
 * @author Martijn van de Rijdt
 */
@Slf4j
@Ignore // see Javadoc
public class JInputControllerIntegrationTest {
    /** Setup method. */
    @Before
    public void setUp() {
        // JInput uses java.util.logging; redirect to slf4j.

        // remove existing handlers attached to j.u.l root logger
        SLF4JBridgeHandler.removeHandlersForRootLogger();

        // add SLF4JBridgeHandler to j.u.l's root logger, should be done once during
        // the initialization phase of your application
        SLF4JBridgeHandler.install();

        // log classpath and library path
        log.info("java.class.path: " + System.getProperty("java.class.path"));
        log.info("java.library.path: " + System.getProperty("java.library.path"));
    }

    /** 
     * Tests the getInputState method, using the default button mapping.
     * 
     * @throws NoSuitableControllerException unexpected exception
     */
    @Test
    public void test() throws NoSuitableControllerException {
        JInputController inputController = new JInputController(JInputControllerConfiguration.defaultConfiguration());
        log.info("Controller: " + inputController);

        InputState state = inputController.getInputState();

        log.info("State: " + state);
        // state depends on what inputs the user was pressing; just check that they are non-null
        Assert.assertNotNull(state);
    }
}
