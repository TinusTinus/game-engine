package nl.mvdr.tinustris.input;

import lombok.AccessLevel;
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
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
@Getter(AccessLevel.PACKAGE)
@ToString
@EqualsAndHashCode
class ControllerAndInputMapping {
    /** Controller. */
    @NonNull
    private final Controller controller;
    /** Input mapping. */
    @NonNull
    private final InputMapping mapping;
}
