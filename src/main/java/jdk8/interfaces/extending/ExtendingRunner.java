package jdk8.interfaces.extending;

import jdk8.interfaces.basics.SimpleExample;
import utils.MessageFormatter;

import static jdk8.interfaces.basics.StaticExample.show;

@SuppressWarnings("JavaDoc")
public class ExtendingRunner {

	public static void main(final String... ignore) {

		// simple extends example
		final SampleOne sampleOne = new SampleOne("sAmPlEoNe");
		show(sampleOne.format("getName: {}", sampleOne.getName()));
		show(sampleOne.format("prepareName: {}", sampleOne.prepareName()));

		// most specific default-providing interface example
		final SampleTwo sampleTwo = new SampleTwo("SaMpLeTwO");
		show(sampleTwo.format("getName: {}", sampleTwo.getName()));
		show(sampleTwo.format("prepareName: {}", sampleTwo.prepareName()));

	}

	private static class SampleOne implements ExtendsExample {

		private final String name;

		SampleOne(final String name) {
			this.name = name;
		}

		@Override
		public String getName() {
			return name;
		}
	}

	// a class can implement interfaces another interface has extended
	private static class SampleTwo implements ExtendsExample, SimpleExample, MessageFormatter {

		private final String name;

		SampleTwo(final String name) {
			this.name = name;
		}

		@Override
		public String getName() {
			return name;
		}
	}

}
