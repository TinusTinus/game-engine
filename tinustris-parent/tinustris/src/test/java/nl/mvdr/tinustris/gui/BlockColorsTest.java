package nl.mvdr.tinustris.gui;

import javafx.scene.paint.Color;
import lombok.extern.slf4j.Slf4j;
import nl.mvdr.tinustris.model.Tetromino;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test class for {@link BlockColors}.
 * 
 * @author Martijn van de Rijdt
 */
@Slf4j
public class BlockColorsTest {
    /** Tests whether all tetrominoes have an associated color. */
    @Test
    public void testAllTetrominoesMapped() {
        for (Tetromino tetromino: Tetromino.values()) {
            Color color = BlockColors.getColor(tetromino);
            
            log.info("Color for {}: {}.", tetromino, color);
            Assert.assertNotNull(color);
        }
    }
}
