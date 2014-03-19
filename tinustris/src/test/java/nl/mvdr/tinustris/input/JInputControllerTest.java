package nl.mvdr.tinustris.input;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import net.java.games.input.Component;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test class for {@link JInputController}.
 * 
 * @author Martijn van de Rijdt
 */
@Slf4j
public class JInputControllerTest {
    /** Tests the getInputState method, where no input has been mapped. */
    @Test
    public void testNoInputs() {
        // map every input to an empty set of components
        Map<Input, Set<Component>> mapping = Arrays.asList(Input.values())
            .stream()
            .collect(Collectors.toMap(Function.identity(), input -> Collections.emptySet()));
        // also do not provide any controllers
        JInputControllerConfiguration configuration = new JInputControllerConfiguration(mapping, Collections.emptySet());
        JInputController inputController = new JInputController(configuration);
        log.info("Controller: " + inputController);

        InputState state = inputController.getInputState();

        log.info("State: " + state);
        for (Input input: Input.values()) {
            Assert.assertFalse(state.isPressed(input));
        }
    }
}
