package nl.mvdr.tinustris.engine.level;

import nl.mvdr.tinustris.model.GameState;

/**
 * Determines the leveling system for a game.
 * 
 * @author Martijn van de Rijdt
 */
public interface LevelSystem {
    /**
     * Computes the new level value and fills it in.
     * 
     * @param previousState previous game state
     * @param newState next game state; all fields should be filled except for level
     * @return copy of newState with the level filled in
     */
    GameState fillLevel(GameState previousState, GameState newState);
}
