package nl.mvdr.tinustris.hazelcast;

import com.hazelcast.core.Client;
import com.hazelcast.core.ClientListener;

/**
 * Functional listener interface for when a client is added to a Hazelcast cluster.
 * 
 * @author Martijn van de Rijdt
 */
@FunctionalInterface
public interface ClientAddedListener extends ClientListener {
    /** {@inheritDoc} */
    @Override
    default void clientDisconnected(Client client) {
        // do nothing
    }
}
