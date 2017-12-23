package junit.assertions;

/**
 * The Lambda used by the Exception assertion tester.
 *
 * @author Rick Rupp
 */
@SuppressWarnings("WeakerAccess")
@FunctionalInterface
public interface ExceptionThrower {

    void throwException() throws Exception;

}
