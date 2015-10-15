package nl.mvdr.game.jinput;

import java.util.Map;
import java.util.Set;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import net.java.games.input.Controller;

/**
 * Configuration for {@link JInputController}.
 * 
 * @param <S> enum type containing all possible inputs from the user
 * 
 * @author Martijn van de Rijdt
 */
@RequiredArgsConstructor
@Getter
@ToString
public class JInputControllerConfiguration<S extends Enum<S>> {
    /**
     * Key / button mapping. Not every input needs to be mapped, which is to say: values in this map may be empty sets;
     * if an input is not mapped it will simply never get pressed. The mapping should contain values for all valid keys.
     */
    @NonNull
    private final Map<S, Set<InputMapping>> mapping;

    /** All relevant controllers. All of the components in {@link #mapping} must belong to one of these controllers. */
    @NonNull
    private final Set<Controller> controllers;
}
