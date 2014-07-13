package nl.mvdr.tinustris.configuration;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

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
    /** Open stream for outputting information. */
    @NonNull
    private final ObjectOutputStream outputStream;
    /** Open stream for reading information. */
    // TODO @NonNull
    private final ObjectInputStream inputStream;
}
