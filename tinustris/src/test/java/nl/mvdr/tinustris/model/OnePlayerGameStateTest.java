package nl.mvdr.tinustris.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.extern.slf4j.Slf4j;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test class for {@link OnePlayerGameState}.
 * 
 * @author Martijn van de Rijdt
 */
@Slf4j
public class OnePlayerGameStateTest {
    /** Test case for the default constructor. */
    @Test
    public void testDefaultConstructor() {
        OnePlayerGameState gameState = new OnePlayerGameState();

        log.info(gameState.toString());
        Assert.assertEquals(10, gameState.getWidth());
        Assert.assertEquals(22, gameState.getHeight());
        gameState.getGrid()
            .forEach(block -> Assert.assertFalse(block.isPresent()));
        Assert.assertNotNull(gameState.getNext());
    }

    /** Test case for the constructor with custom width and height values. */
    @Test
    public void testWidthHeightConstructor() {
        OnePlayerGameState gameState = new OnePlayerGameState(38, 12);

        log.info(gameState.toString());
        Assert.assertEquals(38, gameState.getWidth());
        Assert.assertEquals(12, gameState.getHeight());
        gameState.getGrid()
            .forEach(block -> Assert.assertFalse(block.isPresent()));
        Assert.assertNotNull(gameState.getNext());
    }

    /** Test case for the constructor with custom width and height values. */
    @Test
    public void testWidthHeightConstructorMinimum() {
        OnePlayerGameState gameState = new OnePlayerGameState(4, 6);

        log.info(gameState.toString());
        Assert.assertEquals(4, gameState.getWidth());
        Assert.assertEquals(6, gameState.getHeight());
        gameState.getGrid()
            .forEach(block -> Assert.assertFalse(block.isPresent()));
        Assert.assertNotNull(gameState.getNext());
    }

    /** Test case for the constructor with custom width and height values. */
    @Test(expected = IllegalArgumentException.class)
    public void testWidthHeightConstructorNegativeWidth() {
        new OnePlayerGameState(-4, 22);
    }

    /** Test case for the constructor with custom width and height values. */
    @Test(expected = IllegalArgumentException.class)
    public void testWidthHeightConstructorZeroWidth() {
        new OnePlayerGameState(0, 22);
    }

    /** Test case for the constructor with custom width and height values. */
    @Test(expected = IllegalArgumentException.class)
    public void testWidthHeightConstructorTooSmallWidth() {
        new OnePlayerGameState(1, 22);
    }

    /** Test case for the constructor with custom width and height values. */
    @Test(expected = IllegalArgumentException.class)
    public void testWidthHeightConstructorNegativeHeight() {
        new OnePlayerGameState(10, -4);
    }

    /** Test case for the constructor with custom width and height values. */
    @Test(expected = IllegalArgumentException.class)
    public void testWidthHeightConstructorZeroHeight() {
        new OnePlayerGameState(10, 0);
    }

    /** Test case for the constructor with custom width and height values. */
    @Test(expected = IllegalArgumentException.class)
    public void testWidthHeightConstructorTooSmallHeight() {
        new OnePlayerGameState(10, 1);
    }

    /** Test case for the constructor where all required fields are simply passed in. */
    @Test
    public void testConstructor() {
        List<Optional<Block>> grid = createGrid(64);

        OnePlayerGameState gameState = new OnePlayerGameState(grid, 8, Tetromino.L, Tetromino.Z);

        log.info(gameState.toString());
        Assert.assertEquals(8, gameState.getWidth());
        Assert.assertEquals(8, gameState.getHeight());
        Assert.assertEquals(grid, gameState.getGrid());
        Assert.assertEquals(Tetromino.L, gameState.getActiveTetromino().get());
        Assert.assertEquals(Tetromino.Z, gameState.getNext());
    }

    /** Test case for the constructor where all required fields are simply passed in. */
    @Test
    public void testConstructorMinimalSize() {
        List<Optional<Block>> grid = createGrid(24);

        OnePlayerGameState gameState = new OnePlayerGameState(grid, 4, Tetromino.L, Tetromino.Z);

        log.info(gameState.toString());
        Assert.assertEquals(4, gameState.getWidth());
        Assert.assertEquals(6, gameState.getHeight());
        Assert.assertEquals(grid, gameState.getGrid());
        Assert.assertEquals(Tetromino.L, gameState.getActiveTetromino().get());
        Assert.assertEquals(Tetromino.Z, gameState.getNext());
    }

    /** Test case for the constructor where all required fields are simply passed in. */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorEmptyGrid() {
        new OnePlayerGameState(Collections.emptyList(), 4, Tetromino.L, Tetromino.Z);
    }

    /** Test case for the constructor where all required fields are simply passed in. */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorNegativeWidth() {
        List<Optional<Block>> grid = createGrid(16);

        new OnePlayerGameState(grid, -4, Tetromino.L, Tetromino.Z);
    }

    /** Test case for the constructor where all required fields are simply passed in. */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorZeroWidth() {
        List<Optional<Block>> grid = createGrid(16);

        new OnePlayerGameState(grid, 0, Tetromino.L, Tetromino.Z);
    }

    /** Test case for the constructor where all required fields are simply passed in. */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorTooSmallWidth() {
        List<Optional<Block>> grid = createGrid(16);

        new OnePlayerGameState(grid, 2, Tetromino.L, Tetromino.Z);
    }

    /** Test case for the constructor where all required fields are simply passed in. */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorTooSmallHeight() {
        List<Optional<Block>> grid = createGrid(8);

        new OnePlayerGameState(grid, 4, Tetromino.L, Tetromino.Z);
    }

    /** Test case for the constructor where all required fields are simply passed in. */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorNotDivisableByWidth() {
        List<Optional<Block>> grid = createGrid(65);

        new OnePlayerGameState(grid, 8, Tetromino.L, Tetromino.Z);
    }

    /** Test case where a game state is copied. */
    @Test
    public void testConstructorCopy() {
        OnePlayerGameState gameState0 = new OnePlayerGameState();

        OnePlayerGameState gameState1 = new OnePlayerGameState(gameState0.getGrid(), gameState0.getWidth(),
                gameState0.getActiveTetromino(), gameState0.getCurrentBlockLocation(),
                gameState0.getCurrentBlockOrientation(), gameState0.getNext());

        Assert.assertEquals(gameState0, gameState1);
    }

    /** Test case for the getBlock method. */
    @Test
    public void testGetBlockNoBlocksInGrid() {
        OnePlayerGameState gameState = new OnePlayerGameState();
        log.info(gameState.toString());

        for (int x = 0; x != 10; x++) {
            for (int y = 0; y != 22; y++) {
                Assert.assertFalse(gameState.getBlock(x, y).isPresent());
            }
        }
    }

    /** Test case for the getBlock method. */
    @Test
    public void testGetBlock() {
        List<Optional<Block>> grid = new ArrayList<>(10 * 22);
        while (grid.size() != 10 * 22) {
            grid.add(Optional.of(Block.O));
        }
        grid = Collections.unmodifiableList(grid);
        OnePlayerGameState gameState = new OnePlayerGameState(grid, 10, Tetromino.L, Tetromino.Z);
        log.info(gameState.toString());

        for (int x = 0; x != 10; x++) {
            for (int y = 0; y != 22; y++) {
                Optional<Block> block = gameState.getBlock(x, y);

                Assert.assertEquals(Block.O, block.get());
            }
        }
    }

    /** Test case for the getBlock method. */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testGetBlockXTooSmall() {
        OnePlayerGameState gameState = new OnePlayerGameState();
        log.info(gameState.toString());

        gameState.getBlock(-1, 4);
    }

    /** Test case for the getBlock method. */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testGetBlockXTooLarge() {
        OnePlayerGameState gameState = new OnePlayerGameState();
        log.info(gameState.toString());

        gameState.getBlock(35, 4);
    }

    /** Test case for the getBlock method. */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testGetBlockYTooSmall() {
        OnePlayerGameState gameState = new OnePlayerGameState();
        log.info(gameState.toString());

        gameState.getBlock(4, -1);
    }

    /** Test case for the getBlock method. */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testGetBlockYTooLarge() {
        OnePlayerGameState gameState = new OnePlayerGameState();
        log.info(gameState.toString());

        gameState.getBlock(4, 35);
    }

    /** Tests the isTopped method. */
    @Test
    public void testIsToppedEmptyGridNoActiveBlock() {
        OnePlayerGameState gameState = new OnePlayerGameState();
        log.info(gameState.toString());

        Assert.assertFalse(gameState.isTopped());
    }

    /** Tests the isTopped method. */
    @Test
    public void testIsToppedFullGridNoActiveBlock() {
        List<Optional<Block>> grid = createGrid(220, Block.J);
        OnePlayerGameState gameState = new OnePlayerGameState(grid, 10, Optional.empty(), Tetromino.I);
        log.info(gameState.toString());

        Assert.assertTrue(gameState.isTopped());
    }

    /** Tests the isTopped method. */
    @Test
    public void testIsToppedEmptyGridActiveBlock() {
        List<Optional<Block>> grid = createEmptyGrid(220);
        OnePlayerGameState gameState = new OnePlayerGameState(grid, 10, Tetromino.J, Tetromino.I);
        log.info(gameState.toString());

        Assert.assertFalse(gameState.isTopped());
    }

    /** Tests the isTopped method. */
    @Test
    public void testIsToppedFullGridActiveBlock() {
        List<Optional<Block>> grid = createGrid(220, Block.L);
        OnePlayerGameState gameState = new OnePlayerGameState(grid, 10, Tetromino.J, Tetromino.I);
        log.info(gameState.toString());

        Assert.assertTrue(gameState.isTopped());
    }

    /** Tests the isTopped method. */
    @Test
    public void testIsToppedOneBlockOverlap() {
        List<Optional<Block>> grid = createEmptyGrid(220);
        // add a single block at (2, 2)
        grid.set(2 + 2 * 10, Optional.of(Block.S));
        OnePlayerGameState gameState = new OnePlayerGameState(grid, 10, Tetromino.O, new Point(1, 1), Orientation.getDefault(),
                Tetromino.I);
        log.info(gameState.toString());

        Assert.assertTrue(gameState.isTopped());
    }

    /** Tests the isTopped method. */
    @Test
    public void testIsToppedOneBlockInTopRow() {
        List<Optional<Block>> grid = createEmptyGrid(220);
        // add a single block in the vanish zone
        grid.set(2 + 21 * 10, Optional.of(Block.Z));
        OnePlayerGameState gameState = new OnePlayerGameState(grid, 10, Tetromino.O, Tetromino.I);
        log.info(gameState.toString());

        Assert.assertTrue(gameState.isTopped());
    }

    /** Tests the isTopped method. */
    @Test
    public void testIsToppedOneBlockInSecondRowFromTop() {
        List<Optional<Block>> grid = createEmptyGrid(220);
        // add a single block in the vanish zone
        grid.set(2 + 20 * 10, Optional.of(Block.Z));
        OnePlayerGameState gameState = new OnePlayerGameState(grid, 10, Tetromino.O, Tetromino.I);
        log.info(gameState.toString());

        Assert.assertTrue(gameState.isTopped());
    }

    /** Tests the isTopped method. */
    @Test
    public void testIsToppedOneBlockInThirdRowFromTop() {
        List<Optional<Block>> grid = createEmptyGrid(220);
        // add a single block just below the vanish zone
        grid.set(2 + 19 * 10, Optional.of(Block.Z));
        OnePlayerGameState gameState = new OnePlayerGameState(grid, 10, Tetromino.O, Tetromino.I);
        log.info(gameState.toString());

        Assert.assertFalse(gameState.isTopped());
    }

    /** Tests the canMoveLeft method when there is no active block. */
    @Test(expected = IllegalStateException.class)
    public void testCanMoveLeftNoActiveBlock() {
        OnePlayerGameState gameState = new OnePlayerGameState();
        log.info(gameState.toString());

        gameState.canMoveLeft();
    }

    /** Tests the canMoveLeft method with an empty grid and the active block at the spawn location. */
    @Test
    public void testCanMoveLeftEmptyGridSpawn() {
        List<Optional<Block>> grid = createEmptyGrid(220);
        OnePlayerGameState gameState = new OnePlayerGameState(grid, 10, Tetromino.O, Tetromino.I);
        log.info(gameState.toString());

        Assert.assertTrue(gameState.canMoveLeft());
    }

    /** Tests the canMoveLeft method with a full grid and the active block at the spawn location. */
    @Test
    public void testCanMoveLeftFullGridSpawn() {
        List<Optional<Block>> grid = createGrid(220, Block.L);
        OnePlayerGameState gameState = new OnePlayerGameState(grid, 10, Tetromino.O, Tetromino.I);
        log.info(gameState.toString());

        Assert.assertFalse(gameState.canMoveLeft());
    }

    /** Tests the canMoveLeft method when the currently active block is hugging the left wall. */
    @Test
    public void testCanMoveLeftEmptyGridLeft() {
        List<Optional<Block>> grid = createEmptyGrid(220);
        OnePlayerGameState gameState = new OnePlayerGameState(grid, 10, Tetromino.O, new Point(-1, 10), Orientation.getDefault(),
                Tetromino.I);
        log.info(gameState.toString());

        Assert.assertFalse(gameState.canMoveLeft());
    }

    /** Tests the canMoveLeft method when the currently active block is hugging the right wall. */
    @Test
    public void testCanMoveLeftEmptyGridRight() {
        List<Optional<Block>> grid = createEmptyGrid(220);
        OnePlayerGameState gameState = new OnePlayerGameState(grid, 10, Tetromino.O, new Point(7, 10), Orientation.getDefault(),
                Tetromino.I);
        log.info(gameState.toString());

        Assert.assertTrue(gameState.canMoveLeft());
    }

    /** Tests the canMoveLeft method when the currently active block is directly to the right of a block. */
    @Test
    public void testCanMoveLeftRightOfBlock() {
        List<Optional<Block>> grid = createEmptyGrid(220);
        // add a single block at (2, 2)
        grid.set(2 + 2 * 10, Optional.of(Block.S));
        OnePlayerGameState gameState = new OnePlayerGameState(grid, 10, Tetromino.O, new Point(2, 1), Orientation.getDefault(),
                Tetromino.I);
        log.info(gameState.toString());

        Assert.assertFalse(gameState.canMoveLeft());
    }

    /** Tests the canMoveLeft method when the currently active block is directly to the left of a block. */
    @Test
    public void testCanMoveLeftLeftOfBlock() {
        List<Optional<Block>> grid = createEmptyGrid(220);
        // add a single block at (5, 2)
        grid.set(5 + 2 * 10, Optional.of(Block.S));
        OnePlayerGameState gameState = new OnePlayerGameState(grid, 10, Tetromino.O, new Point(2, 1), Orientation.getDefault(),
                Tetromino.I);
        log.info(gameState.toString());

        Assert.assertTrue(gameState.canMoveLeft());
    }
    
    /** Tests the canMoveRight method when there is no active block. */
    @Test(expected = IllegalStateException.class)
    public void testCanMoveRightNoActiveBlock() {
        OnePlayerGameState gameState = new OnePlayerGameState();
        log.info(gameState.toString());

        gameState.canMoveRight();
    }

    /** Tests the canMoveRight method with an empty grid and the active block at the spawn location. */
    @Test
    public void testCanMoveRightEmptyGridSpawn() {
        List<Optional<Block>> grid = createEmptyGrid(220);
        OnePlayerGameState gameState = new OnePlayerGameState(grid, 10, Tetromino.O, Tetromino.I);
        log.info(gameState.toString());

        Assert.assertTrue(gameState.canMoveRight());
    }

    /** Tests the canMoveRight method with a full grid and the active block at the spawn location. */
    @Test
    public void testCanMoveRightFullGridSpawn() {
        List<Optional<Block>> grid = createGrid(220, Block.L);
        OnePlayerGameState gameState = new OnePlayerGameState(grid, 10, Tetromino.O, Tetromino.I);
        log.info(gameState.toString());

        Assert.assertFalse(gameState.canMoveRight());
    }

    /** Tests the canMoveLeft method when the currently active block is hugging the left wall. */
    @Test
    public void testCanMoveRightEmptyGridLeft() {
        List<Optional<Block>> grid = createEmptyGrid(220);
        OnePlayerGameState gameState = new OnePlayerGameState(grid, 10, Tetromino.O, new Point(-1, 10), Orientation.getDefault(),
                Tetromino.I);
        log.info(gameState.toString());

        Assert.assertTrue(gameState.canMoveRight());
    }

    /** Tests the canMoveRight method when the currently active block is hugging the right wall. */
    @Test
    public void testCanMoveRightEmptyGridRight() {
        List<Optional<Block>> grid = createEmptyGrid(220);
        OnePlayerGameState gameState = new OnePlayerGameState(grid, 10, Tetromino.O, new Point(7, 10), Orientation.getDefault(),
                Tetromino.I);
        log.info(gameState.toString());

        Assert.assertFalse(gameState.canMoveRight());
    }

    /** Tests the canMoveRight method when the currently active block is directly to the right of a block. */
    @Test
    public void testCanMoveRightRightOfBlock() {
        List<Optional<Block>> grid = createEmptyGrid(220);
        // add a single block at (2, 2)
        grid.set(2 + 2 * 10, Optional.of(Block.S));
        OnePlayerGameState gameState = new OnePlayerGameState(grid, 10, Tetromino.O, new Point(2, 1), Orientation.getDefault(),
                Tetromino.I);
        log.info(gameState.toString());

        Assert.assertTrue(gameState.canMoveRight());
    }

    /** Tests the canMoveRight method when the currently active block is directly to the left of a block. */
    @Test
    public void testCanMoveRightLeftOfBlock() {
        List<Optional<Block>> grid = createEmptyGrid(220);
        // add a single block at (5, 2)
        grid.set(5 + 2 * 10, Optional.of(Block.S));
        OnePlayerGameState gameState = new OnePlayerGameState(grid, 10, Tetromino.O, new Point(2, 1), Orientation.getDefault(),
                Tetromino.I);
        log.info(gameState.toString());

        Assert.assertFalse(gameState.canMoveRight());
    }

    /** Tests the canMoveDown method when there is no active block. */
    @Test(expected = IllegalStateException.class)
    public void testCanMoveDownNoActiveBlock() {
        OnePlayerGameState gameState = new OnePlayerGameState();
        log.info(gameState.toString());

        gameState.canMoveDown();
    }

    /** Tests the canMoveDown method with an empty grid and the active block at the spawn location. */
    @Test
    public void testCanMoveDownEmptyGridSpawn() {
        List<Optional<Block>> grid = createEmptyGrid(220);
        OnePlayerGameState gameState = new OnePlayerGameState(grid, 10, Tetromino.O, Tetromino.I);
        log.info(gameState.toString());

        Assert.assertTrue(gameState.canMoveDown());
    }

    /** Tests the canMoveRight method with a full grid and the active block at the spawn location. */
    @Test
    public void testCanMoveDownFullGridSpawn() {
        List<Optional<Block>> grid = createGrid(220, Block.L);
        OnePlayerGameState gameState = new OnePlayerGameState(grid, 10, Tetromino.O, Tetromino.I);
        log.info(gameState.toString());

        Assert.assertFalse(gameState.canMoveDown());
    }

    /** Tests the canMoveLeft method when the currently active block is hugging the left wall. */
    @Test
    public void testCanMoveDownEmptyGridLeft() {
        List<Optional<Block>> grid = createEmptyGrid(220);
        OnePlayerGameState gameState = new OnePlayerGameState(grid, 10, Tetromino.O, new Point(-1, 10), Orientation.getDefault(),
                Tetromino.I);
        log.info(gameState.toString());

        Assert.assertTrue(gameState.canMoveDown());
    }

    /** Tests the canMoveDown method when the currently active block is hugging the right wall. */
    @Test
    public void testCanMoveDownEmptyGridRight() {
        List<Optional<Block>> grid = createEmptyGrid(220);
        OnePlayerGameState gameState = new OnePlayerGameState(grid, 10, Tetromino.O, new Point(7, 10), Orientation.getDefault(),
                Tetromino.I);
        log.info(gameState.toString());

        Assert.assertTrue(gameState.canMoveDown());
    }
    
    /** Tests the canMoveDown method when the currently active block is hugging the bottom of the grid. */
    @Test
    public void testCanMoveDownEmptyGridBottom() {
        List<Optional<Block>> grid = createEmptyGrid(220);
        OnePlayerGameState gameState = new OnePlayerGameState(grid, 10, Tetromino.O, new Point(3, -1), Orientation.getDefault(),
                Tetromino.I);
        log.info(gameState.toString());

        Assert.assertFalse(gameState.canMoveDown());
    }

    /** Tests the canMoveDown method when the currently active block is directly to the right of a block. */
    @Test
    public void testCanMoveDownRightOfBlock() {
        List<Optional<Block>> grid = createEmptyGrid(220);
        // add a single block at (2, 2)
        grid.set(2 + 2 * 10, Optional.of(Block.S));
        OnePlayerGameState gameState = new OnePlayerGameState(grid, 10, Tetromino.O, new Point(2, 1), Orientation.getDefault(),
                Tetromino.I);
        log.info(gameState.toString());

        Assert.assertTrue(gameState.canMoveDown());
    }

    /** Tests the canMoveDown method when the currently active block is directly to the left of a block. */
    @Test
    public void testCanMoveDownLeftOfBlock() {
        List<Optional<Block>> grid = createEmptyGrid(220);
        // add a single block at (5, 2)
        grid.set(5 + 2 * 10, Optional.of(Block.S));
        OnePlayerGameState gameState = new OnePlayerGameState(grid, 10, Tetromino.O, new Point(2, 1), Orientation.getDefault(),
                Tetromino.I);
        log.info(gameState.toString());

        Assert.assertTrue(gameState.canMoveDown());
    }
    
    /** Tests the canMoveDown method when the currently active block is directly above a block. */
    @Test
    public void testCanMoveDownAboveBlock() {
        List<Optional<Block>> grid = createEmptyGrid(220);
        // add a single block at (2, 2)
        grid.set(2 + 2 * 10, Optional.of(Block.S));
        OnePlayerGameState gameState = new OnePlayerGameState(grid, 10, Tetromino.O, new Point(1, 2), Orientation.getDefault(),
                Tetromino.I);
        log.info(gameState.toString());

        Assert.assertFalse(gameState.canMoveDown());
    }
    
    /** Tests the canMoveDown method when the currently active block is directly below a block. */
    @Test
    public void testCanMoveDownBelowBlock() {
        List<Optional<Block>> grid = createEmptyGrid(220);
        // add a single block at (2, 5)
        grid.set(2 + 5 * 10, Optional.of(Block.S));
        OnePlayerGameState gameState = new OnePlayerGameState(grid, 10, Tetromino.O, new Point(1, 2), Orientation.getDefault(),
                Tetromino.I);
        log.info(gameState.toString());

        Assert.assertTrue(gameState.canMoveDown());
    }
    
    /** Tests the getGhostLocation method, when there is currently no block. */
    @Test
    public void testGetGhostLocationNoCurrentBlock() {
        OnePlayerGameState gameState = new OnePlayerGameState();
        log.info(gameState.toString());

        Assert.assertFalse(gameState.getGhostLocation().isPresent());
    }
    
    /** Tests the getGhostLocation method when the currently active block is on the floor. */
    @Test
    public void testGetGhostLocationOnFloor() {
        List<Optional<Block>> grid = createEmptyGrid(220);
        Point blockLocation = new Point(1, -1);
        OnePlayerGameState gameState = new OnePlayerGameState(grid, 10, Tetromino.O, blockLocation, Orientation.getDefault(),
                Tetromino.I);
        log.info(gameState.toString());

        Assert.assertEquals(blockLocation, gameState.getGhostLocation().get());
    }
    
    /** Tests the getGhostLocation method when the currently active block is just above the floor. */
    @Test
    public void testGetGhostLocation1AboveFloor() {
        List<Optional<Block>> grid = createEmptyGrid(220);
        Point blockLocation = new Point(1, 0);
        OnePlayerGameState gameState = new OnePlayerGameState(grid, 10, Tetromino.O, blockLocation, Orientation.getDefault(),
                Tetromino.I);
        log.info(gameState.toString());

        Assert.assertEquals(new Point(1, -1), gameState.getGhostLocation().get());
    }
    
    /** Tests the getGhostLocation method when the currently active block is far above the floor. */
    @Test
    public void testGetGhostLocationFarAboveFloor() {
        List<Optional<Block>> grid = createEmptyGrid(220);
        Point blockLocation = new Point(1, 15);
        OnePlayerGameState gameState = new OnePlayerGameState(grid, 10, Tetromino.O, blockLocation, Orientation.getDefault(),
                Tetromino.I);
        log.info(gameState.toString());

        Assert.assertEquals(new Point(1, -1), gameState.getGhostLocation().get());
    }
    
    
    /** Tests the getGhostLocation method when the currently active block is directly above a block. */
    @Test
    public void testGetGhostLocationDirectlyAboveBlock() {
        List<Optional<Block>> grid = createEmptyGrid(220);
        // add a single block at (2, 2)
        grid.set(2 + 2 * 10, Optional.of(Block.S));
        Point blockLocation = new Point(1, 2);
        OnePlayerGameState gameState = new OnePlayerGameState(grid, 10, Tetromino.O, blockLocation, Orientation.getDefault(),
                Tetromino.I);
        log.info(gameState.toString());

        Assert.assertEquals(blockLocation, gameState.getGhostLocation().get());
    }
    
    /** Tests the {@link OnePlayerGameState#isFullLine(int)} method in case of a fully empty grid. */
    @Test
    public void testIsFullLineEmptyGrid() {
        OnePlayerGameState gameState = new OnePlayerGameState();
        
        for (int y = 0; y != gameState.getHeight(); y++) {
            Assert.assertFalse("Full line found at index " + y, gameState.isFullLine(y));
        }
    }
    
    /** Tests the {@link OnePlayerGameState#isFullLine(int)} method in case of a full line. */
    @Test
    public void testIsFullLineAtBottom() {
        List<Optional<Block>> grid = createEmptyGrid(220);
        // add a line at 0
        for (int x = 0; x != 10; x++) {
            grid.set(x, Optional.of(Block.values()[x % Block.values().length]));
        }
        OnePlayerGameState gameState = new OnePlayerGameState(grid, 10, Optional.empty(), Optional.empty(),
                Optional.empty(), Tetromino.I);
        log.info(gameState.toString());

        Assert.assertTrue(gameState.isFullLine(0));
    }
    
    /** Tests the {@link OnePlayerGameState#isFullLine(int)} method in case of a full line. */
    @Test
    public void testIsFullLineAboveBottom() {
        List<Optional<Block>> grid = createEmptyGrid(220);
        // add a line at 1
        for (int x = 10; x != 20; x++) {
            grid.set(x, Optional.of(Block.values()[x % Block.values().length]));
        }
        OnePlayerGameState gameState = new OnePlayerGameState(grid, 10, Optional.empty(), Optional.empty(),
                Optional.empty(), Tetromino.I);
        log.info(gameState.toString());

        Assert.assertFalse(gameState.isFullLine(0));
        Assert.assertTrue(gameState.isFullLine(1));
    }
    
    /** Tests the {@link OnePlayerGameState#isFullLine(int)} method in case of a partially filled line. */
    @Test
    public void testIsFullPartialLineAtBottom() {
        List<Optional<Block>> grid = createEmptyGrid(220);
        grid.set(0, Optional.of(Block.L));
        grid.set(2, Optional.of(Block.L));
        grid.set(3, Optional.of(Block.L));
        grid.set(5, Optional.of(Block.L));
        grid.set(6, Optional.of(Block.L));
        OnePlayerGameState gameState = new OnePlayerGameState(grid, 10, Optional.empty(), Optional.empty(),
                Optional.empty(), Tetromino.I);
        log.info(gameState.toString());

        Assert.assertFalse(gameState.isFullLine(0));
    }
    
    /** Test method for {@link OnePlayerGameState#getBlockSpawnLocation()}. */
    @Test
    public void testGetBlockSpawnLocation() {
        OnePlayerGameState gameState = new OnePlayerGameState(10, 22);
        
        Point point = gameState.getBlockSpawnLocation();
        
        Assert.assertEquals(new Point(3, 16), point);
    }
    
    /** Test case for {@link OnePlayerGameState#isCurrentBlockWithinBounds()}. */
    @Test
    public void test() {
        List<Optional<Block>> grid = createEmptyGrid(220);
        Point blockLocation = new Point(1, 15);
        OnePlayerGameState gameState = new OnePlayerGameState(grid, 10, Tetromino.O, blockLocation, Orientation.getDefault(),
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
    private List<Optional<Block>> createGrid(int size) {
        List<Optional<Block>> values = new ArrayList<>(Block.values().length + 1);
        values.add(Optional.empty());
        values.addAll(Stream.of(Block.values())
                .map(Optional::of)
                .collect(Collectors.toList()));

        List<Optional<Block>> result = new ArrayList<>();
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
    private List<Optional<Block>> createEmptyGrid(int size) {
        return createGrid(size, Optional.empty());
    }

    /**
     * Creates a list of blocks.
     * 
     * @param size
     *            number of items in the list
     * @param value
     *            the block value; all items of the list will have this value
     * @return list
     */
    private List<Optional<Block>> createGrid(int size, Block value) {
        return createGrid(size, Optional.of(value));
    }
    
    /**
     * Creates a list of blocks.
     * 
     * @param size
     *            number of items in the list
     * @param value
     *            the tetromino value; all items of the list will have this value
     * @return list
     */
    private List<Optional<Block>> createGrid(int size, Optional<Block> value) {
        return new ArrayList<>(Collections.nCopies(size, value));
    }
}
