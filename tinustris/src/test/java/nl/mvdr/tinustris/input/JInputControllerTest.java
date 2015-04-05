package nl.mvdr.tinustris.input;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.extern.slf4j.Slf4j;

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
        Map<Input, Set<InputMapping>> mapping = Stream.of(Input.values())
            .collect(Collectors.toMap(Function.identity(), input -> Collections.emptySet()));
        // also do not provide any controllers
        JInputControllerConfiguration configuration = new JInputControllerConfiguration(mapping, Collections.emptySet());
        JInputController<Input> inputController = new JInputController<>(Input.class, configuration);
        log.info("Controller: " + inputController);

        InputState<Input> state = inputController.getInputState();

        log.info("State: " + state);
        for (Input input: Input.values()) {
            Assert.assertFalse(state.isPressed(input));
        }
    }
}
