package nl.mvdr.tinustris.gui;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * Label with light green, fairly large text.
 * 
 * @author Martijn van de Rijdt
 */
public class GreenTextLabel extends Label {
    /** Font size. */
    private static final int FONT_SIZE = 30;

    /** Constructor. */
    GreenTextLabel() {
        super();

        setFont(new Font(FONT_SIZE));
        setTextFill(Color.LIGHTGREEN);
    }
    
    /**
     * Constructor.
     * 
     * @param text
     *            initial text
     */
    GreenTextLabel(String text) {
        super(text);

        setFont(new Font(FONT_SIZE));
        setTextFill(Color.LIGHTGREEN);
    }
}
