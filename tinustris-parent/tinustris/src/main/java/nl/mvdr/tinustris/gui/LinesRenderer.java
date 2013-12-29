package nl.mvdr.tinustris.gui;

import nl.mvdr.tinustris.model.GameState;

/**
 * Component showing the number of lines.
 * 
 * @author Martijn van de Rijdt
 */
class LinesRenderer extends LabelRenderer {
    /** {@inheritDoc} */
    @Override
    protected String toText(GameState state) {
        return "" + state.getLines();
    }
}
