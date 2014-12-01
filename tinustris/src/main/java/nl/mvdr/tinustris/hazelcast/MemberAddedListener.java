package nl.mvdr.tinustris.hazelcast;

import com.hazelcast.core.MemberAttributeEvent;
import com.hazelcast.core.MembershipEvent;
import com.hazelcast.core.MembershipListener;

/**
 * Functional listener interface for when a member is added to a Hazelcast cluster.
 * 
 * @author Martijn van de Rijdt
 */
@FunctionalInterface
public interface MemberAddedListener extends MembershipListener {
    /** {@inheritDoc} */
    @Override
    default void memberRemoved(MembershipEvent membershipEvent) {
        // do nothing
    }
    
    /** {@inheritDoc} */
    @Override
    default void memberAttributeChanged(MemberAttributeEvent memberAttributeEvent) {
        // do nothing
    }
}
