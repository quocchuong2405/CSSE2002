package srg.exceptions;

/**
 * Thrown when a neccessary resource is unavailable or a room is broken.
 */
public class InsufficientResourcesException extends Exception {
    /**
     * Constructs an InsufficientResourcesException with no message.
     */
    public InsufficientResourcesException() {}

    /**
     * Constructs an InsufficientResourcesException that contains a message explaining why the exception occurred.
     * @param message String message containing details of the exception. 
     */
    public InsufficientResourcesException(String message) {
        super(message);
    }
}
