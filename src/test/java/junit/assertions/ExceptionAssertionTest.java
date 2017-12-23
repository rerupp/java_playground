package junit.assertions;

import org.junit.Test;


/**
 * Simple tester of the tester...
 *
 * @author Rick Rupp
 */
public class ExceptionAssertionTest {

    @Test
    public void goodTest() throws Exception {
        ExceptionAssertion.assertThrown(() -> testBed(new IllegalArgumentException())).isInstanceOf(IllegalArgumentException.class);
    }

    @Test(expected = AssertionError.class)
    public void badTest() throws Exception {
        ExceptionAssertion.assertThrown(() -> testBed(new Exception())).isInstanceOf(RuntimeException.class);
    }

    private void testBed(final Exception willBeThrown) throws Exception {
        throw willBeThrown;
    }

}