package nl.mvdr.tinustris.input;

/**
 * Exception class that there are no suitable controllers for JInput.
 * 
 * @author Martijn van de Rijdt
 */
@SuppressWarnings("serial") // not serialised
public class NoSuitableControllerException extends Exception {
    /** Constructor. */
    public NoSuitableControllerException() {
        super();
    }

    /**
     * Constructor.
     * 
     * @param message exception message
     */
    public NoSuitableControllerException(String message) {
        super(message);
    }

    /**
     * Constructor.
     * 
     * @param cause cause
     */
    public NoSuitableControllerException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructor.
     * 
     * @param message exception message
     * @param cause cause
     */
    public NoSuitableControllerException(String message, Throwable cause) {
        super(message, cause);
    }
}
