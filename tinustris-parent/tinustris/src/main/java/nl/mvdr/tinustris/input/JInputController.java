package nl.mvdr.tinustris.input;

import java.util.Collections;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import net.java.games.input.Component;
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
     * Key / button mapping. Not every input needs to be mapped; if an input is not mapped it will simply never get
     * pressed.
     */
    @NonNull
    private final Map<Input, Component> mapping;

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
        
        // find the keyboard controller
        Controller keyboard = null;
        for (Controller controller : controllersFromEnvironment) {
            if (controller instanceof Keyboard) {
                keyboard = controller;
            }
        }
        
        if (keyboard == null) {
            throw new IllegalStateException("No keyboard present!");
        }
        
        log.info("Using keyboard controller: " + keyboard.getName());
        this.controllers = Collections.singleton(keyboard);
        
        this.mapping = new EnumMap<>(Input.class);
        mapping.put(Input.LEFT, getComponent(keyboard, Key.LEFT));
        mapping.put(Input.RIGHT, getComponent(keyboard, Key.RIGHT));
        mapping.put(Input.FASTER_DROP, getComponent(keyboard, Key.DOWN));
        mapping.put(Input.INSTANT_DROP, getComponent(keyboard, Key.UP));
        mapping.put(Input.TURN_LEFT, getComponent(keyboard, Key.Z));
        mapping.put(Input.TURN_RIGHT, getComponent(keyboard, Key.X));
        mapping.put(Input.HOLD, getComponent(keyboard, Key.C));
        log.info("Using default input mapping: " + mapping);
    }

    /**
     * Returns the component with the given identifier.
     * 
     * @param keyboard
     *            keyboard controller
     * @param key
     *            key identifier
     * @return component corresponding to the given key
     * @throws IllegalStateException
     *             in case the key cannot be found
     */
    private Component getComponent(Controller keyboard, Key key) {
        Component component = keyboard.getComponent(key);
        if (component == null) {
            throw new IllegalStateException("Unable to find key: " + key);
        }
        return component;
    }

    /** {@inheritDoc} */
    @Override
    public InputState getInputState() {
        for (Controller controller: controllers) {
            controller.poll();
        }
        
        Set<Input> pressedInputs = EnumSet.noneOf(Input.class);
        for (Input input: Input.values()) {
            Component component = mapping.get(input);
            if (component != null) {
                float pollData = component.getPollData();
                if (pollData != 0.0f) {
                    pressedInputs.add(input);
                }
            }
        }
        return new InputStateImpl(pressedInputs);
    }
}
