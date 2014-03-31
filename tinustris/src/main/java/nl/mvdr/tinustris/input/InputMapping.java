package nl.mvdr.tinustris.input;

import java.util.function.Predicate;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import net.java.games.input.Component;
import net.java.games.input.Component.Identifier.Axis;
import net.java.games.input.Component.POV;

/**
 * Mapping value for a single input.
 * 
 * @author Martijn van de Rijdt
 */
@RequiredArgsConstructor
public class InputMapping {
    /** Component. */
    @Getter(AccessLevel.PACKAGE)
    @NonNull
    private final Component component;
    /** Predicate to determine whether the component's poll data indicates that the input has been pressed. */
    @NonNull
    private final Predicate<Float> matcher;
    
    /** @return whether the input is pressed */
    boolean isPressed() {
        float pollData = component.getPollData();
        return matcher.test(pollData);
    }
    
    /** {@inheritDoc} */
    @Override
    public String toString() {
        String result;
        
        if (component.getIdentifier() == Axis.POV) {
            // d-pad
            if (matcher.test(POV.UP)) {
                result = "D-pad up";
            } else if (matcher.test(POV.UP_RIGHT)) {
                result = "D-pad up-right";
            } else if (matcher.test(POV.RIGHT)) {
                result = "D-pad right";
            } else if (matcher.test(POV.DOWN_RIGHT)) {
                result = "D-pad down-right";
            } else if (matcher.test(POV.DOWN)) {
                result = "D-pad down";
            } else if (matcher.test(POV.DOWN_LEFT)) {
                result = "D-pad down-left";
            } else if (matcher.test(POV.LEFT)) {
                result = "D-pad left";
            } else if (matcher.test(POV.UP_LEFT)) {
                result = "D-pad up-left";
            } else {
                // unknown value
                result = "D-pad";
            }
        } else {
            result = component.getName();
        }
        return result;
    }
    
}
