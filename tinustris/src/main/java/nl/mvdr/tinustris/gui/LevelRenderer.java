package nl.mvdr.tinustris.gui;

import nl.mvdr.tinustris.model.OnePlayerGameState;

/**
 * Component showing the current level.
 * 
 * @author Martijn van de Rijdt
 */
class LevelRenderer extends LabelRenderer<OnePlayerGameState> {
    /** {@inheritDoc} */
    @Override
    protected String toText(OnePlayerGameState state) {
        return "" + state.getLevel();
    }
}
