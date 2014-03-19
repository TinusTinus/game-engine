package nl.mvdr.tinustris.input;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import net.java.games.input.Component;
import net.java.games.input.Component.Identifier;
import net.java.games.input.Component.Identifier.Key;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;
import net.java.games.input.Keyboard;

/**
 * Implementation of InputController that uses JInput to determine the current input state.
 * 
 * @author Martijn van de Rijdt
 */
@RequiredArgsConstructor
@ToString
@Slf4j
public class JInputController implements InputController {
    /**
     * Key / button mapping. Not every input needs to be mapped, which is to say: values in this map may be empty sets;
     * if an input is not mapped it will simply never get pressed. The mapping should contain values for all valid keys.
     */
    @NonNull
    private final Map<Input, Set<Component>> mapping;

    /** All relevant controllers. All of the components in mapping must belong to one of these controllers. */
    @NonNull
    private final Set<Controller> controllers;

    /** Constructor using the default mapping. */
    public JInputController() {
        Controller[] controllersFromEnvironment = ControllerEnvironment.getDefaultEnvironment().getControllers();
        if (controllersFromEnvironment.length == 0) {
            throw new IllegalStateException(
                    "No controllers present."
                    + " This may mean JInput is not present on java.library.path,"
                    + " or that JInput could not find any input devices.");
        }
        
        // find the keyboard controller(s)
        controllers = Arrays.asList(controllersFromEnvironment)
            .stream()
            .map(this::log)
            .filter(controller -> controller instanceof Keyboard)
            .collect(Collectors.toSet());
        if (controllers.isEmpty()) {
            throw new IllegalStateException("No keyboard present!");
        }
        log.info("Using keyboard controllers: " + controllers);
        
        mapping = new EnumMap<>(Input.class);
        for (Input input: Input.values()) {
            mapping.put(input, new HashSet<Component>());
        }
        for (Controller keyboard : controllers) {
            mapping.get(Input.LEFT).add(getComponent(keyboard, Key.LEFT));
            mapping.get(Input.RIGHT).add(getComponent(keyboard, Key.RIGHT));
            mapping.get(Input.SOFT_DROP).add(getComponent(keyboard, Key.DOWN));
            mapping.get(Input.HARD_DROP).add(getComponent(keyboard, Key.UP));
            mapping.get(Input.TURN_LEFT).add(getComponent(keyboard, Key.Z));
            mapping.get(Input.TURN_RIGHT).add(getComponent(keyboard, Key.X));
            mapping.get(Input.HOLD).add(getComponent(keyboard, Key.C));
        }
        log.info("Using default input mapping: " + mapping);
    }
    
    /**
     * Logs the given controller.
     * 
     * @param controller controller
     * @return the input
     */
    private Controller log(Controller controller) {
        log.info("Controller found: {}, type: {}, class: {}",
            controller, controller.getType(), controller.getClass().getName());
        return controller;
    }

    /**
     * Returns the component with the given identifier.
     * 
     * @param keyboard
     *            keyboard controller
     * @param identifier
     *            component identifier
     * @return component corresponding to the given key
     * @throws IllegalStateException
     *             in case the key cannot be found
     */
    private Component getComponent(Controller keyboard, Identifier identifier) {
        Component component = keyboard.getComponent(identifier);
        if (component == null) {
            throw new IllegalStateException("Unable to find key: " + identifier);
        }
        return component;
    }

    /** {@inheritDoc} */
    @Override
    public InputState getInputState() {
        controllers.forEach(Controller::poll);
        
        Set<Input> pressedInputs = Arrays.asList(Input.values())
            .stream()
            .filter(this::isPressed)
            .collect(Collectors.toCollection(() -> EnumSet.noneOf(Input.class)));
        
        return input -> pressedInputs.contains(input);
    }
    
    /**
     * Checks whether the given input is pressed.
     * 
     * @param input input
     * @return whether the input is pressed
     */
    private boolean isPressed(Input input) {
        return mapping.get(input)
            .stream()
            .map(component -> component.getPollData())
            .anyMatch(pollData -> pollData.floatValue() != 0.0f);
    }
}
