package junit.assertions;

import static org.junit.Assert.assertTrue;

/**
 * A simple exception asserter.
 *
 * @author Rick Rupp
 */
@SuppressWarnings("WeakerAccess")
public class ExceptionAssertion {

    public static ExceptionAssertion assertThrown(final ExceptionThrower exceptionThrower) {
        return assertThrown("Expected an exception to be thrown", exceptionThrower);
    }

    public static ExceptionAssertion assertThrown(final String message, final ExceptionThrower exceptionThrower) {
        try {
            exceptionThrower.throwException();
        } catch (final Exception exception) {
            return new ExceptionAssertion(exception);
        }
        throw new NotThrownException(message);
    }

    private final Exception exceptionThrown;

    private ExceptionAssertion(final Exception exceptionThrown) {
        this.exceptionThrown = exceptionThrown;
    }

    public ExceptionAssertion isInstanceOf(final Class<? extends Exception> expectedException) {
        return isInstanceOf("did not expect " + exceptionThrown.getClass().getName(), expectedException);
    }

    public ExceptionAssertion isInstanceOf(final String message, final Class<? extends Exception> expectedException) {
        assertTrue(message, expectedException.isInstance(exceptionThrown));
        return this;
    }

}
