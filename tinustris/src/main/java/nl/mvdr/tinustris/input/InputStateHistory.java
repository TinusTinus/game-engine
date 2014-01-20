package nl.mvdr.tinustris.input;

public interface InputStateHistory {
    /**
     * Retrieves the number of frames the given input has been pressed.
     * 
     * @param input input
     * @return number of frames
     */
    public abstract int getNumberOfFrames(Input input);
    
    /**
     * Creates the input state history for the next frame.
     * 
     * @param inputState input state for the next frame
     * @return new input state history
     */
    public abstract InputStateHistory next(InputState inputState);
}