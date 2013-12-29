package nl.mvdr.tinustris.gui;

import javafx.scene.text.TextAlignment;
import nl.mvdr.tinustris.model.GameState;

/**
 * Component showing the number of lines.
 * 
 * @author Martijn van de Rijdt
 */
class LinesRenderer extends LabelRenderer {
    /** Constructor. */
    LinesRenderer() {
        super();
        setTextAlignment(TextAlignment.RIGHT);
    }
    
    /** {@inheritDoc} */
    @Override
    protected String toText(GameState state) {
        return "" + state.getLines();
    }
}
