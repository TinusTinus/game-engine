package nl.mvdr.tinustris.configuration;

import java.util.function.Function;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import nl.mvdr.tinustris.engine.level.ClassicLevelSystem;
import nl.mvdr.tinustris.engine.level.LevelSystem;
import nl.mvdr.tinustris.engine.level.TheGrandMasterLevelSystem;
import nl.mvdr.tinustris.engine.speedcurve.GameBoySpeedCurve;
import nl.mvdr.tinustris.engine.speedcurve.NESSpeedCurve;
import nl.mvdr.tinustris.engine.speedcurve.SpeedCurve;
import nl.mvdr.tinustris.engine.speedcurve.TheGrandMaster2MasterSpeedCurve;
import nl.mvdr.tinustris.engine.speedcurve.TheGrandMaster2NormalSpeedCurve;
import nl.mvdr.tinustris.engine.speedcurve.TheGrandMasterSpeedCurve;
import nl.mvdr.tinustris.engine.speedcurve.TinustrisSpeedCurve;

/**
 * Spcifies the behavior of the game. This includes things like gravity, lock delay and the wa levels work.
 * 
 * @author Martijn van de Rijdt
 */
@RequiredArgsConstructor
public enum Behavior {
    /** Behavior inspired by Game Boy Tetris. */
    GAME_BOY(startLevel -> new ClassicLevelSystem(startLevel), true, new GameBoySpeedCurve()),
    /** Behavior inspired by NES Tetris. */
    NES(startLevel -> new ClassicLevelSystem(startLevel), true, new NESSpeedCurve()),
    /** Behavior inspired by Tetris: The Grandmaster. */
    THE_GRANDMASTER(startLevel -> new TheGrandMasterLevelSystem(), true, new TheGrandMasterSpeedCurve()),
    /** Behavior inspired by Tetris: The Grandmaster 2, Normal Mode. */
    THE_GRANDMASTER_2_NORMAL(startLevel -> new TheGrandMasterLevelSystem(), false, new TheGrandMaster2NormalSpeedCurve()),
    /** Behavior inspired by Tetris: The Grandmaster 2, Master Mode. */
    THE_GRANDMASTER_2_MASTER(startLevel -> new TheGrandMasterLevelSystem(), false, new TheGrandMaster2MasterSpeedCurve()),
    /** Custom behavior. */
    TINUSTRIS(startLevel -> new ClassicLevelSystem(startLevel), true, new TinustrisSpeedCurve());

    /** Factory which can create snew LevelSystem instances. */
    private final Function<Integer, LevelSystem> levelSystemFactory;
    /** Indicates whether the level system supports a starting level. */
    @Getter
    private final boolean startLevelSupported;
    /** Speed curve. */
    @Getter
    private final SpeedCurve speedCurve;
    
    /** @return default value */
    public static Behavior defaultBehavior() {
        return TINUSTRIS;
    }
    
    /**
     * Creates a new level system. Note that some level systems do not support starting levels. For these, the
     * startLevel parameter is ignored.
     * 
     * @param startLevel starting level
     * @return level system
     */
    public LevelSystem createLevelSystem(int startLevel) {
        return levelSystemFactory.apply(startLevel);
    }
    
    /**
     * Creates a new level system. If the level system supports a start level, it is initialised at 0.
     * 
     * @param startLevel starting level
     * @return level system
     */
    public LevelSystem createLevelSystem() {
        return this.createLevelSystem(0);
    }
}
