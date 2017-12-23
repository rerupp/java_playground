package tds;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Description goes here!!!
 *
 * @author Rick Rupp on 6/24/15.
 */
public class LocalExample {

    void example() {

        String foo = "abc";
        Supplier<String> test = new Supplier<String>() {
            @Override
            public String get() {
                return foo;
            }
        };
//        foo = "def";
    }

}
