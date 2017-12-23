package env;

import org.apache.commons.lang3.text.StrBuilder;

import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

/**
 * Dump the system properties.
 *
 * @author Rick Rupp
 */
@SuppressWarnings("JavaDoc")
public class ShowSystemProperties {

    public static void main(final String... args) {

        final Map<String, String> sortedProperties = new TreeMap<>();

        final Properties properties = System.getProperties();
        properties.stringPropertyNames().forEach(key -> sortedProperties.put(key, properties.getProperty(key)));

        final StrBuilder builder = new StrBuilder().appendln("System properties:");
        sortedProperties.forEach((key, value) -> builder.append("  ").append(key).append(": ").appendln(value));
        //noinspection UseOfSystemOutOrSystemErr
        System.out.println(builder.build());
    }
}
