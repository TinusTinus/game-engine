package nl.mvdr.tinustris.input;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import net.java.games.input.Component;
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
     * @return component
     */
    // TODO also return the corresponding controller and the matcher predicate
    public Component waitForComponentAction() {
        log.info("Waiting for component action.");
        List<Controller> controllers = Arrays.asList(ControllerEnvironment.getDefaultEnvironment().getControllers())
                .stream()
                .filter(controller -> controller.getType() == Type.KEYBOARD || controller.getType() == Type.GAMEPAD)
                .collect(Collectors.toList());
        log.info("Using controllers: " + controllers);

        Optional<Component> result = Optional.empty();
        while (!result.isPresent()) {
            controllers.forEach(Controller::poll);
            result = controllers.stream()
                .map(controller -> Arrays.asList(controller.getComponents()))
                .flatMap(List<Component>::stream)
                .filter(this::isPressed)
                .findFirst();
        }

        return result.get();
    }
    
    private boolean isPressed(Component component) {
        return component.getPollData() != 0.0f; // TODO fix this!
    }

}
