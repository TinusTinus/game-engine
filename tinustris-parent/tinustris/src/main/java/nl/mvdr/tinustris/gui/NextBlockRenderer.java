package nl.mvdr.tinustris.gui;

import nl.mvdr.tinustris.model.GameState;

/**
 * Shows the upcoming block.
 * 
 * @author Martijn van de Rijdt
 */
// TODO have this be a graphical view of the next block rather than a text label!
class NextBlockRenderer extends LabelRenderer implements GameRenderer {
    /** {@inheritDoc} */
    @Override
    protected String toText(GameState state) {
        return state.getNextBlock().toString();
    }
}
