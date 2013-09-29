package nl.mvdr.tinustris.gui;

import javafx.application.Platform;
import javafx.scene.control.Label;
import lombok.NonNull;
import nl.mvdr.tinustris.model.GameState;

/**
 * Implementation of GameRenderer which renders the game state as ASCII art into a given label.
 * 
 * @author Martijn van de Rijdt
 */
public class LabelRenderer implements GameRenderer<Label> {
    /**
     * Renders the game state in the given label.
     * 
     * @param label
     *            the label which will display the game state as ASCII art; should have a monospaced font
     * @param gameState
     *            game state to be displayed
     */
    @Override
    public void render(@NonNull final Label label, @NonNull GameState gameState) {
        final String text = gameState.toString();
        if (!text.equals(label.getText())) {
            runOnJavaFXThread(new Runnable() {
                /** {@inheritDoc} */
                @Override
                public void run() {
                    label.setText(text);
                }
            });
        }
    }
    
    /**
     * Runs the given runnable on the JavaFX thread.
     * 
     * @param runnable runnable
     */
    // protected visibility as an extension point for unit tests
    protected void runOnJavaFXThread(Runnable runnable) {
        Platform.runLater(runnable);
    }
}
