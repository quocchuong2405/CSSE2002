package lms.exceptions;

/**
 * The UnsupportedActionException class represents an exception that is thrown when an unsupported
 * action is attempted. This class extends the RuntimeException class to allow it to be thrown
 * without the need to declare it in a method signature.
 */
public class UnsupportedActionException extends RuntimeException {

    /**
     * Constructs a new UnsupportedActionException with no message.
     */
    public UnsupportedActionException() {
        super();
    }

    /**
     * Constructs a new UnsupportedActionException with the specified error message.
     *
     * @param message A String containing the error message to be associated with this exception.
     */
    public UnsupportedActionException(String message) {
        super(message);
    }

    /**
     * Constructs a new UnsupportedActionException with the specified error message and cause.
     *
     * @param message A String containing the error message to be associated with this exception.
     * @param cause   A Throwable object representing the cause of this exception.
     */
    public UnsupportedActionException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new UnsupportedActionException with the specified cause.
     *
     * @param cause A Throwable object representing the cause of this exception.
     */
    public UnsupportedActionException(Throwable cause) {
        super(cause);
    }

}
