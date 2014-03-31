package nl.mvdr.tinustris.input;

import java.util.Set;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * Container holding an input and its mapping.
 * 
 * @author Martijn van de Rijdt
 */
@Getter
@ToString
@RequiredArgsConstructor
public class InputAndMapping {
    /** Input. */
    @NonNull
    private final Input input;
    /** Mapping. */
    @NonNull
    private final Set<InputMapping> mapping;
}
