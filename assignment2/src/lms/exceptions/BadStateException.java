package lms.exceptions;

/**
 * A special RuntimeException that represents a bad state exception. Unlike normal checked
 * exceptions that extend from Exception class, this exception is an unchecked RuntimeException
 * which allows it to be added to a method without needing to have it in the method signature. This
 * class should be used when the system encounters a state that is not expected or invalid, and it
 * cannot proceed without resolving the issue.
 */
public class BadStateException extends RuntimeException {

    /**
     * Constructs a new BadStateException with no message.
     */
    public BadStateException() {
        super();
    }

    /**
     * Constructs a new BadStateException with the specified error message.
     *
     * @param message the error message to be associated with this exception.
     */
    public BadStateException(String message) {
        super(message);
    }

    /**
     * Constructs a new BadStateException with the specified detail message and cause.
     *
     * @param message the detail message to be associated with this exception (which is saved for
     *                later retrieval by the getMessage() method).
     * @param cause   the cause (which is saved for later retrieval by the getCause() method).
     * @see Throwable#getMessage()
     * @see Throwable#getCause()
     */
    public BadStateException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new BadStateException with the specified cause.
     *
     * @param cause the cause (which is saved for later retrieval by the getCause() method).
     * @see Throwable#getCause()
     */
    public BadStateException(Throwable cause) {
        super(cause);
    }

}
