package jdk8.interfaces.basics;

import org.apache.commons.lang3.StringUtils;

@SuppressWarnings("JavaDoc")
public interface SimpleExample {

	String getName();

	default String prepareName() {
		return StringUtils.trimToEmpty(getName()).toUpperCase();
	}

}
