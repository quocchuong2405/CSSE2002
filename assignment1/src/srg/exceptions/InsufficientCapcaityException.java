package srg.exceptions;

/** 
 * Thrown when there is not enough capacity to store a resource. 
 */
public class InsufficientCapcaityException extends Exception {

    /**
     * Constructs an InsufficientCapcaityException with no message.
     */
    public InsufficientCapcaityException() {}

    /**
     * Constructs an InsufficientCapcaityException that contains a message explaining why the exception occurred.
     * @param message String message containing details of the exception. 
     */
    public InsufficientCapcaityException(String message) {
        super(message);
    }
}
