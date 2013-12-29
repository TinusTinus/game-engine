package nl.mvdr.tinustris.gui;

import javafx.application.Platform;
import javafx.scene.control.Label;
import lombok.NonNull;
import nl.mvdr.tinustris.model.GameState;

/**
 * Label containing (part of) the game state.
 * 
 * @author Martijn van de Rijdt
 */
abstract class LabelRenderer extends Label implements GameRenderer {
    /** {@inheritDoc} */
    @Override
    public void render(@NonNull GameState gameState) {
        final String newText = toText(gameState);
        if (!newText.equals(getText())) {
            runOnJavaFXThread(new Runnable() {
                /** {@inheritDoc} */
                @Override
                public void run() {
                    setText(newText);
                }
            });
        }
    }
    
    /**
     * Runs the given runnable on the JavaFX thread.
     * 
     * @param runnable runnable
     */
    // default visibility as an extension point for unit tests
    void runOnJavaFXThread(Runnable runnable) {
        Platform.runLater(runnable);
    }
    
    /**
     * Creates the text for this label based on the given game state.
     * 
     * @param state game state to be represented
     * @return string representation of the given state; may not be null
     */
    protected abstract String toText(GameState state);
}
