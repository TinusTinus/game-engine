package nl.mvdr.tinustris.engine;

/**
 * Used to generate a value.
 * 
 * @param <S> type of objects to be generated
 * 
 * @author Martijn van de Rijdt
 */
@FunctionalInterface
public interface Generator<S> {
    /**
     * Returns value i.
     * 
     * If get is invoked multiple times with the same index, the same value is returned every time.
     * 
     * @param i
     *            index of the value; must be at least 0
     * @return value
     */
    S get(int i);
}
