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
public class ConfigurationImpl implements Configuration {
    @Getter
    @NonNull
    private List<PlayerConfiguration> playerConfigurations;
    /** Graphical style for the blocks in the grid. */
    @Getter
    @NonNull
    private final GraphicsStyle graphicsStyle;
    /** Behavior. */
    @Getter
    @NonNull
    private final Behavior behavior;
    /** Starting level. */
    @Getter
    private final int startLevel;
}
