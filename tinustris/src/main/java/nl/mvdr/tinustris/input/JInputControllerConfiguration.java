package nl.mvdr.tinustris.input;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import net.java.games.input.Component;
import net.java.games.input.Component.Identifier;
import net.java.games.input.Component.Identifier.Axis;
import net.java.games.input.Component.Identifier.Button;
import net.java.games.input.Component.Identifier.Key;
import net.java.games.input.Component.POV;
import net.java.games.input.Controller;
import net.java.games.input.Controller.Type;
import net.java.games.input.ControllerEnvironment;

/**
 * Configuration for {@link JInputController}.
 * 
 * @author Martijn van de Rijdt
 */
@RequiredArgsConstructor
@Getter
@Slf4j
@ToString
public class JInputControllerConfiguration {
    /**
     * Key / button mapping. Not every input needs to be mapped, which is to say: values in this map may be empty sets;
     * if an input is not mapped it will simply never get pressed. The mapping should contain values for all valid keys.
     */
    @NonNull
    private final Map<Input, Set<InputMapping>> mapping;

    /** All relevant controllers. All of the components in {@link mapping} must belong to one of these controllers. */
    @NonNull
    private final Set<Controller> controllers;
    
    /**
     * Creates a default configuration mapping, using keyboard controls, where all movement options are bound to the
     * arrow keys and other actions to Z, X and C. At least one keyboard controller must be present.
     * 
     * @return default configuration
     */
    public static JInputControllerConfiguration defaultConfiguration() throws NoSuitableControllerException {
        return defaultKeyboardConfiguration();
    }

    /**
     * Creates a configuration mapping, using keyboard controls, where all movement options are bound to the arrow keys
     * and other actions to Z, X and C. At least one keyboard controller must be present.
     * 
     * @return default keyboard configuration
     * @throws NoSuitableControllerException in case there is no keyboard
     */
    public static JInputControllerConfiguration defaultKeyboardConfiguration() throws NoSuitableControllerException {
        Controller[] controllersFromEnvironment = ControllerEnvironment.getDefaultEnvironment().getControllers();
        if (controllersFromEnvironment.length == 0) {
            throw new NoSuitableControllerException(
                    "No controllers present."
                    + " This may mean JInput is not present on java.library.path,"
                    + " or that JInput could not find any input devices.");
        }
        
        // find the keyboard controller(s)
        Set<Controller> controllers = Stream.of(controllersFromEnvironment)
            .map(JInputControllerConfiguration::log)
            .filter(controller -> controller.getType() == Type.KEYBOARD)
            .collect(Collectors.toSet());
        if (controllers.isEmpty()) {
            throw new NoSuitableControllerException("No keyboard present!");
        }
        log.info("Using keyboard controllers: " + controllers);
        
        Map<Input, Set<InputMapping>> mapping = Stream.of(Input.values())
                .collect(Collectors.toMap(Function.identity(), input -> new HashSet<>()));
        for (Controller keyboard : controllers) {
            mapping.get(Input.LEFT)
                .add(new InputMapping(getComponent(keyboard, Key.LEFT), f -> f != 0.0f));
            mapping.get(Input.RIGHT)
                .add(new InputMapping(getComponent(keyboard, Key.RIGHT), f -> f != 0.0f));
            mapping.get(Input.SOFT_DROP)
                .add(new InputMapping(getComponent(keyboard, Key.DOWN), f -> f != 0.0f));
            mapping.get(Input.HARD_DROP)
                .add(new InputMapping(getComponent(keyboard, Key.UP), f -> f != 0.0f));
            mapping.get(Input.TURN_LEFT)
                .add(new InputMapping(getComponent(keyboard, Key.Z), f -> f != 0.0f));
            mapping.get(Input.TURN_RIGHT)
                .add(new InputMapping(getComponent(keyboard, Key.X), f -> f != 0.0f));
            mapping.get(Input.HOLD)
                .add(new InputMapping(getComponent(keyboard, Key.C), f -> f != 0.0f));
        }
        log.info("Created input mapping: " + mapping);
        
        return new JInputControllerConfiguration(mapping, controllers);
    }
    
    /**
     * Creates a configuration mapping, using gamepad controls, where all movement options are bound to the D-pad / left
     * analog stick and other actions to face buttons. At least one joypad controller must be present. Tested using a
     * wireless XBox 360 controller.
     * 
     * @return default gamepad configuration
     * @throws NoSuitableControllerException in case there is no gamepad
     */
    public static JInputControllerConfiguration defaultGamepadConfiguration() throws NoSuitableControllerException {
        Controller[] controllersFromEnvironment = ControllerEnvironment.getDefaultEnvironment().getControllers();
        if (controllersFromEnvironment.length == 0) {
            throw new NoSuitableControllerException(
                    "No controllers present."
                    + " This may mean JInput is not present on java.library.path,"
                    + " or that JInput could not find any input devices.");
        }
        
        // find the gamepad controller(s)
        Set<Controller> controllers = Stream.of(controllersFromEnvironment)
            .map(JInputControllerConfiguration::log)
            .filter(controller -> controller.getType() == Type.GAMEPAD)
            .collect(Collectors.toSet());
        if (controllers.isEmpty()) {
            throw new NoSuitableControllerException("No gamepad present!");
        }
        log.info("Using gamepad controllers: " + controllers);
        
        Map<Input, Set<InputMapping>> mapping = Stream.of(Input.values())
                .collect(Collectors.toMap(Function.identity(), input -> new HashSet<>()));
                
        for (Controller gamepad : controllers) {
            // analogue stick
            mapping.get(Input.LEFT)
                .add(new InputMapping(getComponent(gamepad, Axis.X), f -> f == -1.0f));
            mapping.get(Input.RIGHT)
                .add(new InputMapping(getComponent(gamepad, Axis.X), f -> f == 1.0f));
            mapping.get(Input.SOFT_DROP)
                .add(new InputMapping(getComponent(gamepad, Axis.Y), f -> f == 1.0f));
            mapping.get(Input.HARD_DROP)
                .add(new InputMapping(getComponent(gamepad, Axis.Y), f -> f == -1.0f));
            // d-pad
            mapping.get(Input.LEFT)
                .add(new InputMapping(getComponent(gamepad, Axis.POV), f -> f == POV.LEFT));
            mapping.get(Input.RIGHT)
                .add(new InputMapping(getComponent(gamepad, Axis.POV), f -> f == POV.RIGHT));
            mapping.get(Input.SOFT_DROP)
                .add(new InputMapping(getComponent(gamepad, Axis.POV), f -> f == POV.DOWN));
            mapping.get(Input.HARD_DROP)
                .add(new InputMapping(getComponent(gamepad, Axis.POV), f -> f == POV.UP));
            // face buttons
            mapping.get(Input.TURN_LEFT)
                .add(new InputMapping(getComponent(gamepad, Button._2), f -> f != 0.0f));
            mapping.get(Input.TURN_RIGHT)
                .add(new InputMapping(getComponent(gamepad, Button._0), f -> f != 0.0f));
            mapping.get(Input.HOLD)
                .add(new InputMapping(getComponent(gamepad, Button._3), f -> f != 0.0f));
        }
        log.info("Created input mapping: " + mapping);
        
        return new JInputControllerConfiguration(mapping, controllers);
    }
    
    /**
     * Logs the given controller.
     * 
     * @param controller
     *            controller
     * @return the input
     */
    private static Controller log(Controller controller) {
        log.info("Controller found: {}, type: {}, class: {}", controller, controller.getType(), controller.getClass()
                .getName());
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
    private static Component getComponent(Controller keyboard, Identifier identifier) {
        Component component = keyboard.getComponent(identifier);
        if (component == null) {
            throw new IllegalStateException("Unable to find key: " + identifier);
        }
        return component;
    }
}
