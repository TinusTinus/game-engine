package nl.mvdr.tinustris.input;

public interface InputStateHistory {
    /** Input state history where no inputs have been pressed. */
    public static final InputStateHistory NEW = new AbstractInputStateHistory() {
        /** {@inheritDoc} */
        @Override
        public int getNumberOfFrames(Input input) {
            return 0;
        }
        
        /** {@inheritDoc} */
        @Override
        public String toString() {
            return InputStateHistory.class.getSimpleName() + ".NEW";
        }
    };
    
    /**
     * Retrieves the number of frames the given input has been pressed.
     * 
     * @param input input
     * @return number of frames
     */
    int getNumberOfFrames(Input input);
    
    /**
     * Creates the input state history for the next frame.
     * 
     * @param inputState input state for the next frame
     * @return new input state history
     */
    InputStateHistory next(InputState inputState);
}