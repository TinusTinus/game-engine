package nl.mvdr.tinustris.engine;

import java.util.Iterator;
import java.util.SortedMap;

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
     * Map that represents the ranges. Each range (i, j) is represented by its lower bound i, where the upper bound is
     * the next range's lower bound, or infinity if it is the last one.
     * 
     * Should not be null or empty.
     */
    @NonNull
    private final SortedMap<Integer, Integer> map;

    /**
     * Finds the range (i, j) where i <= key < j and returns the corresponding value; returns the default value if none
     * of the ranges match.
     * 
     * @param key key; may not be less than the lower bound of the first range
     * @return value
     */
    int getValue(int key) {
        Integer result = null;
        Integer previousMapKey = null;
        Iterator<Integer> iterator = map.keySet().iterator();
        while (result == null && iterator.hasNext()) {
            Integer mapKey = iterator.next();
            if (key < mapKey.intValue()) {
                result = map.get(previousMapKey);
            }
            previousMapKey = mapKey;
        }
        if (result == null) {
            result = map.get(previousMapKey);
        }
        return result.intValue();
    }
}
