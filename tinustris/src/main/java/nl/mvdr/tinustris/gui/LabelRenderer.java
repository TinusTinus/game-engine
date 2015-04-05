package nl.mvdr.tinustris.gui;

import javafx.application.Platform;
import lombok.NonNull;
import nl.mvdr.game.gui.GameRenderer;
import nl.mvdr.game.state.GameState;

/**
 * Label containing (part of) the game state.
 * 
 * @param <S> game state type
 * 
 * @author Martijn van de Rijdt
 */
abstract class LabelRenderer<S extends GameState> extends GreenTextLabel implements GameRenderer<S> {
    /** {@inheritDoc} */
    @Override
    public void render(@NonNull S gameState) {
        final String newText = toText(gameState);
        if (!newText.equals(getText())) {
            runOnJavaFXThread(() -> setText(newText));
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
    protected abstract String toText(S state);
}
