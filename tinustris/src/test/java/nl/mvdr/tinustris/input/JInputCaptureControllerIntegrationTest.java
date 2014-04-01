package nl.mvdr.tinustris.input;

import javafx.application.Application;
import javafx.application.Platform;
import lombok.extern.slf4j.Slf4j;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.bridge.SLF4JBridgeHandler;

/**
 * Integration test for {@link JInputCaptureController}.
 * 
 * This test class is an integration test. Test methods are disabled by default, because it relies on user input and
 * JInput's native libraries. These are not available by default. If you want to run this class, make sure that the
 * java.library.path system property contains target/natives in this project directory.
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
public class JInputCaptureControllerIntegrationTest {
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

        // show an application window, since JInput only captures input if running in an application that has focus
        Runnable runnable = () -> Application.launch(DummyApplication.class, new String[] {});
        new Thread(runnable, "JavaFX starter thread").start();
    }

    /** Teardown method. */
    @After
    public void tearDown() {
        Platform.exit();
    }

    /**
     * Test method for {@link JInputCaptureController#waitForComponentAction()}.
     * 
     * This test method shows a JavaFX application window which has focus. The user should press a valid input (button
     * or key or other supported input) before the test times out. The result is logged.
     */
    @Ignore
    @Test(timeout = 5000)
    public void testWaitForComponentAction() {
        JInputCaptureController controller = new JInputCaptureController();

        ControllerAndInputMapping result = controller.waitForComponentAction();

        Assert.assertNotNull(result);
        log.info(result.toString());
    }
    
    /**
     * Test method for {@link JInputCaptureController#waitForComponentAction()}.
     * 
     * This test method shows a JavaFX application window which has focus. The user should press a valid input (button
     * or key or other supported input) before the test times out, then release it again. The result is logged.
     * @throws Exception unexpected exception
     */
    @Ignore
    @Test(timeout = 5000)
    public void testWaitForRelease() throws Exception {
        JInputCaptureController controller = new JInputCaptureController();
        
        controller.call();
    }
}
