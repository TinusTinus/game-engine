package nl.mvdr.tinustris.configuration;

import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import nl.mvdr.tinustris.gui.GraphicsStyle;
import nl.mvdr.tinustris.input.JInputControllerConfiguration;

/**
 * Implementation of {@link Configuration}.
 * 
 * @author Martijn van de Rijdt
 */
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public class ConfigurationImpl implements Configuration {
    /** Number of players in the game. Is at least 1. */
    @Getter
    private final int numberOfPlayers;
    /** Graphical style for the blocks in the grid. */
    @Getter
    @NonNull
    private final GraphicsStyle graphicsStyle;
    /** Configuration for the JInutController. */
    @NonNull
    private final List<JInputControllerConfiguration> jInputControllerConfigurations;
    /** Behavior. */
    @Getter
    @NonNull
    private final Behavior behavior;
    /** Starting level. */
    @Getter
    private final int startLevel;
    
    /** {@inheritDoc} */
    @Override
    public JInputControllerConfiguration getJInputControllerConfiguration(int i) {
        return this.jInputControllerConfigurations.get(i);
    }
}
