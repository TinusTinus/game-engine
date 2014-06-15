package nl.mvdr.tinustris.configuration;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import nl.mvdr.tinustris.input.JInputControllerConfiguration;

/**
 * Implementation of {@link PlayerConfiguration}.
 * 
 * @author Martijn van de Rijdt
 */
@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public class LocalPlayerConfigurationImpl implements LocalPlayerConfiguration {
    /** Player name. */
    @NonNull
    private final String name;
    /** Configuration for the JInputController for this player. */
    @NonNull
    private final JInputControllerConfiguration jInputControllerConfiguration;
}
