package srg.exceptions;

/**
 * Thrown when no flight or jump path to a specified {@link SpacePort} can be found.
 */
public class NoPathException extends Exception {
    /**
     * Constructs a NoPathException with no message.
     */
    public NoPathException() {}

    /**
     * Constructs a NoPathException that contains a message explaining why the exception occurred.
     * @param message String message containing details of the exception. 
     */
    public NoPathException(String message) {
        super(message);
    }
}
