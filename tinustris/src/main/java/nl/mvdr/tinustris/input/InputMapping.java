package nl.mvdr.tinustris.input;

import java.util.function.Predicate;

import lombok.RequiredArgsConstructor;
import lombok.ToString;
import net.java.games.input.Component;

/**
 * Mapping value for a single input.
 * 
 * @author Martijn van de Rijdt
 */
@RequiredArgsConstructor
@ToString
class InputMapping {
    /** Component. */
    private final Component component;
    /** Predicate to determine whether the component's poll data indicates that the input has been pressed. */
    private final Predicate<Float> matcher;
    
    /** @return whether the input is pressed */
    boolean isPressed() {
        float pollData = component.getPollData();
        return matcher.test(pollData);
    }
}
