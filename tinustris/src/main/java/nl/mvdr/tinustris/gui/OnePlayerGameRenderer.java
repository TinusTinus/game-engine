package nl.mvdr.tinustris.gui;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.Paint;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import nl.mvdr.tinustris.model.OnePlayerGameState;

/**
 * Group containing all components to render a single player game.
 * 
 * @author Martijn van de Rijdt
 */
class OnePlayerGameRenderer extends Group implements GameRenderer<OnePlayerGameState> {
    /** Size of the border around the Tetris grid and other UI components. */
    private static final int BORDER_SIZE = 10;
    /** Size of the margin between windows. */
    private static final int MARGIN = 10;
    /** Width of a text window. */
    private static final int TEXT_WINDOW_HEIGHT = 50;
    /** Width of the game over label. */
    private static final int GAME_OVER_LABEL_WIDTH = 170;
    /** Size for the arc of a window. */
    private static final int ARC_SIZE = 10;
    
    /** Renderers. */
    @Getter(value = AccessLevel.PACKAGE)
    private final List<GameRenderer<OnePlayerGameState>> renderers;
    
    /**
     * Constructor.
     * 
     * @param playerName
     *            player's name
     * @param widthInBlocks
     *            width of the grid in blocks
     * @param heightInBlocks
     *            height of the part of the grid to be displayed (not including the vanish zone!), in blocks
     * @param blockCreator
     *            block creator
     */
    OnePlayerGameRenderer(@NonNull String playerName, int widthInBlocks, int heightInBlocks, @NonNull BlockCreator blockCreator) {
        super();
        
        GridRenderer gridGroup = new GridRenderer(blockCreator);
        NextBlockRenderer nextBlockRenderer = new NextBlockRenderer(blockCreator);
        LinesRenderer linesRenderer = new LinesRenderer();
        LevelRenderer levelRenderer = new LevelRenderer();
        GameOverRenderer gameOverRenderer = new GameOverRenderer();
        renderers = Collections.unmodifiableList(
            Arrays.asList(gridGroup, nextBlockRenderer, linesRenderer, levelRenderer, gameOverRenderer));
        
        Group gridWindow = createWindow(playerName.toUpperCase(), gridGroup, MARGIN, MARGIN, widthInBlocks
                * BlockGroupRenderer.BLOCK_SIZE, heightInBlocks * BlockGroupRenderer.BLOCK_SIZE);
        Group nextBlockWindow = createWindow("NEXT", nextBlockRenderer,
                2 * MARGIN + widthInBlocks * BlockGroupRenderer.BLOCK_SIZE + 2 * BORDER_SIZE,
                MARGIN,
                4 * BlockGroupRenderer.BLOCK_SIZE,
                4 * BlockGroupRenderer.BLOCK_SIZE);
        Group linesWindow = createWindow("LINES", linesRenderer,
                2 * MARGIN + widthInBlocks * BlockGroupRenderer.BLOCK_SIZE + 2 * BORDER_SIZE,
                2 * MARGIN + 2 * BORDER_SIZE + 4 * BlockGroupRenderer.BLOCK_SIZE,
                4 * BlockGroupRenderer.BLOCK_SIZE,
                TEXT_WINDOW_HEIGHT);
        Group levelWindow = createWindow("LEVEL", levelRenderer,
                2 * MARGIN + widthInBlocks * BlockGroupRenderer.BLOCK_SIZE + 2 * BORDER_SIZE,
                3 * MARGIN + 4 * BORDER_SIZE + 4 * BlockGroupRenderer.BLOCK_SIZE + TEXT_WINDOW_HEIGHT,
                4 * BlockGroupRenderer.BLOCK_SIZE,
                TEXT_WINDOW_HEIGHT);
        Group gameOverWindow = createWindow("", gameOverRenderer,
                (MARGIN + widthInBlocks * BlockGroupRenderer.BLOCK_SIZE) / 2 - GAME_OVER_LABEL_WIDTH / 2,
                (MARGIN + heightInBlocks * BlockGroupRenderer.BLOCK_SIZE) / 2 - TEXT_WINDOW_HEIGHT / 2,
                GAME_OVER_LABEL_WIDTH,
                TEXT_WINDOW_HEIGHT);
        gameOverWindow.setVisible(false);
        
        getChildren().addAll(gridWindow, nextBlockWindow, linesWindow, levelWindow, gameOverWindow);
    }
    
    /**
     * Creates a red-bordered window containing the given node.
     * 
     * @param title
     *            window title
     * @param contents
     *            contents of the window
     * @param x
     *            x coordinate
     * @param y
     *            coordinate
     * @param contentsWidth
     *            width of the contents
     * @param contentsHeight
     *            height of the contents
     * @return group containing the window and the contents
     */
    private Group createWindow(String title, Node contents, double x, double y, double contentsWidth,
            double contentsHeight) {
        // bounding red rectangle
        Rectangle border = new Rectangle(x + BORDER_SIZE, y + BORDER_SIZE, contentsWidth, contentsHeight);
        border.setFill(null);
        
        Paint stroke = new RadialGradient(0,
                1,
                border.getX() + border.getWidth() / 2,
                border.getY() + border.getHeight() / 2,
                border.getWidth(),
                false,
                CycleMethod.NO_CYCLE,
                new Stop(0, Color.WHITE),
                new Stop(1, Color.DARKRED));
        
        border.setStroke(stroke);
        border.setStrokeWidth(BORDER_SIZE);
        border.setStrokeType(StrokeType.OUTSIDE);
        border.setArcWidth(ARC_SIZE);
        border.setArcHeight(ARC_SIZE);
        
        // black, seethrough background, to dim whatever is behind the window
        Rectangle background = new Rectangle(border.getX(), border.getY(), border.getWidth(), border.getHeight());
        background.setFill(Color.BLACK);
        background.setOpacity(.5);
        background.setArcWidth(ARC_SIZE);
        background.setArcHeight(ARC_SIZE);
        
        contents.setTranslateX(x + BORDER_SIZE);
        contents.setTranslateY(y + BORDER_SIZE);
        
        Label label = new Label(title);
        label.setTextFill(Color.WHITE);
        label.setFont(new Font(10));
        label.setLayoutX(border.getX() + 2);
        label.setLayoutY(border.getY() - BORDER_SIZE - 2);
        
        return new Group(background, border, contents, label);
    }
    
    /** {@inheritDoc} */
    @Override
    public void render(@NonNull OnePlayerGameState gameState) {
        renderers.forEach(renderer -> renderer.render(gameState));
    }
}
