package nl.mvdr.tinustris.input;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.extern.slf4j.Slf4j;
import net.java.games.input.Component;
import net.java.games.input.Component.Identifier.Axis;
import net.java.games.input.Component.Identifier.Button;
import net.java.games.input.Component.Identifier.Key;
import net.java.games.input.Controller;
import net.java.games.input.Controller.Type;
import net.java.games.input.ControllerEnvironment;

/**
 * Controller for capturing user input. Can be used to let the user define their input configuration (as input for
 * {@link JInputController}) by pressing each of their preferred buttons in turn.
 * 
 * @author Martijn van de Rijdt
 */
@Slf4j
public class JInputCaptureController {
    /**
     * Periodically polls for user input. As soon as the user activates a JInput component, this method returns that
     * component as well as its corresponding controller.
     * 
     * @return input mapping and its corresponding controller
     */
    public ControllerAndInputMapping waitForComponentAction() {
        log.info("Waiting for component action.");
        List<Controller> controllers = Stream.of(ControllerEnvironment.getDefaultEnvironment().getControllers())
                .filter(controller -> controller.getType() == Type.KEYBOARD || controller.getType() == Type.GAMEPAD)
                .collect(Collectors.toList());
        log.info("Using controllers: " + controllers);

        Optional<ControllerAndInputMapping> result = Optional.empty();
        while (!result.isPresent()) {
            controllers.forEach(Controller::poll);
            
            Function<Controller, Stream<ControllerAndInputMapping>> toControllerAndInputMappingStream = 
                controller -> Stream.of(controller.getComponents())
                    .map(component -> new ControllerAndInputMapping(controller, new InputMapping(component, f -> f == component.getPollData())));
            result = controllers.stream()
                .map(toControllerAndInputMappingStream)
                .flatMap(Function.identity())
                .filter(controllerAndInputMapping -> isPressed(controllerAndInputMapping.getMapping().getComponent()))
                .findFirst();
            
            // TODO sleep if not found?
        }
        
        log.info("Mapped: " + result.get());

        return result.get();
    }
    
    /**
     * Waits until the given component is released.
     * 
     * @param component component to be checked
     */
    public void waitUntilReleased(Component component) {
        while(isPressed(component)) {
            // TODO sleep
        }
    }

    /**
     * Checks whether the given component is currently pressed (and is also one of the components supported by the
     * JInputController).
     * 
     * @param component
     *            component
     * @return whether the component is pressed
     */
    private boolean isPressed(Component component) {
        boolean result;
        
        if (component.getIdentifier() instanceof Button || component.getIdentifier() instanceof Key) {
            result = component.getPollData() == 1.0f;
        } else if (component.getIdentifier() == Axis.POV) {
            result = component.getPollData() != 0.0f;
        } else if (component.getIdentifier() instanceof Axis && !component.isRelative()) {
            result = Math.abs(component.getPollData()) == 1.0;
        } else {
            // unsupported
            result = false;
        }
        
        return result;
    }
}
