package junit.assertions;

/**
 * This error will be thrown by the {@link ExceptionAssertion} class when an exception is
 * expected by the assertion however one is not thrown.
 *
 * @author Rick Rupp
 */
public class NotThrownException extends RuntimeException {

    private static final long serialVersionUID = -6549880697291736145L;

    /**
     * Create the error with a default message.
     */
    public NotThrownException() {
        this("Expected and exception to the thrown");
    }

    /**
     * Create the error with a specific message.
     * @param message
     */
    @SuppressWarnings("WeakerAccess")
    public NotThrownException(final String message) {
        super(message);
    }

}
