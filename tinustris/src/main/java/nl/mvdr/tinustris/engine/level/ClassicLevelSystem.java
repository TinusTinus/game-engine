package nl.mvdr.tinustris.engine.level;

import lombok.RequiredArgsConstructor;
import lombok.ToString;
import nl.mvdr.tinustris.model.OnePlayerGameState;

/**
 * Classic leveling system as seen in most Tetris games.
 * 
 * @author Martijn van de Rijdt
 */
@RequiredArgsConstructor
@ToString
public class ClassicLevelSystem implements LevelSystem {
    /** Starting level. */
    private final int startLevel;
    
    /** Constructor. */
    public ClassicLevelSystem() {
        this(0);
    }
    
    /** {@inheritDoc} */
    @Override
    public int computeLevel(OnePlayerGameState previousState, OnePlayerGameState newState) {
        return Math.max(startLevel, newState.getLines() / 10);
    }
}
