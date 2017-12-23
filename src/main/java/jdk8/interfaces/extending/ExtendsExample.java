package jdk8.interfaces.extending;

import jdk8.interfaces.basics.SimpleExample;
import org.apache.commons.lang3.StringUtils;
import utils.MessageFormatter;

@SuppressWarnings("JavaDoc")
interface ExtendsExample extends SimpleExample, MessageFormatter {

	@Override
	default String prepareName() {
		return StringUtils.trimToEmpty(getName()).toLowerCase();
	}

}
