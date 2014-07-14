package nl.mvdr.tinustris.gui;

import javafx.application.Platform;
import nl.mvdr.tinustris.model.OnePlayerGameState;

/**
 * Component showing whether the game is over. Should be part of an invisible group by default.
 * 
 * @author Martijn van de Rijdt
 */
class GameOverRenderer extends GreenTextLabel implements GameRenderer<OnePlayerGameState> {
    /** Constructor. */
    GameOverRenderer() {
        super("GAME OVER");
    }
    
    /** {@inheritDoc} */
    @Override
    public void render(OnePlayerGameState gameState) {
        if (getParent().isVisible() != gameState.isTopped()) {
            runOnJavaFXThread(() -> getParent().setVisible(gameState.isTopped()));
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
}
