package nl.mvdr.tinustris.input;

import java.util.Set;
import java.util.stream.Collectors;

import nl.mvdr.game.input.InputMapping;
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
    
    /** @return formatted version of the input mapping */
    public String getMappingFormatted() {
        Set<String> descriptions = mapping.stream()
            .map(InputMapping::toString)
            .collect(Collectors.toSet());
        return String.join(", ", descriptions);
    }
}
