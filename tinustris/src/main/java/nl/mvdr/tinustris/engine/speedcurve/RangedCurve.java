package nl.mvdr.tinustris.engine.speedcurve;

import java.util.Collections;
import java.util.Set;
import java.util.SortedMap;
import java.util.stream.Collectors;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * Representation of a curve of values based on ranges.
 * 
 * @author Martijn van de Rijdt
 */
@RequiredArgsConstructor
class RangedCurve {
    /**
     * Map that represents the ranges. Each range (i, j) is represented by its lower bound i, where the upper bound j is
     * the next range's lower bound, or infinity if it is the last one.
     * 
     * Should not be null or empty.
     */
    @NonNull
    private final SortedMap<Integer, Integer> map;

    /**
     * Finds the range (i, j) where i <= key < j and returns the corresponding value.
     * 
     * @param key key; may not be less than the lower bound of the first range
     * @return value
     */
    int getValue(int key) {
        Set<Integer> keys = map.keySet()
            .stream()
            .filter(i -> i <= key)
            .collect(Collectors.toSet());

        Integer mapKey = Collections.max(keys);
        
        return map.get(mapKey).intValue();
    }
}
