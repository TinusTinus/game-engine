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
        for (Block block : gameState.getGrid()) {
            Assert.assertNull(block);
        }
        Assert.assertNotNull(gameState.getNext());
    }

    /** Test case for the constructor with custom width and height values. */
    @Test
    public void testWidthHeightConstructor() {
        GameState gameState = new GameState(38, 12);

        log.info(gameState.toString());
        Assert.assertEquals(38, gameState.getWidth());
        Assert.assertEquals(12, gameState.getHeight());
        for (Block block : gameState.getGrid()) {
            Assert.assertNull(block);
        }
        Assert.assertNotNull(gameState.getNext());
    }

    /** Test case for the constructor with custom width and height values. */
    @Test
    public void testWidthHeightConstructorMinimum() {
        GameState gameState = new GameState(4, 6);

        log.info(gameState.toString());
        Assert.assertEquals(4, gameState.getWidth());
        Assert.assertEquals(6, gameState.getHeight());
        for (Block block : gameState.getGrid()) {
            Assert.assertNull(block);
        }
        Assert.assertNotNull(gameState.getNext());
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
        List<Block> grid = createGrid(64);

        GameState gameState = new GameState(grid, 8, Tetromino.L, Tetromino.Z);

        log.info(gameState.toString());
        Assert.assertEquals(8, gameState.getWidth());
        Assert.assertEquals(8, gameState.getHeight());
        Assert.assertEquals(grid, gameState.getGrid());
        Assert.assertEquals(Tetromino.L, gameState.getActiveTetromino());
        Assert.assertEquals(Tetromino.Z, gameState.getNext());
    }

    /** Test case for the constructor where all required fields are simply passed in. */
    @Test
    public void testConstructorMinimalSize() {
        List<Block> grid = createGrid(24);

        GameState gameState = new GameState(grid, 4, Tetromino.L, Tetromino.Z);

        log.info(gameState.toString());
        Assert.assertEquals(4, gameState.getWidth());
        Assert.assertEquals(6, gameState.getHeight());
        Assert.assertEquals(grid, gameState.getGrid());
        Assert.assertEquals(Tetromino.L, gameState.getActiveTetromino());
        Assert.assertEquals(Tetromino.Z, gameState.getNext());
    }

    /** Test case for the constructor where all required fields are simply passed in. */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorEmptyGrid() {
        new GameState(Collections.<Block> emptyList(), 4, Tetromino.L, Tetromino.Z);
    }

    /** Test case for the constructor where all required fields are simply passed in. */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorNegativeWidth() {
        List<Block> grid = createGrid(16);

        new GameState(grid, -4, Tetromino.L, Tetromino.Z);
    }

    /** Test case for the constructor where all required fields are simply passed in. */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorZeroWidth() {
        List<Block> grid = createGrid(16);

        new GameState(grid, 0, Tetromino.L, Tetromino.Z);
    }

    /** Test case for the constructor where all required fields are simply passed in. */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorTooSmallWidth() {
        List<Block> grid = createGrid(16);

        new GameState(grid, 2, Tetromino.L, Tetromino.Z);
    }

    /** Test case for the constructor where all required fields are simply passed in. */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorTooSmallHeight() {
        List<Block> grid = createGrid(8);

        new GameState(grid, 4, Tetromino.L, Tetromino.Z);
    }

    /** Test case for the constructor where all required fields are simply passed in. */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorNotDivisableByWidth() {
        List<Block> grid = createGrid(65);

        new GameState(grid, 8, Tetromino.L, Tetromino.Z);
    }

    /** Test case where a game state is copied. */
    @Test
    public void testConstructorCopy() {
        GameState gameState0 = new GameState();

        GameState gameState1 = new GameState(gameState0.getGrid(), gameState0.getWidth(),
                gameState0.getActiveTetromino(), gameState0.getCurrentBlockLocation(),
                gameState0.getCurrentBlockOrientation(), gameState0.getNext());

        Assert.assertEquals(gameState0, gameState1);
    }

    /** Test case for the getBlock method. */
    @Test
    public void testGetBlockNoBlocksInGrid() {
        GameState gameState = new GameState();
        log.info(gameState.toString());

        for (int x = 0; x != 10; x++) {
            for (int y = 0; y != 22; y++) {
                Block block = gameState.getBlock(x, y);

                Assert.assertNull(block);
            }
        }
    }

    /** Test case for the getBlock method. */
    @Test
    public void testGetBlock() {
        List<Block> grid = new ArrayList<>(10 * 22);
        while (grid.size() != 10 * 22) {
            grid.add(Block.O);
        }
        grid = Collections.unmodifiableList(grid);
        GameState gameState = new GameState(grid, 10, Tetromino.L, Tetromino.Z);
        log.info(gameState.toString());

        for (int x = 0; x != 10; x++) {
            for (int y = 0; y != 22; y++) {
                Block block = gameState.getBlock(x, y);

                Assert.assertEquals(Block.O, block);
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
        List<Block> grid = createGrid(220, Block.J);
        GameState gameState = new GameState(grid, 10, null, Tetromino.I);
        log.info(gameState.toString());

        Assert.assertTrue(gameState.isTopped());
    }

    /** Tests the isTopped method. */
    @Test
    public void testIsToppedEmptyGridActiveBlock() {
        List<Block> grid = createEmptyGrid(220);
        GameState gameState = new GameState(grid, 10, Tetromino.J, Tetromino.I);
        log.info(gameState.toString());

        Assert.assertFalse(gameState.isTopped());
    }

    /** Tests the isTopped method. */
    @Test
    public void testIsToppedFullGridActiveBlock() {
        List<Block> grid = createGrid(220, Block.L);
        GameState gameState = new GameState(grid, 10, Tetromino.J, Tetromino.I);
        log.info(gameState.toString());

        Assert.assertTrue(gameState.isTopped());
    }

    /** Tests the isTopped method. */
    @Test
    public void testIsToppedOneBlockOverlap() {
        List<Block> grid = createEmptyGrid(220);
        // add a single block at (2, 2)
        grid.set(2 + 2 * 10, Block.S);
        GameState gameState = new GameState(grid, 10, Tetromino.O, new Point(1, 1), Orientation.getDefault(),
                Tetromino.I);
        log.info(gameState.toString());

        Assert.assertTrue(gameState.isTopped());
    }

    /** Tests the isTopped method. */
    @Test
    public void testIsToppedOneBlockInTopRow() {
        List<Block> grid = createEmptyGrid(220);
        // add a single block in the vanish zone
        grid.set(2 + 21 * 10, Block.Z);
        GameState gameState = new GameState(grid, 10, Tetromino.O, Tetromino.I);
        log.info(gameState.toString());

        Assert.assertTrue(gameState.isTopped());
    }

    /** Tests the isTopped method. */
    @Test
    public void testIsToppedOneBlockInSecondRowFromTop() {
        List<Block> grid = createEmptyGrid(220);
        // add a single block in the vanish zone
        grid.set(2 + 20 * 10, Block.Z);
        GameState gameState = new GameState(grid, 10, Tetromino.O, Tetromino.I);
        log.info(gameState.toString());

        Assert.assertTrue(gameState.isTopped());
    }

    /** Tests the isTopped method. */
    @Test
    public void testIsToppedOneBlockInThirdRowFromTop() {
        List<Block> grid = createEmptyGrid(220);
        // add a single block just below the vanish zone
        grid.set(2 + 19 * 10, Block.Z);
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
        List<Block> grid = createEmptyGrid(220);
        GameState gameState = new GameState(grid, 10, Tetromino.O, Tetromino.I);
        log.info(gameState.toString());

        Assert.assertTrue(gameState.canMoveLeft());
    }

    /** Tests the canMoveLeft method with a full grid and the active block at the spawn location. */
    @Test
    public void testCanMoveLeftFullGridSpawn() {
        List<Block> grid = createGrid(220, Block.L);
        GameState gameState = new GameState(grid, 10, Tetromino.O, Tetromino.I);
        log.info(gameState.toString());

        Assert.assertFalse(gameState.canMoveLeft());
    }

    /** Tests the canMoveLeft method when the currently active block is hugging the left wall. */
    @Test
    public void testCanMoveLeftEmptyGridLeft() {
        List<Block> grid = createEmptyGrid(220);
        GameState gameState = new GameState(grid, 10, Tetromino.O, new Point(-1, 10), Orientation.getDefault(),
                Tetromino.I);
        log.info(gameState.toString());

        Assert.assertFalse(gameState.canMoveLeft());
    }

    /** Tests the canMoveLeft method when the currently active block is hugging the right wall. */
    @Test
    public void testCanMoveLeftEmptyGridRight() {
        List<Block> grid = createEmptyGrid(220);
        GameState gameState = new GameState(grid, 10, Tetromino.O, new Point(7, 10), Orientation.getDefault(),
                Tetromino.I);
        log.info(gameState.toString());

        Assert.assertTrue(gameState.canMoveLeft());
    }

    /** Tests the canMoveLeft method when the currently active block is directly to the right of a block. */
    @Test
    public void testCanMoveLeftRightOfBlock() {
        List<Block> grid = createEmptyGrid(220);
        // add a single block at (2, 2)
        grid.set(2 + 2 * 10, Block.S);
        GameState gameState = new GameState(grid, 10, Tetromino.O, new Point(2, 1), Orientation.getDefault(),
                Tetromino.I);
        log.info(gameState.toString());

        Assert.assertFalse(gameState.canMoveLeft());
    }

    /** Tests the canMoveLeft method when the currently active block is directly to the left of a block. */
    @Test
    public void testCanMoveLeftLeftOfBlock() {
        List<Block> grid = createEmptyGrid(220);
        // add a single block at (5, 2)
        grid.set(5 + 2 * 10, Block.S);
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
        List<Block> grid = createEmptyGrid(220);
        GameState gameState = new GameState(grid, 10, Tetromino.O, Tetromino.I);
        log.info(gameState.toString());

        Assert.assertTrue(gameState.canMoveRight());
    }

    /** Tests the canMoveRight method with a full grid and the active block at the spawn location. */
    @Test
    public void testCanMoveRightFullGridSpawn() {
        List<Block> grid = createGrid(220, Block.L);
        GameState gameState = new GameState(grid, 10, Tetromino.O, Tetromino.I);
        log.info(gameState.toString());

        Assert.assertFalse(gameState.canMoveRight());
    }

    /** Tests the canMoveLeft method when the currently active block is hugging the left wall. */
    @Test
    public void testCanMoveRightEmptyGridLeft() {
        List<Block> grid = createEmptyGrid(220);
        GameState gameState = new GameState(grid, 10, Tetromino.O, new Point(-1, 10), Orientation.getDefault(),
                Tetromino.I);
        log.info(gameState.toString());

        Assert.assertTrue(gameState.canMoveRight());
    }

    /** Tests the canMoveRight method when the currently active block is hugging the right wall. */
    @Test
    public void testCanMoveRightEmptyGridRight() {
        List<Block> grid = createEmptyGrid(220);
        GameState gameState = new GameState(grid, 10, Tetromino.O, new Point(7, 10), Orientation.getDefault(),
                Tetromino.I);
        log.info(gameState.toString());

        Assert.assertFalse(gameState.canMoveRight());
    }

    /** Tests the canMoveRight method when the currently active block is directly to the right of a block. */
    @Test
    public void testCanMoveRightRightOfBlock() {
        List<Block> grid = createEmptyGrid(220);
        // add a single block at (2, 2)
        grid.set(2 + 2 * 10, Block.S);
        GameState gameState = new GameState(grid, 10, Tetromino.O, new Point(2, 1), Orientation.getDefault(),
                Tetromino.I);
        log.info(gameState.toString());

        Assert.assertTrue(gameState.canMoveRight());
    }

    /** Tests the canMoveRight method when the currently active block is directly to the left of a block. */
    @Test
    public void testCanMoveRightLeftOfBlock() {
        List<Block> grid = createEmptyGrid(220);
        // add a single block at (5, 2)
        grid.set(5 + 2 * 10, Block.S);
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
        List<Block> grid = createEmptyGrid(220);
        GameState gameState = new GameState(grid, 10, Tetromino.O, Tetromino.I);
        log.info(gameState.toString());

        Assert.assertTrue(gameState.canMoveDown());
    }

    /** Tests the canMoveRight method with a full grid and the active block at the spawn location. */
    @Test
    public void testCanMoveDownFullGridSpawn() {
        List<Block> grid = createGrid(220, Block.L);
        GameState gameState = new GameState(grid, 10, Tetromino.O, Tetromino.I);
        log.info(gameState.toString());

        Assert.assertFalse(gameState.canMoveDown());
    }

    /** Tests the canMoveLeft method when the currently active block is hugging the left wall. */
    @Test
    public void testCanMoveDownEmptyGridLeft() {
        List<Block> grid = createEmptyGrid(220);
        GameState gameState = new GameState(grid, 10, Tetromino.O, new Point(-1, 10), Orientation.getDefault(),
                Tetromino.I);
        log.info(gameState.toString());

        Assert.assertTrue(gameState.canMoveDown());
    }

    /** Tests the canMoveDown method when the currently active block is hugging the right wall. */
    @Test
    public void testCanMoveDownEmptyGridRight() {
        List<Block> grid = createEmptyGrid(220);
        GameState gameState = new GameState(grid, 10, Tetromino.O, new Point(7, 10), Orientation.getDefault(),
                Tetromino.I);
        log.info(gameState.toString());

        Assert.assertTrue(gameState.canMoveDown());
    }
    
    /** Tests the canMoveDown method when the currently active block is hugging the bottom of the grid. */
    @Test
    public void testCanMoveDownEmptyGridBottom() {
        List<Block> grid = createEmptyGrid(220);
        GameState gameState = new GameState(grid, 10, Tetromino.O, new Point(3, -1), Orientation.getDefault(),
                Tetromino.I);
        log.info(gameState.toString());

        Assert.assertFalse(gameState.canMoveDown());
    }

    /** Tests the canMoveDown method when the currently active block is directly to the right of a block. */
    @Test
    public void testCanMoveDownRightOfBlock() {
        List<Block> grid = createEmptyGrid(220);
        // add a single block at (2, 2)
        grid.set(2 + 2 * 10, Block.S);
        GameState gameState = new GameState(grid, 10, Tetromino.O, new Point(2, 1), Orientation.getDefault(),
                Tetromino.I);
        log.info(gameState.toString());

        Assert.assertTrue(gameState.canMoveDown());
    }

    /** Tests the canMoveDown method when the currently active block is directly to the left of a block. */
    @Test
    public void testCanMoveDownLeftOfBlock() {
        List<Block> grid = createEmptyGrid(220);
        // add a single block at (5, 2)
        grid.set(5 + 2 * 10, Block.S);
        GameState gameState = new GameState(grid, 10, Tetromino.O, new Point(2, 1), Orientation.getDefault(),
                Tetromino.I);
        log.info(gameState.toString());

        Assert.assertTrue(gameState.canMoveDown());
    }
    
    /** Tests the canMoveDown method when the currently active block is directly above a block. */
    @Test
    public void testCanMoveDownAboveBlock() {
        List<Block> grid = createEmptyGrid(220);
        // add a single block at (2, 2)
        grid.set(2 + 2 * 10, Block.S);
        GameState gameState = new GameState(grid, 10, Tetromino.O, new Point(1, 2), Orientation.getDefault(),
                Tetromino.I);
        log.info(gameState.toString());

        Assert.assertFalse(gameState.canMoveDown());
    }
    
    /** Tests the canMoveDown method when the currently active block is directly below a block. */
    @Test
    public void testCanMoveDownBelowBlock() {
        List<Block> grid = createEmptyGrid(220);
        // add a single block at (2, 5)
        grid.set(2 + 5 * 10, Block.S);
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
        List<Block> grid = createEmptyGrid(220);
        Point blockLocation = new Point(1, -1);
        GameState gameState = new GameState(grid, 10, Tetromino.O, blockLocation, Orientation.getDefault(),
                Tetromino.I);
        log.info(gameState.toString());

        Assert.assertEquals(blockLocation, gameState.getGhostLocation());
    }
    
    /** Tests the getGhostLocation method when the currently active block is just above the floor. */
    @Test
    public void testGetGhostLocation1AboveFloor() {
        List<Block> grid = createEmptyGrid(220);
        Point blockLocation = new Point(1, 0);
        GameState gameState = new GameState(grid, 10, Tetromino.O, blockLocation, Orientation.getDefault(),
                Tetromino.I);
        log.info(gameState.toString());

        Assert.assertEquals(new Point(1, -1), gameState.getGhostLocation());
    }
    
    /** Tests the getGhostLocation method when the currently active block is far above the floor. */
    @Test
    public void testGetGhostLocationFarAboveFloor() {
        List<Block> grid = createEmptyGrid(220);
        Point blockLocation = new Point(1, 15);
        GameState gameState = new GameState(grid, 10, Tetromino.O, blockLocation, Orientation.getDefault(),
                Tetromino.I);
        log.info(gameState.toString());

        Assert.assertEquals(new Point(1, -1), gameState.getGhostLocation());
    }
    
    
    /** Tests the getGhostLocation method when the currently active block is directly above a block. */
    @Test
    public void testGetGhostLocationDirectlyAboveBlock() {
        List<Block> grid = createEmptyGrid(220);
        // add a single block at (2, 2)
        grid.set(2 + 2 * 10, Block.S);
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
        List<Block> grid = createEmptyGrid(220);
        // add a line at 0
        for (int x = 0; x != 10; x++) {
            grid.set(x, Block.values()[x % Block.values().length]);
        }
        GameState gameState = new GameState(grid, 10, null, null, null, Tetromino.I);
        log.info(gameState.toString());

        Assert.assertTrue(gameState.isFullLine(0));
    }
    
    /** Tests the {@link GameState#isFullLine(int)} method in case of a full line. */
    @Test
    public void testIsFullLineAboveBottom() {
        List<Block> grid = createEmptyGrid(220);
        // add a line at 1
        for (int x = 10; x != 20; x++) {
            grid.set(x, Block.values()[x % Block.values().length]);
        }
        GameState gameState = new GameState(grid, 10, null, null, null, Tetromino.I);
        log.info(gameState.toString());

        Assert.assertFalse(gameState.isFullLine(0));
        Assert.assertTrue(gameState.isFullLine(1));
    }
    
    /** Tests the {@link GameState#isFullLine(int)} method in case of a partially filled line. */
    @Test
    public void testIsFullPartialLineAtBottom() {
        List<Block> grid = createEmptyGrid(220);
        grid.set(0, Block.L);
        grid.set(2, Block.L);
        grid.set(3, Block.L);
        grid.set(5, Block.L);
        grid.set(6, Block.L);
        GameState gameState = new GameState(grid, 10, null, null, null, Tetromino.I);
        log.info(gameState.toString());

        Assert.assertFalse(gameState.isFullLine(0));
    }
    
    /** Test method for {@link GameState#getBlockSpawnLocation()}. */
    @Test
    public void testGetBlockSpawnLocation() {
        GameState gameState = new GameState(10, 22);
        
        Point point = gameState.getBlockSpawnLocation();
        
        Assert.assertEquals(new Point(3, 16), point);
    }
    
    /** Test case for {@link GameState#isCurrentBlockWithinBounds()}. */
    @Test
    public void test() {
        List<Block> grid = createEmptyGrid(220);
        Point blockLocation = new Point(1, 15);
        GameState gameState = new GameState(grid, 10, Tetromino.O, blockLocation, Orientation.getDefault(),
                Tetromino.I);
        log.info(gameState.toString());

        Assert.assertTrue(gameState.isCurrentBlockWithinBounds());
    }
    
    
    /**
     * Creates a list of tetrominoes.
     * 
     * @param size
     *            number of items in the list
     * @return list
     */
    private List<Block> createGrid(int size) {
        List<Block> values = new ArrayList<>(Block.values().length);
        values.add(null);
        values.addAll(Arrays.asList(Block.values()));

        List<Block> result = new ArrayList<>();
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
    private List<Block> createEmptyGrid(int size) {
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
    private List<Block> createGrid(int size, Block value) {
        List<Block> result = new ArrayList<>();
        while (result.size() != size) {
            result.add(value);
        }
        return result;
    }
}
