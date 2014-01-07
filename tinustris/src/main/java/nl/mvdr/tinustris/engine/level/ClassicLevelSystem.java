package nl.mvdr.tinustris.engine.level;

import lombok.RequiredArgsConstructor;
import nl.mvdr.tinustris.model.GameState;

/**
 * Classic leveling system as seen in most Tetris games.
 * 
 * @author Martijn van de Rijdt
 */
@RequiredArgsConstructor
public class ClassicLevelSystem extends AbstractLevelSystem {
    /** Starting level. */
    private final int startLevel;
    
    /** Constructor. */
    public ClassicLevelSystem() {
        this(0);
    }
    
    /** {@inheritDoc} */
    @Override
    int computeLevel(GameState previousState, GameState newState) {
        return Math.max(startLevel, newState.getLines() / 10);
    }
}
