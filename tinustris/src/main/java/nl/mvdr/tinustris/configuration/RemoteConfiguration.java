package nl.mvdr.tinustris.configuration;

import lombok.EqualsAndHashCode;
import lombok.Getter;
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
    /** Hostname. */
    private final String host;
    /** Port number. */
    private final int port;
}
