package nl.mvdr.tinustris.configuration;

import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * Implementation of {@link NetcodeConfiguration}.
 * 
 * @author Martijn van de Rijdt
 */
@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public class NetcodeConfigurationImpl implements NetcodeConfiguration {
    /** Configurations for remote instances. */
    private final List<RemoteConfiguration> remotes;
}
