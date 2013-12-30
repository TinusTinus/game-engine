package nl.mvdr.tinustris.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test class for {@link GameState}.
 * 
 * @author Martijn van de Rijdt
 */
@Slf4j
public class GameStateTest {
    /** Test case for the default constructor. */
    @Test
    public void testDefaultConstructor() {
        GameState gameState = new GameState();

        log.info(gameState.toString());
        Assert.assertEquals(10, gameState.getWidth());
        Assert.assertEquals(22, gameState.getHeight());
        for (Tetromino block : gameState.getGrid()) {
            Assert.assertNull(block);
        }
        Assert.assertNotNull(gameState.getNextBlock());
    }

    /** Test case for the constructor with custom width and height values. */
    @Test
    public void testWidthHeightConstructor() {
        GameState gameState = new GameState(38, 12);

        log.info(gameState.toString());
        Assert.assertEquals(38, gameState.getWidth());
        Assert.assertEquals(12, gameState.getHeight());
        for (Tetromino block : gameState.getGrid()) {
            Assert.assertNull(block);
        }
        Assert.assertNotNull(gameState.getNextBlock());
    }

    /** Test case for the constructor with custom width and height values. */
    @Test
    public void testWidthHeightConstructorMinimum() {
        GameState gameState = new GameState(4, 6);

        log.info(gameState.toString());
        Assert.assertEquals(4, gameState.getWidth());
        Assert.assertEquals(6, gameState.getHeight());
        for (Tetromino block : gameState.getGrid()) {
            Assert.assertNull(block);
        }
        Assert.assertNotNull(gameState.getNextBlock());
    }

    /** Test case for the constructor with custom width and height values. */
    @Test(expected = IllegalArgumentException.class)
    public void testWidthHeightConstructorNegativeWidth() {
        new GameState(-4, 22);
    }

    /** Test case for the constructor with custom width and height values. */
    @Test(expected = IllegalArgumentException.class)
    public void testWidthHeightConstructorZeroWidth() {
        new GameState(0, 22);
    }

    /** Test case for the constructor with custom width and height values. */
    @Test(expected = IllegalArgumentException.class)
    public void testWidthHeightConstructorTooSmallWidth() {
        new GameState(1, 22);
    }

    /** Test case for the constructor with custom width and height values. */
    @Test(expected = IllegalArgumentException.class)
    public void testWidthHeightConstructorNegativeHeight() {
        new GameState(10, -4);
    }

    /** Test case for the constructor with custom width and height values. */
    @Test(expected = IllegalArgumentException.class)
    public void testWidthHeightConstructorZeroHeight() {
        new GameState(10, 0);
    }

    /** Test case for the constructor with custom width and height values. */
    @Test(expected = IllegalArgumentException.class)
    public void testWidthHeightConstructorTooSmallHeight() {
        new GameState(10, 1);
    }

    /** Test case for the constructor where all required fields are simply passed in. */
    @Test
    public void testConstructor() {
        List<Tetromino> grid = createGrid(64);

        GameState gameState = new GameState(grid, 8, Tetromino.L, Tetromino.Z);

        log.info(gameState.toString());
        Assert.assertEquals(8, gameState.getWidth());
        Assert.assertEquals(8, gameState.getHeight());
        Assert.assertEquals(grid, gameState.getGrid());
        Assert.assertEquals(Tetromino.L, gameState.getCurrentBlock());
        Assert.assertEquals(Tetromino.Z, gameState.getNextBlock());
    }

    /** Test case for the constructor where all required fields are simply passed in. */
    @Test
    public void testConstructorMinimalSize() {
        List<Tetromino> grid = createGrid(24);

        GameState gameState = new GameState(grid, 4, Tetromino.L, Tetromino.Z);

        log.info(gameState.toString());
        Assert.assertEquals(4, gameState.getWidth());
        Assert.assertEquals(6, gameState.getHeight());
        Assert.assertEquals(grid, gameState.getGrid());
        Assert.assertEquals(Tetromino.L, gameState.getCurrentBlock());
        Assert.assertEquals(Tetromino.Z, gameState.getNextBlock());
    }

    /** Test case for the constructor where all required fields are simply passed in. */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorEmptyGrid() {
        new GameState(Collections.<Tetromino> emptyList(), 4, Tetromino.L, Tetromino.Z);
    }

    /** Test case for the constructor where all required fields are simply passed in. */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorNegativeWidth() {
        List<Tetromino> grid = createGrid(16);

        new GameState(grid, -4, Tetromino.L, Tetromino.Z);
    }

    /** Test case for the constructor where all required fields are simply passed in. */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorZeroWidth() {
        List<Tetromino> grid = createGrid(16);

        new GameState(grid, 0, Tetromino.L, Tetromino.Z);
    }

    /** Test case for the constructor where all required fields are simply passed in. */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorTooSmallWidth() {
        List<Tetromino> grid = createGrid(16);

        new GameState(grid, 2, Tetromino.L, Tetromino.Z);
    }

    /** Test case for the constructor where all required fields are simply passed in. */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorTooSmallHeight() {
        List<Tetromino> grid = createGrid(8);

        new GameState(grid, 4, Tetromino.L, Tetromino.Z);
    }

    /** Test case for the constructor where all required fields are simply passed in. */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorNotDivisableByWidth() {
        List<Tetromino> grid = createGrid(65);

        new GameState(grid, 8, Tetromino.L, Tetromino.Z);
    }

    /** Test case where a game state is copied. */
    @Test
    public void testConstructorCopy() {
        GameState gameState0 = new GameState();

        GameState gameState1 = new GameState(gameState0.getGrid(), gameState0.getWidth(), gameState0.getCurrentBlock(),
                gameState0.getCurrentBlockLocation(), gameState0.getCurrentBlockOrientation(),
                gameState0.getNextBlock());

        Assert.assertEquals(gameState0, gameState1);
    }

    /** Test case for the getBlock method. */
    @Test
    public void testGetBlockNoBlocksInGrid() {
        GameState gameState = new GameState();
        log.info(gameState.toString());

        for (int x = 0; x != 10; x++) {
            for (int y = 0; y != 22; y++) {
                Tetromino block = gameState.getBlock(x, y);

                Assert.assertNull(block);
            }
        }
    }

    /** Test case for the getBlock method. */
    @Test
    public void testGetBlock() {
        List<Tetromino> grid = new ArrayList<>(10 * 22);
        while (grid.size() != 10 * 22) {
            grid.add(Tetromino.O);
        }
        grid = Collections.unmodifiableList(grid);
        GameState gameState = new GameState(grid, 10, Tetromino.L, Tetromino.Z);
        log.info(gameState.toString());

        for (int x = 0; x != 10; x++) {
            for (int y = 0; y != 22; y++) {
                Tetromino block = gameState.getBlock(x, y);

                Assert.assertEquals(Tetromino.O, block);
            }
        }
    }

    /** Test case for the getBlock method. */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testGetBlockXTooSmall() {
        GameState gameState = new GameState();
        log.info(gameState.toString());

        gameState.getBlock(-1, 4);
    }

    /** Test case for the getBlock method. */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testGetBlockXTooLarge() {
        GameState gameState = new GameState();
        log.info(gameState.toString());

        gameState.getBlock(35, 4);
    }

    /** Test case for the getBlock method. */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testGetBlockYTooSmall() {
        GameState gameState = new GameState();
        log.info(gameState.toString());

        gameState.getBlock(4, -1);
    }

    /** Test case for the getBlock method. */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testGetBlockYTooLarge() {
        GameState gameState = new GameState();
        log.info(gameState.toString());

        gameState.getBlock(4, 35);
    }

    /** Tests the isTopped method. */
    @Test
    public void testIsToppedEmptyGridNoActiveBlock() {
        GameState gameState = new GameState();
        log.info(gameState.toString());

        Assert.assertFalse(gameState.isTopped());
    }

    /** Tests the isTopped method. */
    @Test
    public void testIsToppedFullGridNoActiveBlock() {
        List<Tetromino> grid = createGrid(220, Tetromino.J);
        GameState gameState = new GameState(grid, 10, null, Tetromino.I);
        log.info(gameState.toString());

        Assert.assertTrue(gameState.isTopped());
    }

    /** Tests the isTopped method. */
    @Test
    public void testIsToppedEmptyGridActiveBlock() {
        List<Tetromino> grid = createEmptyGrid(220);
        GameState gameState = new GameState(grid, 10, Tetromino.J, Tetromino.I);
        log.info(gameState.toString());

        Assert.assertFalse(gameState.isTopped());
    }

    /** Tests the isTopped method. */
    @Test
    public void testIsToppedFullGridActiveBlock() {
        List<Tetromino> grid = createGrid(220, Tetromino.L);
        GameState gameState = new GameState(grid, 10, Tetromino.J, Tetromino.I);
        log.info(gameState.toString());

        Assert.assertTrue(gameState.isTopped());
    }

    /** Tests the isTopped method. */
    @Test
    public void testIsToppedOneBlockOverlap() {
        List<Tetromino> grid = createEmptyGrid(220);
        // add a single block at (2, 2)
        grid.set(2 + 2 * 10, Tetromino.S);
        GameState gameState = new GameState(grid, 10, Tetromino.O, new Point(1, 1), Orientation.getDefault(),
                Tetromino.I);
        log.info(gameState.toString());

        Assert.assertTrue(gameState.isTopped());
    }

    /** Tests the isTopped method. */
    @Test
    public void testIsToppedOneBlockInTopRow() {
        List<Tetromino> grid = createEmptyGrid(220);
        // add a single block in the vanish zone
        grid.set(2 + 21 * 10, Tetromino.Z);
        GameState gameState = new GameState(grid, 10, Tetromino.O, Tetromino.I);
        log.info(gameState.toString());

        Assert.assertTrue(gameState.isTopped());
    }

    /** Tests the isTopped method. */
    @Test
    public void testIsToppedOneBlockInSecondRowFromTop() {
        List<Tetromino> grid = createEmptyGrid(220);
        // add a single block in the vanish zone
        grid.set(2 + 20 * 10, Tetromino.Z);
        GameState gameState = new GameState(grid, 10, Tetromino.O, Tetromino.I);
        log.info(gameState.toString());

        Assert.assertTrue(gameState.isTopped());
    }

    /** Tests the isTopped method. */
    @Test
    public void testIsToppedOneBlockInThirdRowFromTop() {
        List<Tetromino> grid = createEmptyGrid(220);
        // add a single block just below the vanish zone
        grid.set(2 + 19 * 10, Tetromino.Z);
        GameState gameState = new GameState(grid, 10, Tetromino.O, Tetromino.I);
        log.info(gameState.toString());

        Assert.assertFalse(gameState.isTopped());
    }

    /** Tests the canMoveLeft method when there is no active block. */
    @Test(expected = IllegalStateException.class)
    public void testCanMoveLeftNoActiveBlock() {
        GameState gameState = new GameState();
        log.info(gameState.toString());

        gameState.canMoveLeft();
    }

    /** Tests the canMoveLeft method with an empty grid and the active block at the spawn location. */
    @Test
    public void testCanMoveLeftEmptyGridSpawn() {
        List<Tetromino> grid = createEmptyGrid(220);
        GameState gameState = new GameState(grid, 10, Tetromino.O, Tetromino.I);
        log.info(gameState.toString());

        Assert.assertTrue(gameState.canMoveLeft());
    }

    /** Tests the canMoveLeft method with a full grid and the active block at the spawn location. */
    @Test
    public void testCanMoveLeftFullGridSpawn() {
        List<Tetromino> grid = createGrid(220, Tetromino.L);
        GameState gameState = new GameState(grid, 10, Tetromino.O, Tetromino.I);
        log.info(gameState.toString());

        Assert.assertFalse(gameState.canMoveLeft());
    }

    /** Tests the canMoveLeft method when the currently active block is hugging the left wall. */
    @Test
    public void testCanMoveLeftEmptyGridLeft() {
        List<Tetromino> grid = createEmptyGrid(220);
        GameState gameState = new GameState(grid, 10, Tetromino.O, new Point(-1, 10), Orientation.getDefault(),
                Tetromino.I);
        log.info(gameState.toString());

        Assert.assertFalse(gameState.canMoveLeft());
    }

    /** Tests the canMoveLeft method when the currently active block is hugging the right wall. */
    @Test
    public void testCanMoveLeftEmptyGridRight() {
        List<Tetromino> grid = createEmptyGrid(220);
        GameState gameState = new GameState(grid, 10, Tetromino.O, new Point(7, 10), Orientation.getDefault(),
                Tetromino.I);
        log.info(gameState.toString());

        Assert.assertTrue(gameState.canMoveLeft());
    }

    /** Tests the canMoveLeft method when the currently active block is directly to the right of a block. */
    @Test
    public void testCanMoveLeftRightOfBlock() {
        List<Tetromino> grid = createEmptyGrid(220);
        // add a single block at (2, 2)
        grid.set(2 + 2 * 10, Tetromino.S);
        GameState gameState = new GameState(grid, 10, Tetromino.O, new Point(2, 1), Orientation.getDefault(),
                Tetromino.I);
        log.info(gameState.toString());

        Assert.assertFalse(gameState.canMoveLeft());
    }

    /** Tests the canMoveLeft method when the currently active block is directly to the left of a block. */
    @Test
    public void testCanMoveLeftLeftOfBlock() {
        List<Tetromino> grid = createEmptyGrid(220);
        // add a single block at (5, 2)
        grid.set(5 + 2 * 10, Tetromino.S);
        GameState gameState = new GameState(grid, 10, Tetromino.O, new Point(2, 1), Orientation.getDefault(),
                Tetromino.I);
        log.info(gameState.toString());

        Assert.assertTrue(gameState.canMoveLeft());
    }
    
    /** Tests the canMoveRight method when there is no active block. */
    @Test(expected = IllegalStateException.class)
    public void testCanMoveRightNoActiveBlock() {
        GameState gameState = new GameState();
        log.info(gameState.toString());

        gameState.canMoveRight();
    }

    /** Tests the canMoveRight method with an empty grid and the active block at the spawn location. */
    @Test
    public void testCanMoveRightEmptyGridSpawn() {
        List<Tetromino> grid = createEmptyGrid(220);
        GameState gameState = new GameState(grid, 10, Tetromino.O, Tetromino.I);
        log.info(gameState.toString());

        Assert.assertTrue(gameState.canMoveRight());
    }

    /** Tests the canMoveRight method with a full grid and the active block at the spawn location. */
    @Test
    public void testCanMoveRightFullGridSpawn() {
        List<Tetromino> grid = createGrid(220, Tetromino.L);
        GameState gameState = new GameState(grid, 10, Tetromino.O, Tetromino.I);
        log.info(gameState.toString());

        Assert.assertFalse(gameState.canMoveRight());
    }

    /** Tests the canMoveLeft method when the currently active block is hugging the left wall. */
    @Test
    public void testCanMoveRightEmptyGridLeft() {
        List<Tetromino> grid = createEmptyGrid(220);
        GameState gameState = new GameState(grid, 10, Tetromino.O, new Point(-1, 10), Orientation.getDefault(),
                Tetromino.I);
        log.info(gameState.toString());

        Assert.assertTrue(gameState.canMoveRight());
    }

    /** Tests the canMoveRight method when the currently active block is hugging the right wall. */
    @Test
    public void testCanMoveRightEmptyGridRight() {
        List<Tetromino> grid = createEmptyGrid(220);
        GameState gameState = new GameState(grid, 10, Tetromino.O, new Point(7, 10), Orientation.getDefault(),
                Tetromino.I);
        log.info(gameState.toString());

        Assert.assertFalse(gameState.canMoveRight());
    }

    /** Tests the canMoveRight method when the currently active block is directly to the right of a block. */
    @Test
    public void testCanMoveRightRightOfBlock() {
        List<Tetromino> grid = createEmptyGrid(220);
        // add a single block at (2, 2)
        grid.set(2 + 2 * 10, Tetromino.S);
        GameState gameState = new GameState(grid, 10, Tetromino.O, new Point(2, 1), Orientation.getDefault(),
                Tetromino.I);
        log.info(gameState.toString());

        Assert.assertTrue(gameState.canMoveRight());
    }

    /** Tests the canMoveRight method when the currently active block is directly to the left of a block. */
    @Test
    public void testCanMoveRightLeftOfBlock() {
        List<Tetromino> grid = createEmptyGrid(220);
        // add a single block at (5, 2)
        grid.set(5 + 2 * 10, Tetromino.S);
        GameState gameState = new GameState(grid, 10, Tetromino.O, new Point(2, 1), Orientation.getDefault(),
                Tetromino.I);
        log.info(gameState.toString());

        Assert.assertFalse(gameState.canMoveRight());
    }

    /** Tests the canMoveDown method when there is no active block. */
    @Test(expected = IllegalStateException.class)
    public void testCanMoveDownNoActiveBlock() {
        GameState gameState = new GameState();
        log.info(gameState.toString());

        gameState.canMoveDown();
    }

    /** Tests the canMoveDown method with an empty grid and the active block at the spawn location. */
    @Test
    public void testCanMoveDownEmptyGridSpawn() {
        List<Tetromino> grid = createEmptyGrid(220);
        GameState gameState = new GameState(grid, 10, Tetromino.O, Tetromino.I);
        log.info(gameState.toString());

        Assert.assertTrue(gameState.canMoveDown());
    }

    /** Tests the canMoveRight method with a full grid and the active block at the spawn location. */
    @Test
    public void testCanMoveDownFullGridSpawn() {
        List<Tetromino> grid = createGrid(220, Tetromino.L);
        GameState gameState = new GameState(grid, 10, Tetromino.O, Tetromino.I);
        log.info(gameState.toString());

        Assert.assertFalse(gameState.canMoveDown());
    }

    /** Tests the canMoveLeft method when the currently active block is hugging the left wall. */
    @Test
    public void testCanMoveDownEmptyGridLeft() {
        List<Tetromino> grid = createEmptyGrid(220);
        GameState gameState = new GameState(grid, 10, Tetromino.O, new Point(-1, 10), Orientation.getDefault(),
                Tetromino.I);
        log.info(gameState.toString());

        Assert.assertTrue(gameState.canMoveDown());
    }

    /** Tests the canMoveDown method when the currently active block is hugging the right wall. */
    @Test
    public void testCanMoveDownEmptyGridRight() {
        List<Tetromino> grid = createEmptyGrid(220);
        GameState gameState = new GameState(grid, 10, Tetromino.O, new Point(7, 10), Orientation.getDefault(),
                Tetromino.I);
        log.info(gameState.toString());

        Assert.assertTrue(gameState.canMoveDown());
    }
    
    /** Tests the canMoveDown method when the currently active block is hugging the bottom of the grid. */
    @Test
    public void testCanMoveDownEmptyGridBottom() {
        List<Tetromino> grid = createEmptyGrid(220);
        GameState gameState = new GameState(grid, 10, Tetromino.O, new Point(3, -1), Orientation.getDefault(),
                Tetromino.I);
        log.info(gameState.toString());

        Assert.assertFalse(gameState.canMoveDown());
    }

    /** Tests the canMoveDown method when the currently active block is directly to the right of a block. */
    @Test
    public void testCanMoveDownRightOfBlock() {
        List<Tetromino> grid = createEmptyGrid(220);
        // add a single block at (2, 2)
        grid.set(2 + 2 * 10, Tetromino.S);
        GameState gameState = new GameState(grid, 10, Tetromino.O, new Point(2, 1), Orientation.getDefault(),
                Tetromino.I);
        log.info(gameState.toString());

        Assert.assertTrue(gameState.canMoveDown());
    }

    /** Tests the canMoveDown method when the currently active block is directly to the left of a block. */
    @Test
    public void testCanMoveDownLeftOfBlock() {
        List<Tetromino> grid = createEmptyGrid(220);
        // add a single block at (5, 2)
        grid.set(5 + 2 * 10, Tetromino.S);
        GameState gameState = new GameState(grid, 10, Tetromino.O, new Point(2, 1), Orientation.getDefault(),
                Tetromino.I);
        log.info(gameState.toString());

        Assert.assertTrue(gameState.canMoveDown());
    }
    
    /** Tests the canMoveDown method when the currently active block is directly above a block. */
    @Test
    public void testCanMoveDownAboveBlock() {
        List<Tetromino> grid = createEmptyGrid(220);
        // add a single block at (2, 2)
        grid.set(2 + 2 * 10, Tetromino.S);
        GameState gameState = new GameState(grid, 10, Tetromino.O, new Point(1, 2), Orientation.getDefault(),
                Tetromino.I);
        log.info(gameState.toString());

        Assert.assertFalse(gameState.canMoveDown());
    }
    
    /** Tests the canMoveDown method when the currently active block is directly below a block. */
    @Test
    public void testCanMoveDownBelowBlock() {
        List<Tetromino> grid = createEmptyGrid(220);
        // add a single block at (2, 5)
        grid.set(2 + 5 * 10, Tetromino.S);
        GameState gameState = new GameState(grid, 10, Tetromino.O, new Point(1, 2), Orientation.getDefault(),
                Tetromino.I);
        log.info(gameState.toString());

        Assert.assertTrue(gameState.canMoveDown());
    }
    
    /** Tests the getGhostLocation method, when there is currently no block. */
    @Test
    public void testGetGhostLocationNoCurrentBlock() {
        GameState gameState = new GameState();
        log.info(gameState.toString());

        Assert.assertNull(gameState.getGhostLocation());
    }
    
    /** Tests the getGhostLocation method when the currently active block is on the floor. */
    @Test
    public void testGetGhostLocationOnFloor() {
        List<Tetromino> grid = createEmptyGrid(220);
        Point blockLocation = new Point(1, -1);
        GameState gameState = new GameState(grid, 10, Tetromino.O, blockLocation, Orientation.getDefault(),
                Tetromino.I);
        log.info(gameState.toString());

        Assert.assertEquals(blockLocation, gameState.getGhostLocation());
    }
    
    /** Tests the getGhostLocation method when the currently active block is just above the floor. */
    @Test
    public void testGetGhostLocation1AboveFloor() {
        List<Tetromino> grid = createEmptyGrid(220);
        Point blockLocation = new Point(1, 0);
        GameState gameState = new GameState(grid, 10, Tetromino.O, blockLocation, Orientation.getDefault(),
                Tetromino.I);
        log.info(gameState.toString());

        Assert.assertEquals(new Point(1, -1), gameState.getGhostLocation());
    }
    
    /** Tests the getGhostLocation method when the currently active block is far above the floor. */
    @Test
    public void testGetGhostLocationFarAboveFloor() {
        List<Tetromino> grid = createEmptyGrid(220);
        Point blockLocation = new Point(1, 15);
        GameState gameState = new GameState(grid, 10, Tetromino.O, blockLocation, Orientation.getDefault(),
                Tetromino.I);
        log.info(gameState.toString());

        Assert.assertEquals(new Point(1, -1), gameState.getGhostLocation());
    }
    
    
    /** Tests the getGhostLocation method when the currently active block is directly above a block. */
    @Test
    public void testGetGhostLocationDirectlyAboveBlock() {
        List<Tetromino> grid = createEmptyGrid(220);
        // add a single block at (2, 2)
        grid.set(2 + 2 * 10, Tetromino.S);
        Point blockLocation = new Point(1, 2);
        GameState gameState = new GameState(grid, 10, Tetromino.O, blockLocation, Orientation.getDefault(),
                Tetromino.I);
        log.info(gameState.toString());

        Assert.assertEquals(blockLocation, gameState.getGhostLocation());
    }
    
    /** Tests the {@link GameState#isFullLine(int)} method in case of a fully empty grid. */
    @Test
    public void testIsFullLineEmptyGrid() {
        GameState gameState = new GameState();
        
        for (int y = 0; y != gameState.getHeight(); y++) {
            Assert.assertFalse("Full line found at index " + y, gameState.isFullLine(y));
        }
    }
    
    /** Tests the {@link GameState#isFullLine(int)} method in case of a full line. */
    @Test
    public void testIsFullLineAtBottom() {
        List<Tetromino> grid = createEmptyGrid(220);
        // add a line at 0
        for (int x = 0; x != 10; x++) {
            grid.set(x, Tetromino.values()[x % Tetromino.values().length]);
        }
        GameState gameState = new GameState(grid, 10, null, null, null, Tetromino.I);
        log.info(gameState.toString());

        Assert.assertTrue(gameState.isFullLine(0));
    }
    
    /** Tests the {@link GameState#isFullLine(int)} method in case of a full line. */
    @Test
    public void testIsFullLineAboveBottom() {
        List<Tetromino> grid = createEmptyGrid(220);
        // add a line at 1
        for (int x = 10; x != 20; x++) {
            grid.set(x, Tetromino.values()[x % Tetromino.values().length]);
        }
        GameState gameState = new GameState(grid, 10, null, null, null, Tetromino.I);
        log.info(gameState.toString());

        Assert.assertFalse(gameState.isFullLine(0));
        Assert.assertTrue(gameState.isFullLine(1));
    }
    
    /** Tests the {@link GameState#isFullLine(int)} method in case of a partially filled line. */
    @Test
    public void testIsFullPartialLineAtBottom() {
        List<Tetromino> grid = createEmptyGrid(220);
        grid.set(0, Tetromino.L);
        grid.set(2, Tetromino.L);
        grid.set(3, Tetromino.L);
        grid.set(5, Tetromino.L);
        grid.set(6, Tetromino.L);
        GameState gameState = new GameState(grid, 10, null, null, null, Tetromino.I);
        log.info(gameState.toString());

        Assert.assertFalse(gameState.isFullLine(0));
    }
    
    /** Tests the {@link GameState#computeLevel()} method. */
    @Test
    public void testComputeLevel() {
        GameState state = new GameState();
        Assert.assertEquals(0, state.computeLevel());
    }
    
    /**
     * Creates a list of tetrominoes.
     * 
     * @param size
     *            number of items in the list
     * @return list
     */
    private List<Tetromino> createGrid(int size) {
        List<Tetromino> values = new ArrayList<>(Tetromino.values().length);
        values.add(null);
        values.addAll(Arrays.asList(Tetromino.values()));

        List<Tetromino> result = new ArrayList<>();
        int i = 0;
        while (result.size() != size) {
            result.add(values.get(i));
            i = (i + 1) % values.size();
        }
        result = Collections.unmodifiableList(result);
        return result;
    }

    /**
     * Creates an list with the given size, containing all null values.
     * 
     * @param size
     *            size of the list to be returned
     * @return list containing only null values
     */
    private List<Tetromino> createEmptyGrid(int size) {
        return createGrid(size, null);
    }

    /**
     * Creates a list of tetrominoes.
     * 
     * @param size
     *            number of items in the list
     * @param value
     *            the tetromino value; all items of the list will have this value
     * @return list
     */
    private List<Tetromino> createGrid(int size, Tetromino value) {
        List<Tetromino> result = new ArrayList<>();
        while (result.size() != size) {
            result.add(value);
        }
        return result;
    }
}
