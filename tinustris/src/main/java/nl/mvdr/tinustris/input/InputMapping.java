package nl.mvdr.tinustris.input;

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
    /** The value the component must have in order to count as pressed.. */
    private final float pressedValue;
    
    /** @return whether the input is pressed */
    boolean isPressed() {
        return component.getPollData() == pressedValue;
    }
    
    /** {@inheritDoc} */
    @Override
    public String toString() {
        String result;
        
        if (component.getIdentifier() == Axis.POV) {
            // d-pad
            if (pressedValue == POV.UP) {
                result = "D-pad up";
            } else if (pressedValue == POV.UP_RIGHT) {
                result = "D-pad up-right";
            } else if (pressedValue == POV.RIGHT) {
                result = "D-pad right";
            } else if (pressedValue == POV.DOWN_RIGHT) {
                result = "D-pad down-right";
            } else if (pressedValue == POV.DOWN) {
                result = "D-pad down";
            } else if (pressedValue == POV.DOWN_LEFT) {
                result = "D-pad down-left";
            } else if (pressedValue == POV.LEFT) {
                result = "D-pad left";
            } else if (pressedValue == POV.UP_LEFT) {
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
