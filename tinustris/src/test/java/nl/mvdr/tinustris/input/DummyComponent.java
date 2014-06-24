package nl.mvdr.tinustris.input;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import net.java.games.input.Component;

/**
 * Dummy implementation of {@link Component}.
 * 
 * @author Martijn van de Rijdt
 */
@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public class DummyComponent implements Component {
    /** Indentifier for this dummy component. */
    private final Identifier identifier;
    /** Whether this dummy component is relative; false means absolute. */
    private final boolean relative;
    /** Whether this dummy component is analog; false means digital. */
    private final boolean analog;
    /** This component's dead zone. */
    private final float deadZone;
    /** Current poll data for this dummy component. */
    private final float pollData;
    /** Name for this component. */
    private final String name;
    
    /**
     * Convenience constructor.
     * 
     * @param identifier identifier for this component
     */
    public DummyComponent(Identifier identifier) {
        this(identifier, false, false, 0, 0, "Dummy");
    }
}
