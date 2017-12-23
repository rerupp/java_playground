package jdk8.interfaces.multiple;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;

@SuppressWarnings("JavaDoc")
interface AnotherSimpleExample {

	String getName();

	default String prepareName() {
		return WordUtils.capitalizeFully(StringUtils.trimToEmpty(getName()));
	}

}
