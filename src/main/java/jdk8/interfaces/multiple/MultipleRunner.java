package jdk8.interfaces.multiple;

import jdk8.interfaces.basics.SimpleExample;
import utils.MessageFormatter;

import static jdk8.interfaces.basics.StaticExample.show;

@SuppressWarnings("JavaDoc")
public class MultipleRunner {

	public static void main(final String... ignore) {

		// simple extends example
		final Sample sample = new Sample("sAmPlE");
		show(sample.format("getName: {}", sample.getName()));
		show(sample.format("prepareName: {}", sample.prepareName()));

	}

	private static class Sample implements AnotherSimpleExample, SimpleExample, MessageFormatter {

		private final String name;

		Sample(final String name) {
			this.name = name;
		}

		@Override
		public String getName() {
			return name;
		}

		@Override
		public String prepareName() {
//			return AnotherSimpleExample.super.prepareName();
			return SimpleExample.super.prepareName();
		}

	}

}
