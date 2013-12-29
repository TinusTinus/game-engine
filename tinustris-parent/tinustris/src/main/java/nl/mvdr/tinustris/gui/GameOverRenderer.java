package nl.mvdr.tinustris.gui;

import javafx.application.Platform;
import nl.mvdr.tinustris.model.GameState;

/**
 * Component showing whether the game is over. Should be part of an invisible group by default.
 * 
 * @author Martijn van de Rijdt
 */
class GameOverRenderer extends GreenTextLabel implements GameRenderer {
    /** Constructor. */
    GameOverRenderer() {
        super("GAME OVER");
    }
    
    /** {@inheritDoc} */
    @Override
    public void render(GameState gameState) {
        if (!getParent().isVisible() && gameState.isTopped()) {
            runOnJavaFXThread(new Runnable() {
                /** {@inheritDoc} */
                @Override
                public void run() {
                    getParent().setVisible(true);
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
}
