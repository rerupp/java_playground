package jdk8.lambdas;

import java.util.function.Supplier;

// add the VM option -ea to enable assertions at run time

@SuppressWarnings("JavaDoc")
public class SupplierDemo {

	public static void main(final String... ignore) {

		final String value = "value";

		final Supplier<String> supplier = new Supplier<String>() {
			@Override
			public String get() {
				return value;
			}
		};
		assert value.equals(supplier.get());
		assert value.equals(supplierGetter(supplier));

		@SuppressWarnings("LocalCanBeFinal")
		Integer effectivelyFinal = 10;
		assert 10 == supplierGetter(() -> effectivelyFinal);

		// breaks effectively final
//		effectivelyFinal++;
	}

	private static <T> T supplierGetter(final Supplier<T> supplier) {
		return supplier.get();
	}

}
