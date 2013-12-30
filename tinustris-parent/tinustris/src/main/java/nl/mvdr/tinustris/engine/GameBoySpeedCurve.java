package nl.mvdr.tinustris.engine;

import nl.mvdr.tinustris.model.GameState;

/**
 * Speed curve based on the original Tetris for the Nintendo Game Boy.
 * 
 * @author Martijn van de Rijdt
 */
public class GameBoySpeedCurve implements SpeedCurve {
    /** {@inheritDoc} */
    @Override
    public int computeInternalGravity(GameState state) {
        // Note: in the actual Gameboy Tetris gravity was expressed as frames per cell rather than as G.
        // The values returned here are an approximation.
        
        int result;
        int level = state.computeLevel();
        // TODO better implementation than this huge elif?
        if (level == 0) {
            // 53 frames per cell
            result = 5;
        } else if (level == 1) {
            // 49 frames per cell
            result = 5;
        } else if (level == 2) {
            // 45 frames per cell
            result = 6;
        } else if (level == 3) {
            // 41 frames per cell
            result = 6;
        } else if (level == 4) {
            // 37 frames per cell
            result = 7;
        } else if (level == 5) {
            // 33 frames per cell
            result = 8;
        } else if (level == 6) {
            // 28 frames per cell
            result = 9;
        } else if (level == 7) {
            // 22 frames per cell
            result = 12;
        } else if (level == 8) {
            // 17 frames per cell
            result = 15;
        } else if (level == 9) {
            // 11 frames per cell
            result = 23;
        } else if (level == 10) {
            // 10 frames per cell
            result = 26;
        } else if (level == 11) {
            // 9 frames per cell
            result = 28;
        } else if (level == 12) {
            // 8 frames per cell
            result = 32;
        } else if (level == 13) {
            // 7 frames per cell
            result = 37;
        } else if (level == 14 || level == 15) {
            // 6 frames per cell
            result = 43;
        } else if (level == 16 || level == 17) {
            // 5 frames per cell
            result = 51;
        } else if (level == 18 || level == 19) {
            // 4 frames per cell
            result = 64;
        } else {
            // level 20 or higher
            // 3 frames per cell
            result = 85;
        }
        
        return result;
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
