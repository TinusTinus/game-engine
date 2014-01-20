package nl.mvdr.tinustris.engine.speedcurve;

import java.util.TreeMap;

import nl.mvdr.tinustris.model.OnePlayerGameState;

/**
 * Speed curve based on Tetris: The Grand Master.
 * 
 * @author Martijn van de Rijdt
 */
public class TheGrandMaster2MasterSpeedCurve implements SpeedCurve {
    /** Internal gravity curve. */
    private final RangedCurve internalGravityCurve;
    /** Lock delay curve/ */
    private final RangedCurve lockDelayCurve;
    /** ARE curve. */
    private final RangedCurve areCurve;
    /** Line curve. */
    private final RangedCurve lineClearCurve;

    /** Constructor. */
    @SuppressWarnings("serial") // maps aren't to be serialised
    public TheGrandMaster2MasterSpeedCurve() {
        super();
        
        this.internalGravityCurve = new RangedCurve(new TreeMap<Integer, Integer>() {{
            put(Integer.valueOf(0), Integer.valueOf(4));
            put(Integer.valueOf(30), Integer.valueOf(6));
            put(Integer.valueOf(35), Integer.valueOf(8));
            put(Integer.valueOf(40), Integer.valueOf(10));
            put(Integer.valueOf(50), Integer.valueOf(12));
            put(Integer.valueOf(60), Integer.valueOf(16));
            put(Integer.valueOf(70), Integer.valueOf(32));
            put(Integer.valueOf(80), Integer.valueOf(48));
            put(Integer.valueOf(90), Integer.valueOf(64));
            put(Integer.valueOf(100), Integer.valueOf(80));
            put(Integer.valueOf(120), Integer.valueOf(96));
            put(Integer.valueOf(140), Integer.valueOf(112));
            put(Integer.valueOf(160), Integer.valueOf(128));
            put(Integer.valueOf(170), Integer.valueOf(144));
            put(Integer.valueOf(200), Integer.valueOf(4));
            put(Integer.valueOf(220), Integer.valueOf(32));
            put(Integer.valueOf(230), Integer.valueOf(64));
            put(Integer.valueOf(233), Integer.valueOf(96));
            put(Integer.valueOf(236), Integer.valueOf(128));
            put(Integer.valueOf(239), Integer.valueOf(160));
            put(Integer.valueOf(243), Integer.valueOf(192));
            put(Integer.valueOf(247), Integer.valueOf(224));
            put(Integer.valueOf(251), Integer.valueOf(256));  //  1G
            put(Integer.valueOf(300), Integer.valueOf(512));  //  2G
            put(Integer.valueOf(330), Integer.valueOf(768));  //  3G
            put(Integer.valueOf(360), Integer.valueOf(1024)); //  4G
            put(Integer.valueOf(400), Integer.valueOf(1280)); //  5G
            put(Integer.valueOf(420), Integer.valueOf(1024)); //  4G
            put(Integer.valueOf(450), Integer.valueOf(768));  //  3G
            put(Integer.valueOf(500), Integer.valueOf(5120)); // 20G
        }});
        
        this.lockDelayCurve = new RangedCurve(new TreeMap<Integer, Integer>() {{
            put(Integer.valueOf(0), Integer.valueOf(30));
            put(Integer.valueOf(900), Integer.valueOf(17));
        }});

        this.areCurve = new RangedCurve(new TreeMap<Integer, Integer>() {{
            put(Integer.valueOf(0), Integer.valueOf(25));
            put(Integer.valueOf(700), Integer.valueOf(16));
            put(Integer.valueOf(800), Integer.valueOf(12));
        }});
        
        this.lineClearCurve = new RangedCurve(new TreeMap<Integer, Integer>() {{
            put(Integer.valueOf(0), Integer.valueOf(40));
            put(Integer.valueOf(500), Integer.valueOf(25));
            put(Integer.valueOf(600), Integer.valueOf(16));
            put(Integer.valueOf(700), Integer.valueOf(12));
            put(Integer.valueOf(800), Integer.valueOf(6));
        }});
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
        int level = state.getLevel();
        return lockDelayCurve.getValue(level);
    }

    /** {@inheritDoc} */
    @Override
    public int computeARE(OnePlayerGameState state) {
        int level = state.getLevel();
        return areCurve.getValue(level);
    }

    /** {@inheritDoc} */
    @Override
    public int computeLineClearDelay(OnePlayerGameState state) {
        int level = state.getLevel();
        return lineClearCurve.getValue(level);
    }
}
