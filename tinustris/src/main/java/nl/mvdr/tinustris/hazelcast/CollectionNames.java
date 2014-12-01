package nl.mvdr.tinustris.hazelcast;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

/**
 * Contains constants for the Hazelcast collection names.
 * 
 * @author Martijn van de Rijdt
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class CollectionNames {
    /** List containing the random seeds. */
    public static final String RANDOM_SEED_LIST = "randomSeeds";
}
