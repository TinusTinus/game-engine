package nl.mvdr.tinustris.gui;

import nl.mvdr.tinustris.model.OnePlayerGameState;

/**
 * Component showing the number of lines.
 * 
 * @author Martijn van de Rijdt
 */
class LinesRenderer extends LabelRenderer<OnePlayerGameState> {
    /** {@inheritDoc} */
    @Override
    protected String toText(OnePlayerGameState state) {
        return "" + state.getLines();
    }
}
