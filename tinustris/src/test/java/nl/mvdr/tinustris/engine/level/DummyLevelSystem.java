package nl.mvdr.tinustris.engine.level;

import lombok.RequiredArgsConstructor;
import nl.mvdr.tinustris.model.GameState;

/**
 * Dummy subclass of AbstractLevelSystem.
 * 
 * @author Martijn van de Rijdt
 */
@RequiredArgsConstructor
public class DummyLevelSystem extends AbstractLevelSystem {
    /** Level value. */
    private final int level;
    
    /** Constructor. */
    public DummyLevelSystem() {
        this(0);
    }
    
    /** {@inheritDoc} */
    @Override
    int computeLevel(GameState previousState, GameState newState) {
        return level;
    }
}
