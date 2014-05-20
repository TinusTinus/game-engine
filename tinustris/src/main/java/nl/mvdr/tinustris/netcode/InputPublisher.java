package nl.mvdr.tinustris.netcode;

import nl.mvdr.tinustris.model.FrameAndInputStatesContainer;

/**
 * Publishes locally given inputs to any interested remote sessions.
 * 
 * @author Martijn van de Rijdt
 */
@FunctionalInterface
public interface InputPublisher {
    /**
     * Publishes the given states.
     * 
     * @param states
     *            states to be published
     */
    void publish(FrameAndInputStatesContainer states);
}
