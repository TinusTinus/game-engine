package nl.mvdr.tinustris.netcode;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import nl.mvdr.tinustris.model.FrameAndInputStatesContainer;

/**
 * Dummy implementation of {@link InputPublisher}.
 * 
 * @author Martijn van de Rijdt
 */
@Slf4j
@Getter
@ToString
public class DummyInputPublisher implements InputPublisher {
    /** Published states. */
    private final List<FrameAndInputStatesContainer> publishedStates;
    
    /** Constructor. */
    public DummyInputPublisher() {
        super();
        this.publishedStates = new ArrayList<>();
    }
    
    /** {@inheritDoc} */
    @Override
    public void publish(FrameAndInputStatesContainer states) {
        log.debug("Publish {}", states);
        this.publishedStates.add(states);
    }
}
