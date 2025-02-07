package monty.exception;

/**
 * Represents a custom exception specific to the Monty application.
 * This exception is used for handling application-specific errors.
 */
public class MontyException extends Exception {

    /**
     * Constructs a {@code MontyException} with the specified detail message.
     *
     * @param message The error message associated with the exception.
     */
    public MontyException(String message) {
        super(message);
    }
}
