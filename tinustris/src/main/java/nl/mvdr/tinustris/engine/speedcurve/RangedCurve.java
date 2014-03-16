package nl.mvdr.tinustris.engine.speedcurve;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
    private final Map<Integer, Integer> map;

    /**
     * Finds the range (i, j) where i <= key < j and returns the corresponding value.
     * 
     * @param key key; may not be less than the lower bound of the first range
     * @return value
     * @throws NoSuchElementException if key is less than the lower bound
     */
    int getValue(int key) {
        Integer mapKey = map.keySet()
            .stream()
            .filter(i -> i <= key)
            .max(Integer::compare)
            .get();

        return map.get(mapKey).intValue();
    }
    
    /** {@inheritDoc} */
    @Override
    public String toString() {
        List<Entry<Integer, Integer>> entryList = map.entrySet()
            .stream()
            .sorted((entry0, entry1) -> entry0.getKey().compareTo(entry1.getKey()))
            .collect(Collectors.toList());
        
        return IntStream.range(0, map.size() - 1)
            .mapToObj(i -> String.format("(%s, %s) -> %s, ", entryList.get(i).getKey(), entryList.get(i + 1).getKey(), entryList.get(i).getValue()))
            .collect(StringBuilder::new, (builder, string) -> builder.append(string), StringBuilder::append)
            .append(String.format("(%s, infinity) -> %s", entryList.get(entryList.size() - 1).getKey(), entryList.get(entryList.size() - 1).getValue()))
            .toString();
    }
}
