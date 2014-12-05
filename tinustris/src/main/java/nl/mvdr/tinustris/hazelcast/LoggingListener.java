package nl.mvdr.tinustris.hazelcast;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.hazelcast.core.Client;
import com.hazelcast.core.ClientListener;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.MemberAttributeEvent;
import com.hazelcast.core.MembershipEvent;
import com.hazelcast.core.MembershipListener;

import edu.umd.cs.findbugs.annotations.NonNull;

/**
 * Listener implementation which logs all events.
 * 
 * @author Martijn van de Rijdt
 */
@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class LoggingListener implements ClientListener, MembershipListener {
    /**
     * Registers this listener on the given Hazelcast instance.
     * 
     * @param hazelcast Hazelcast instance
     */
    public static void register(@NonNull HazelcastInstance hazelcast) {
        LoggingListener listener = new LoggingListener();
        hazelcast.getClientService().addClientListener(listener);
        hazelcast.getCluster().addMembershipListener(listener);
    }
    
    /** {@inheritDoc} */
    @Override
    public void memberAdded(MembershipEvent membershipEvent) {
        log.info("Member added: {}", membershipEvent);
    }

    /** {@inheritDoc} */
    @Override
    public void memberRemoved(MembershipEvent membershipEvent) {
        log.info("Member removed: {}", membershipEvent);
    }

    /** {@inheritDoc} */
    @Override
    public void memberAttributeChanged(MemberAttributeEvent memberAttributeEvent) {
        log.info("Member attribute changed: {}", memberAttributeEvent);
    }

    /** {@inheritDoc} */
    @Override
    public void clientConnected(Client client) {
        log.info("Client connected: {}", client);
    }

    /** {@inheritDoc} */
    @Override
    public void clientDisconnected(Client client) {
        log.info("Client disconnected: {}", client);
    }
}
