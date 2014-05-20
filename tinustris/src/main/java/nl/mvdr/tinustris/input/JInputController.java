package nl.mvdr.tinustris.input;

import java.util.EnumSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import net.java.games.input.Controller;

/**
 * Implementation of InputController that uses JInput to determine the current input state.
 * 
 * @author Martijn van de Rijdt
 */
@RequiredArgsConstructor
@ToString
public class JInputController implements InputController {
    /** Configuration of this component. */
    @NonNull
    private final JInputControllerConfiguration configuration;

    /** {@inheritDoc} */
    @Override
    public InputState getInputState() {
        configuration.getControllers().forEach(Controller::poll);
        
        Set<Input> pressedInputs = Stream.of(Input.values())
            .filter(this::isPressed)
            .collect(Collectors.toCollection(() -> EnumSet.noneOf(Input.class)));
        
        return pressedInputs::contains;
    }
    
    /**
     * Checks whether the given input is pressed.
     * 
     * @param input input
     * @return whether the input is pressed
     */
    private boolean isPressed(Input input) {
        return configuration.getMapping()
            .get(input)
            .stream()
            .anyMatch(InputMapping::isPressed);
    }

    /** {@inheritDoc} */
    @Override
    public boolean isLocal() {
        return true;
    }
}
