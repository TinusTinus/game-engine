package nl.mvdr.tinustris.gui;

import javafx.scene.Node;
import nl.mvdr.tinustris.model.GameState;

/**
 * Responsible for rendering the game in a JavaFX component.
 * 
 * @param <T>
 *            JavaFX node type onto which the game state should be rendered
 * 
 * @author Martijn van de Rijdt
 */
public interface GameRenderer<T extends Node> {
    /**
     * Renders the game state onto the given JavaFX component.
     * 
     * @param node
     *            JavaFX node which will display the game state
     * @param gameState
     *            game state to be displayed
     */
    void render(T node, GameState gameState);
}
