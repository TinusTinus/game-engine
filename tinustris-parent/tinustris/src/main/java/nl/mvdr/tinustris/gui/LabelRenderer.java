package nl.mvdr.tinustris.gui;

import javafx.application.Platform;
import javafx.scene.control.Label;
import nl.mvdr.tinustris.model.GameState;

/**
 * Implementation of GameRenderer which renders the game state as ASCII art into a given label.
 * 
 * @author Martijn van de Rijdt
 */
public class LabelRenderer implements GameRenderer<Label> {
    /**
     * Renders the game state in the given label
     * 
     * @param label
     *            the label which will display the game state as ASCII art; should have a monospaced font
     * @param gameState
     *            game state to be displayed
     */
    @Override
    public void render(final Label label, GameState gameState) {
        final String text = gameState.toString();
        Platform.runLater(new Runnable() {
            /** {@inheritDoc} */
            @Override
            public void run() {
                label.setText(text);
            }
        });
    }
}
