package jdk8.lambdas.demo;

import org.apache.commons.collections4.CollectionUtils;
import utils.ListConverter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.BiPredicate;

// add the VM option -ea to enable assertions at run time

@SuppressWarnings("JavaDoc")
class DemoRunner {

	public static void main(final String... ignore) {

		final List<InPojo> source = Collections.unmodifiableList(Arrays.asList(new InPojo("Rick", "Rupp"),
																			   new InPojo("foo", "bar"),
																			   new InPojo("bar", "foo")));
		new IterateExample().run(source);
		new ListConverterExample().run(source);
	}

	// the pojo compare tools
	private static final BiPredicate<InPojo, OutPojo> inOutPojoPredicate =
			(lhs, rhs) -> lhs.getFirstName().equals(rhs.getFirstName()) && lhs.getLastName().equals(rhs.getLastName());
	private static final BiPredicate<InPojo, InPojo> inPojoPredicate =
			(lhs, rhs) -> lhs.getFirstName().equals(rhs.getFirstName()) && lhs.getLastName().equals(rhs.getLastName());

	private static <L, R> void compare(final List<L> lhs, final List<R> rhs, final BiPredicate<L, R> predicate) {
		assert Objects.requireNonNull(lhs, "lhs cannot be null").size() == Objects.requireNonNull(rhs, "rhs cannot be null").size();
		for (int i = 0; i < lhs.size(); i++) {
			assert predicate.test(lhs.get(i), rhs.get(i));
		}
	}

	private static class IterateExample {

		void run(final List<InPojo> source) {

			// convert to out
			final List<OutPojo> outPojos = toOutPojo(source);
			compare(source, outPojos, inOutPojoPredicate);

			// convert back to source
			final List<InPojo> sourceRoundTripped = toInPojo(outPojos);
			compare(source, sourceRoundTripped, inPojoPredicate);
		}

		List<InPojo> toInPojo(final List<OutPojo> outPojos) {
			if (CollectionUtils.isEmpty(outPojos)) {
				return Collections.emptyList();
			}
			final List<InPojo> inPojos = new ArrayList<>(outPojos.size());
			outPojos.forEach((pojo) -> inPojos.add(new InPojo(pojo.getFirstName(), pojo.getLastName())));
			return inPojos;

		}

		List<OutPojo> toOutPojo(final List<InPojo> inPojos) {
			if (CollectionUtils.isEmpty(inPojos)) {
				return Collections.emptyList();
			}
			final List<OutPojo> outPojos = new ArrayList<>(inPojos.size());
			inPojos.forEach((pojo) -> outPojos.add(new OutPojo(pojo.getFirstName(), pojo.getLastName())));
			return outPojos;
		}

	}

	private static class ListConverterExample implements ListConverter {

		void run(final List<InPojo> source) {

			// convert to out
			final List<OutPojo> outPojos = convert(source, PojoConverter::toOutPojo);
			compare(source, outPojos, inOutPojoPredicate);

			// convert back to source
			final List<InPojo> sourceRoundTripped = convert(outPojos, PojoConverter::toInPojo);
			compare(source, sourceRoundTripped, inPojoPredicate);

		}

	}

}
