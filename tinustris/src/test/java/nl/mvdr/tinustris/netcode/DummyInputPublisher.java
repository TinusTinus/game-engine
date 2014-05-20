package nl.mvdr.tinustris.netcode;

import lombok.extern.slf4j.Slf4j;
import nl.mvdr.tinustris.model.FrameAndInputStatesContainer;

/**
 * Dummy implementation of {@link InputPublisher}.
 * 
 * @author Martijn van de Rijdt
 */
@Slf4j
public class DummyInputPublisher implements InputPublisher {
    /** {@inheritDoc} */
    @Override
    public void publish(FrameAndInputStatesContainer states) {
        log.debug("Publish {}", states);
    }
}
