package nl.mvdr.tinustris.engine;

import nl.mvdr.tinustris.model.Tetromino;

/**
 * Used to select the next tetromino to be dropped in a game of Tetris.
 * 
 * @author Martijn van de Rijdt
 */
@FunctionalInterface
interface TetrominoGenerator {
    /**
     * Returns tetromino i for a game of Tetris.
     * 
     * If get is invoked multiple times with the same index, the same tetromino is returned.
     * 
     * @param i
     *            index of the tetromino; must be at least 0
     * @return tetromino
     */
    Tetromino get(int i);
}
