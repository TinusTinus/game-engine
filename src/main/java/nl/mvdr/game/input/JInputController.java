package nl.mvdr.game.input;

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
 * @param <S> enum type containing all possible inputs from the user
 * 
 * @author Martijn van de Rijdt
 */
@RequiredArgsConstructor
@ToString
public class JInputController<S extends Enum<S>> implements InputController<S> {
    /** Actual enum type for input values. */
    private final Class<S> inputType;
    /** Configuration of this component. */
    @NonNull
    private final JInputControllerConfiguration<S> configuration;

    /** {@inheritDoc} */
    @Override
    public InputState<S> getInputState() {
        configuration.getControllers().forEach(Controller::poll);
        
        Set<S> pressedInputs = Stream.of(inputType.getEnumConstants())
            .filter(this::isPressed)
            .collect(Collectors.toCollection(() -> EnumSet.noneOf(inputType)));
        
        return new InputStateImpl<>(pressedInputs);
    }
    
    /**
     * Checks whether the given input is pressed.
     * 
     * @param input input
     * @return whether the input is pressed
     */
    private boolean isPressed(S input) {
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
