package nl.mvdr.tinustris.engine.level;

import lombok.RequiredArgsConstructor;
import nl.mvdr.tinustris.model.OnePlayerGameState;

/**
 * Dummy implementation of LevelSystem.
 * 
 * @author Martijn van de Rijdt
 */
@RequiredArgsConstructor
public class DummyLevelSystem implements LevelSystem {
    /** Level value. */
    private final int level;
    
    /** Constructor. */
    public DummyLevelSystem() {
        this(0);
    }
    
    /** {@inheritDoc} */
    @Override
    public int computeLevel(OnePlayerGameState previousState, OnePlayerGameState newState) {
        return level;
    }
}
