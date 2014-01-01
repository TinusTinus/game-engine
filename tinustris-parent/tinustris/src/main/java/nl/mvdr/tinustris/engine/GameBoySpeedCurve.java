package nl.mvdr.tinustris.engine;

import java.util.SortedMap;
import java.util.TreeMap;

import nl.mvdr.tinustris.model.GameState;

/**
 * Speed curve based on the original Tetris for the Nintendo Game Boy.
 * 
 * @author Martijn van de Rijdt
 */
public class GameBoySpeedCurve implements SpeedCurve {
    /** Internal gravity curve. */
    private RangedCurve internalGravityCurve;
    
    /** Constructor. */
    public GameBoySpeedCurve() {
        super();
        
        @SuppressWarnings("serial") // not to be serialised
        SortedMap<Integer, Integer> map = new TreeMap<Integer, Integer>() {{
            // Note: in the actual Gameboy Tetris gravity was expressed as frames per cell rather than as G.
            // The values returned here are an approximation.
            put(Integer.valueOf(0), Integer.valueOf(5));   // 53 frames per cell
            put(Integer.valueOf(1), Integer.valueOf(5));   // 49 frames per cell
            put(Integer.valueOf(2), Integer.valueOf(6));   // 45 frames per cell
            put(Integer.valueOf(3), Integer.valueOf(6));   // 41 frames per cell
            put(Integer.valueOf(4), Integer.valueOf(7));   // 37 frames per cell
            put(Integer.valueOf(5), Integer.valueOf(8));   // 33 frames per cell
            put(Integer.valueOf(6), Integer.valueOf(9));   // 28 frames per cell
            put(Integer.valueOf(7), Integer.valueOf(12));  // 22 frames per cell
            put(Integer.valueOf(8), Integer.valueOf(15));  // 17 frames per cell
            put(Integer.valueOf(9), Integer.valueOf(23));  // 11 frames per cell
            put(Integer.valueOf(10), Integer.valueOf(26)); // 10 frames per cell
            put(Integer.valueOf(11), Integer.valueOf(28)); //  9 frames per cell
            put(Integer.valueOf(12), Integer.valueOf(32)); //  8 frames per cell
            put(Integer.valueOf(13), Integer.valueOf(37)); //  7 frames per cell
            put(Integer.valueOf(14), Integer.valueOf(43)); //  6 frames per cell
            put(Integer.valueOf(15), Integer.valueOf(43)); //  6 frames per cell
            put(Integer.valueOf(16), Integer.valueOf(51)); //  5 frames per cell
            put(Integer.valueOf(17), Integer.valueOf(51)); //  5 frames per cell
            put(Integer.valueOf(18), Integer.valueOf(64)); //  4 frames per cell
            put(Integer.valueOf(19), Integer.valueOf(64)); //  4 frames per cell
            put(Integer.valueOf(20), Integer.valueOf(85)); //  3 frames per cell
        }};
        this.internalGravityCurve = new RangedCurve(map);
    }
    
    /** {@inheritDoc} */
    @Override
    public int computeInternalGravity(GameState state) {
        return internalGravityCurve.getValue(state.computeLevel());
    }

    /** {@inheritDoc} */
    @Override
    public int computeLockDelay(GameState state) {
        return 256 / computeInternalGravity(state);
    }

    /** {@inheritDoc} */
    @Override
    public int computeARE(GameState state) {
        return 2;
    }

    /** {@inheritDoc} */
    @Override
    public int computeLineClearDelay(GameState state) {
        return 93;
    }
}
