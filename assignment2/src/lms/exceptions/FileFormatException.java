package lms.exceptions;

/**
 * The FileFormatException class is an exception that is thrown when a file being read or processed
 * is not in the expected format. This class extends the base Exception class in order to provide
 * custom error handling for file format errors.
 */
public class FileFormatException extends Exception {

    /**
     * Constructs a new FileFormatException with no message.
     */
    public FileFormatException() {
        super();
    }

    /**
     * Constructs a new FileFormatException with the specified error message.
     *
     * @param message A String containing the error message to be associated with this exception.
     */
    public FileFormatException(String message) {
        super(message);
    }

    /**
     * Constructs a new FileFormatException with the specified detail message and line number. These
     * should be used to construct a message for the superclass, in the format: "message text (line:
     * line number)"
     *
     * @param message A String containing the detail message (which is saved for later retrieval by
     *                the getMessage() method)
     * @param lineNum An integer containing the line number where the exception occurred
     */
    public FileFormatException(String message, int lineNum) {
        super(message + " (line: " + lineNum + ")");
    }

    /**
     * Constructs a new FileFormatException with the specified detail message, line number, and cause.
     * Message and line number should be used to construct a message for the superclass, in the
     * format: "message text (line: line number)"
     *
     * @param message A String containing the detail message (which is saved for later retrieval by
     *                the getMessage() method)
     * @param lineNum An integer containing the line number where the exception occurred
     * @param cause   Throwable containing the cause (which is saved for later retrieval by the
     *                getCause() method)
     */
    public FileFormatException(String message, int lineNum, Throwable cause) {
        super(message + " (line: " + lineNum + ")", cause);
    }

    /**
     * Constructs a new FileFormatException with the specified detail message and cause.
     *
     * @param message The detail message (which is saved for later retrieval by the getMessage()
     *                method)
     * @param cause   The cause (which is saved for later retrieval by the getCause() method)
     */
    public FileFormatException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new FileFormatException with the specified cause.
     *
     * @param cause The cause (which is saved for later retrieval by the getCause() method)
     */
    public FileFormatException(Throwable cause) {
        super(cause);
    }

}
