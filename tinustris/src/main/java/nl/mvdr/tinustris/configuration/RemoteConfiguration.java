package nl.mvdr.tinustris.configuration;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Optional;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * Configuration for a remote instance of the application.
 * 
 * @author Martijn van de Rijdt
 */
@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public class RemoteConfiguration {
    /**
     * Open stream for outputting information. Only required if the local game instance actually has active players. In
     * other words, this field can remain empty if this game instance is only for spectating.
     */
    @NonNull
    private final Optional<ObjectOutputStream> outputStream;
    /**
     * Open stream for reading information. Only required if this remote game instance actually has active players. In
     * other words, this field can remain empty for remote spectators.
     */
    @NonNull
    private final Optional<ObjectInputStream> inputStream;
}
