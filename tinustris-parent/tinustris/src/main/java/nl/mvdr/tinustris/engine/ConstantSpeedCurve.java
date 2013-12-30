package nl.mvdr.tinustris.engine;

import lombok.RequiredArgsConstructor;
import lombok.ToString;
import nl.mvdr.tinustris.model.GameState;

/**
 * A curve where all values are constant.
 * 
 * @author Martijn van de Rijdt
 */
@RequiredArgsConstructor
@ToString
public class ConstantSpeedCurve implements SpeedCurve {
    /** Gravity in 1 / 256 G. */
    private final int gravity;
    /** Lock delay in frames. */
    private final int lockDelay;
    
    /** Convenience constructor. */
    public ConstantSpeedCurve() {
        this(4, 120);
    }
    
    /** {@inheritDoc} */
    @Override
    public int computeInternalGravity(GameState state) {
        return gravity;
    }

    /** {@inheritDoc} */
    @Override
    public int computeLockDelay(GameState state) {
        return lockDelay;
    }
}
