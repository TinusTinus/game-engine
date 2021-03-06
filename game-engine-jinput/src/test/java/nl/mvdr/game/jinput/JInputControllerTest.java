package nl.mvdr.game.jinput;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.extern.slf4j.Slf4j;
import nl.mvdr.game.jinput.DummyInput;
import nl.mvdr.game.input.InputState;
import nl.mvdr.game.jinput.InputMapping;
import nl.mvdr.game.jinput.JInputController;
import nl.mvdr.game.jinput.JInputControllerConfiguration;

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
        Map<DummyInput, Set<InputMapping>> mapping = Stream.of(DummyInput.values())
            .collect(Collectors.toMap(Function.identity(), input -> Collections.emptySet()));
        // also do not provide any controllers
        JInputControllerConfiguration<DummyInput> configuration = new JInputControllerConfiguration<>(mapping, Collections.emptySet());
        JInputController<DummyInput> inputController = new JInputController<>(DummyInput.class, configuration);
        log.info("Controller: " + inputController);

        InputState<DummyInput> state = inputController.getInputState();

        log.info("State: " + state);
        for (DummyInput input: DummyInput.values()) {
            Assert.assertFalse(state.isPressed(input));
        }
    }
}
