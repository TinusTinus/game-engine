package nl.mvdr.tinustris.gui;

import nl.mvdr.tinustris.model.GameState;

/**
 * Component showing the current level.
 * 
 * @author Martijn van de Rijdt
 */
class LevelRenderer extends LabelRenderer {
    /** {@inheritDoc} */
    @Override
    protected String toText(GameState state) {
        return "" + state.computeLevel();
    }
}
