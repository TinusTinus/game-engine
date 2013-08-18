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
        new GameState(Collections.<Tetromino>emptyList(), 4, Tetromino.L, Tetromino.Z);
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

        for(int x = 0; x != 10; x++) {
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

        for(int x = 0; x != 10; x++) {
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

        gameState.getBlock(-1,  4);
    }
    
    /** Test case for the getBlock method. */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testGetBlockXTooLarge() {
        GameState gameState = new GameState();
        log.info(gameState.toString());

        gameState.getBlock(35,  4);
    }
    
    /** Test case for the getBlock method. */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testGetBlockYTooSmall() {
        GameState gameState = new GameState();
        log.info(gameState.toString());

        gameState.getBlock(4,  -1);
    }
    
    /** Test case for the getBlock method. */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testGetBlockYTooLarge() {
        GameState gameState = new GameState();
        log.info(gameState.toString());

        gameState.getBlock(4,  35);
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

    
    /**
     * Creates a list of tetrominoes.
     * 
     * @param size number of items in the list
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
     * @param size size of the list to be returned
     * @return list containing only null values
     */
    private List<Tetromino> createEmptyGrid(int size) {
        return createGrid(size, null);
    }
    
    /**
     * Creates a list of tetrominoes.
     * 
     * @param size number of items in the list
     * @param value the tetromino value; all items of the list will have this value
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
