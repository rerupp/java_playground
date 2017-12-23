package utils;

import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * A utility API that provides the boiler plate code to convert the contents of a list from one
 * data type to another data type.
 *
 * @author Rick Rupp
 */
public interface ListConverter {

	/**
	 * Creates a copy of the input list converting each input element to another data type using the converter
	 * function. If the input list is {@code null} or empty the supplier will be called to get the output list
	 * that will be returned.
	 *
	 * @param input     the input list that will be converted.
	 * @param converter the function that will be called to convert each element of the input list (must not be {@code null}).
	 * @param <I>       the data type of the input list.
	 * @param <O>       the data type of the output list.
	 * @return the result of the list conversion.
	 */
	default <I, O> List<O> convert(final List<I> input, final Function<I, O> converter) {

		if (CollectionUtils.isEmpty(input)) {
			return Collections.emptyList();
		}

		Objects.requireNonNull(converter, "The function to convert input types to output types cannot be null...");
		return input.stream()
					.map(converter)
					.collect(Collectors.toCollection(() -> new ArrayList<>(input.size())));
	}

}
