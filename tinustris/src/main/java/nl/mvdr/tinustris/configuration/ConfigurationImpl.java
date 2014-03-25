package nl.mvdr.tinustris.configuration;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import nl.mvdr.tinustris.gui.GraphicsStyle;
import nl.mvdr.tinustris.input.JInputControllerConfiguration;

/**
 * Implementation of {@link Configuration}.
 * 
 * @author Martijn van de Rijdt
 */
@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public class ConfigurationImpl {
    /** Number of players in the game. Is at least 1. */
    private final int numberOfPlayers;
    /** Graphical style for the blocks in the grid. */
    private final GraphicsStyle graphicsStyle;
    /** Configuration for the JInutController. */
    private final JInputControllerConfiguration jInputControllerConfiguration;
    /** Behavior. */
    private final Behavior behavior;
    /** Starting level. */
    private final int startLevel;
}
