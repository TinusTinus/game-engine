package nl.mvdr.tinustris.configuration;

import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import nl.mvdr.tinustris.gui.GraphicsStyle;

/**
 * Implementation of {@link Configuration}.
 * 
 * @author Martijn van de Rijdt
 */
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
@Getter
public class ConfigurationImpl implements Configuration {
    @NonNull
    private List<PlayerConfiguration> playerConfigurations;
    /** Graphical style for the blocks in the grid. */
    @NonNull
    private final GraphicsStyle graphicsStyle;
    /** Behavior. */
    @NonNull
    private final Behavior behavior;
    /** Starting level. */
    private final int startLevel;
}
