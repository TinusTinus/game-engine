package nl.mvdr.tinustris.engine.speedcurve;

import java.util.SortedMap;
import java.util.TreeMap;

import nl.mvdr.tinustris.model.GameState;

/**
 * Speed curve based on the original Tetris for the Nintendo Game Boy.
 * 
 * @author Martijn van de Rijdt
 */
public class TinustrisSpeedCurve implements SpeedCurve {
    /** Internal gravity curve. */
    private final RangedCurve internalGravityCurve;
    
    /** Constructor. */
    public TinustrisSpeedCurve() {
        super();
        
        @SuppressWarnings("serial") // not to be serialised
        SortedMap<Integer, Integer> map = new TreeMap<Integer, Integer>() {{
            put(Integer.valueOf(0), Integer.valueOf(5));
            put(Integer.valueOf(1), Integer.valueOf(6));
            put(Integer.valueOf(2), Integer.valueOf(7));
            put(Integer.valueOf(3), Integer.valueOf(8));
            put(Integer.valueOf(4), Integer.valueOf(9));
            put(Integer.valueOf(5), Integer.valueOf(11));
            put(Integer.valueOf(6), Integer.valueOf(14));
            put(Integer.valueOf(7), Integer.valueOf(20));
            put(Integer.valueOf(8), Integer.valueOf(32));
            put(Integer.valueOf(9), Integer.valueOf(43));
            put(Integer.valueOf(10), Integer.valueOf(51));
            put(Integer.valueOf(13), Integer.valueOf(64));
            put(Integer.valueOf(16), Integer.valueOf(85));
            put(Integer.valueOf(19), Integer.valueOf(128));
            put(Integer.valueOf(29), Integer.valueOf(256));  //  1G
            put(Integer.valueOf(34), Integer.valueOf(512));  //  2G
            put(Integer.valueOf(39), Integer.valueOf(768));  //  3G
            put(Integer.valueOf(44), Integer.valueOf(1024)); //  4G
            put(Integer.valueOf(49), Integer.valueOf(1280)); //  5G
            put(Integer.valueOf(54), Integer.valueOf(1024)); //  4G
            put(Integer.valueOf(59), Integer.valueOf(768));  //  3G
            put(Integer.valueOf(64), Integer.valueOf(5120)); // 20G
        }};
        this.internalGravityCurve = new RangedCurve(map);
    }
    
    /** {@inheritDoc} */
    @Override
    public int computeInternalGravity(GameState state) {
        int level = state.getLevel();
        return internalGravityCurve.getValue(level);
    }

    /** {@inheritDoc} */
    @Override
    public int computeLockDelay(GameState state) {
        return Math.max(30, 256 / computeInternalGravity(state));
    }

    /** {@inheritDoc} */
    @Override
    public int computeARE(GameState state) {
        return 10;
    }

    /** {@inheritDoc} */
    @Override
    public int computeLineClearDelay(GameState state) {
        return 30;
    }
}
