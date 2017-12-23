package jdk8.interfaces.basics;

import utils.MessageFormatter;

@SuppressWarnings("JavaDoc")
public class BasicsRunner {

	public static void main(final String... ignore) {

		final Sample sample = new Sample("SaMpLe");

		StaticExample.show(sample.format("getName: {}", sample.getName()));
		StaticExample.show(sample.format("prepareName: {}", sample.prepareName()));

		// what's up with this?
//		StaticExampleImpl.show("something");
	}

	private static class Sample implements SimpleExample, MessageFormatter {

		private final String name;

		Sample(final String name) {
			this.name = name;
		}

		@Override
		public String getName() {
			return name;
		}
	}

	static class StaticExampleImpl implements StaticExample {
	}

}
