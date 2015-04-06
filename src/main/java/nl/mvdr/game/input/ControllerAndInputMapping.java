package nl.mvdr.game.input;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import net.java.games.input.Controller;

/**
 * Container class, for a controller and an input mapping.
 * 
 * @author Martijn van de Rijdt
 */
@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public class ControllerAndInputMapping {
    /** Controller. */
    @NonNull
    private final Controller controller;
    /** Input mapping. */
    @NonNull
    private final InputMapping mapping;
}
