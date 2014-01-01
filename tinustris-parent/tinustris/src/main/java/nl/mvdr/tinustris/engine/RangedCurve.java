package nl.mvdr.tinustris.engine;

import java.util.Iterator;
import java.util.SortedMap;

import lombok.RequiredArgsConstructor;

/**
 * Representation of a curve of values based on ranges.
 * 
 * @author Martijn van de Rijdt
 */
@RequiredArgsConstructor
class RangedCurve {
    /**
     * Map that represents the ranges. Each range (i, j) is represented by its upper bound, where the lower bound is 0
     * for the first range and the previous range's upper bound otherwise.
     */
    private final SortedMap<Integer, Integer> map;
    /** Default value. */
    private final int defaultValue;

    /**
     * Finds the range (i, j) where i <= key < j and returns the corresponding value; returns the default value if none
     * of the ranges match.
     * 
     * @param key key
     * @return value
     */
    int getValue(int key) {
        Integer result = null;
        Iterator<Integer> iterator = map.keySet().iterator();
        while (result == null && iterator.hasNext()) {
            Integer i = iterator.next();
            if (key < i.intValue()) {
                result = map.get(i);
            }
        }
        
        if (result == null) {
            result = Integer.valueOf(defaultValue);
        }
        
        return result.intValue();
    }
}
