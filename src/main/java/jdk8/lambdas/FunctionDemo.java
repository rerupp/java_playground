package jdk8.lambdas;

import jdk8.interfaces.basics.SimpleExample;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import static jdk8.interfaces.basics.StaticExample.show;

// add the VM option -ea to enable assertions at run time

@SuppressWarnings("JavaDoc")
public class FunctionDemo {

	public static void main(final String... ignore) {

		// demonstrate Intellij is your friend
		assert "test".equals(toString("test", new Function<String, String>() {
			@Override
			public String apply(final String value) {
				return value;
			}
		}));
		assert "10".equals(toString(10, new Function<Integer, String>() {
			@Override
			public String apply(final Integer value) {
				return Objects.toString(value);
			}
		}));

		// demonstrate object binding
		final BiFunction<Integer, Integer, Integer> adder = new BiFunction<Integer, Integer, Integer>() {
			@Override
			public Integer apply(final Integer left, final Integer right) {
				return left + right;
			}
		};
		int left = 2;
		int right = 3;
		final Supplier<Integer> first = getSupplier(left, right, adder);
		left *= 2;
		right *= 2;
		final Supplier<Integer> second = getSupplier(left, right, adder);
		assert 5 == first.get();
		assert 10 == second.get();

		// create a string concat lambda and track the number of times it's called
		final LongAdder concaterCalls = new LongAdder();
		final BiFunction<String, String, String> concater = new BiFunction<String, String, String>() {
			@Override
			public String apply(final String lhs, final String rhs) {
				show("calling concater");
				concaterCalls.increment();
				return StringUtils.trimToEmpty(lhs) + StringUtils.trimToEmpty(rhs);
			}
		};
		final Supplier<String> stringSupplier = getSupplier("foo", "bar", concater);
		stringSupplier.get();
		stringSupplier.get();
		assert 2 == concaterCalls.sum();

		// create a lambda from the basic interfaces example
		final SimpleExample simpleExample = () -> "inferred Lambda";
		assert "INFERRED LAMBDA".equals(simpleExample.prepareName());

		// demonstrate specialization
		final ToString<Long> longStringifier = value -> (null == value) ? "?" : value.toString();
		assert "5".equals(longStringifier.get(5L));

		final ToString<Integer> integerStringifier = value -> (null == value) ? "0" : Integer.toBinaryString(value);
		assert "1010".equals(integerStringifier.get(10));

	}

	private static <T> String toString(final T value, final Function<T, String> function) {
		return function.apply(value);
	}

	private static <T> Supplier<T> getSupplier(final T left, final T right, final BiFunction<T, T, T> function) {
		return () -> function.apply(left, right);
	}

	// create a specialized function definition
	private interface ToString<T> extends Function<T, String> {
		default String get(final T value) {
			return apply(value);
		}
	}
}
