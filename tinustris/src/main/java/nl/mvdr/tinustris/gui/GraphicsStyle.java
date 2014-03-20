package nl.mvdr.tinustris.gui;

import java.util.function.Supplier;

import javafx.application.ConditionalFeature;
import javafx.application.Platform;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * The style for blocks in the grid.
 * 
 * @author Martijn van de Rijdt
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum GraphicsStyle {
    /** 2D graphics. */
    TWO_DIMENSIONAL("2D", () -> new RectangleBlockCreator(), true),
    /** Real-time 3D graphics. May not be supported in all runtimes. */
    THREE_DIMENSIONAL("3D", () -> new BoxBlockCreator(), Platform.isSupported(ConditionalFeature.SCENE3D));
    
    /** Name of this enum value. */
    @Getter
    private final String name;
    /** Factory for {@link BlockCreator}. */
    private final Supplier<BlockCreator> blockCreatorFactory;
    /** Indicator whether this graphical style is available at runtime. */
    @Getter
    private final boolean available;
    
    /**
     * Creates a new {@link BlockCreator} instance for this graphical style.
     * 
     * @return new block creator instance
     */
    BlockCreator makeBlockCreator() {
        return blockCreatorFactory.get();
    }
    
    /** {@inheritDoc} */
    @Override
    public String toString() {
        return getName();
    }
}
