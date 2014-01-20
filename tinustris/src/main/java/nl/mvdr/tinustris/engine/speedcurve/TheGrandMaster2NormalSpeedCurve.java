package nl.mvdr.tinustris.engine.speedcurve;

import java.util.SortedMap;
import java.util.TreeMap;

import nl.mvdr.tinustris.model.OnePlayerGameState;

/**
 * Speed curve based on Tetris: The Grand Master.
 * 
 * @author Martijn van de Rijdt
 */
public class TheGrandMaster2NormalSpeedCurve implements SpeedCurve {
    /** Internal gravity curve. */
    private final RangedCurve internalGravityCurve;

    /** Constructor. */
    public TheGrandMaster2NormalSpeedCurve() {
        super();
        
        @SuppressWarnings("serial") // not to be serialised
        SortedMap<Integer, Integer> map = new TreeMap<Integer, Integer>() {{
            put(Integer.valueOf(0), Integer.valueOf(4));
            put(Integer.valueOf(8), Integer.valueOf(5));
            put(Integer.valueOf(19), Integer.valueOf(6));
            put(Integer.valueOf(35), Integer.valueOf(8));
            put(Integer.valueOf(40), Integer.valueOf(10));
            put(Integer.valueOf(50), Integer.valueOf(12));
            put(Integer.valueOf(60), Integer.valueOf(16));
            put(Integer.valueOf(70), Integer.valueOf(32));
            put(Integer.valueOf(80), Integer.valueOf(48));
            put(Integer.valueOf(90), Integer.valueOf(64));
            put(Integer.valueOf(100), Integer.valueOf(4));
            put(Integer.valueOf(108), Integer.valueOf(5));
            put(Integer.valueOf(119), Integer.valueOf(6));
            put(Integer.valueOf(125), Integer.valueOf(8));
            put(Integer.valueOf(131), Integer.valueOf(12));
            put(Integer.valueOf(139), Integer.valueOf(32));
            put(Integer.valueOf(149), Integer.valueOf(48));
            put(Integer.valueOf(156), Integer.valueOf(80));
            put(Integer.valueOf(164), Integer.valueOf(112));
            put(Integer.valueOf(174), Integer.valueOf(128));
            put(Integer.valueOf(180), Integer.valueOf(144));
            put(Integer.valueOf(200), Integer.valueOf(16));
            put(Integer.valueOf(212), Integer.valueOf(48));
            put(Integer.valueOf(221), Integer.valueOf(80));
            put(Integer.valueOf(232), Integer.valueOf(112));
            put(Integer.valueOf(244), Integer.valueOf(144));
            put(Integer.valueOf(256), Integer.valueOf(176));
            put(Integer.valueOf(267), Integer.valueOf(192));
            put(Integer.valueOf(277), Integer.valueOf(208));
            put(Integer.valueOf(287), Integer.valueOf(224));
            put(Integer.valueOf(295), Integer.valueOf(240));
            put(Integer.valueOf(300), Integer.valueOf(5120)); // 20G
        }};
        this.internalGravityCurve = new RangedCurve(map);
    }
    
    /** {@inheritDoc} */
    @Override
    public int computeInternalGravity(OnePlayerGameState state) {
        int level = state.getLevel();
        return internalGravityCurve.getValue(level);
    }

    /** {@inheritDoc} */
    @Override
    public int computeLockDelay(OnePlayerGameState state) {
        return 30;
    }

    /** {@inheritDoc} */
    @Override
    public int computeARE(OnePlayerGameState state) {
        return 25;
    }

    /** {@inheritDoc} */
    @Override
    public int computeLineClearDelay(OnePlayerGameState state) {
        return 40;
    }
}
