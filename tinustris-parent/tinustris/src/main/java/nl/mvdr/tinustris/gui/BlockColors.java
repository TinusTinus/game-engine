package nl.mvdr.tinustris.gui;

import javafx.scene.paint.Color;
import nl.mvdr.tinustris.model.Tetromino;

/**
 * Contains the color for each block.
 * 
 * @author Martijn van de Rijdt
 */
class BlockColors {
    /**
     * Retrieves the color for the given tetromino.
     * 
     * @param tetromino tetromino
     * @return color
     */
    static Color getColor(Tetromino tetromino) {
        Color result;
        if (tetromino == Tetromino.I) {
            result = Color.CYAN;
        } else if (tetromino == Tetromino.O) {
            result = Color.YELLOW;
        } else if (tetromino == Tetromino.T) {
            result = Color.PURPLE;
        } else if (tetromino == Tetromino.S) {
            result = Color.GREEN;
        } else if (tetromino == Tetromino.Z) {
            result = Color.RED;
        } else if (tetromino == Tetromino.J) {
            result = Color.BLUE;
        } else if (tetromino == Tetromino.L) {
            result = Color.ORANGE;
        } else {
            throw new IllegalArgumentException("Unexpected Tetromino: " + tetromino);
        }
        return result;
    }
}
