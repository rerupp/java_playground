/*
 * Copyright (c) 2016 Thomson Reuters/ONESOURCE. All rights reserved.
 */

package utils;

import static org.slf4j.helpers.MessageFormatter.arrayFormat;

/**
 * A front-end to the SLF4J message format
 *
 * @author Rick Rupp
 */
public interface MessageFormatter {

	/**
	 * Creates a message following SLF4J argument substitution pattern.
	 *
	 * @param format The SLF4J message pattern that will be parsed and formatted.
	 * @param arg    The argument to be substituted in place of the formatting anchor.
	 * @return a formatted text string.
	 */
	default String format(final String format, final Object arg) {
		return arrayFormat(format, new Object[] {arg}, null).getMessage();
	}

	/**
	 * Creates a message following SLF4J argument substitution pattern.
	 *
	 * @param format The SLF4J message pattern that will be parsed and formatted.
	 * @param arg0   The first argument to be substituted in place of the formatting anchor.
	 * @param arg1   The second argument to be substituted in place of the formatting anchor.
	 * @return a formatted text string.
	 */
	default String format(final String format, final Object arg0, final Object arg1) {
		return arrayFormat(format, new Object[] {arg0, arg1}, null).getMessage();
	}

	/**
	 * Creates a message following SLF4J argument substitution pattern.
	 *
	 * @param format The SLF4J message pattern that will be parsed and formatted.
	 * @param arg0   The first argument to be substituted in place of the formatting anchor.
	 * @param arg1   The second argument to be substituted in place of the formatting anchor.
	 * @param arg2   The third argument to be substituted in place of the formatting anchor.
	 * @return a formatted text string.
	 */
	default String format(final String format, final Object arg0, final Object arg1, final Object arg2) {
		return arrayFormat(format, new Object[] {arg0, arg1, arg2}, null).getMessage();
	}

	/**
	 * Creates a message following SLF4J argument substitution pattern.
	 *
	 * @param format The SLF4J message pattern that will be parsed and formatted.
	 * @param arg0   The first argument to be substituted in place of the formatting anchor.
	 * @param arg1   The second argument to be substituted in place of the formatting anchor.
	 * @param arg2   The third argument to be substituted in place of the formatting anchor.
	 * @param arg3   The forth argument to be substituted in place of the formatting anchor.
	 * @return a formatted text string.
	 */
	default String format(final String format, final Object arg0, final Object arg1, final Object arg2, final Object arg3) {
		return arrayFormat(format, new Object[] {arg0, arg1, arg2, arg3}, null).getMessage();
	}

	/**
	 * Creates a message following SLF4J argument substitution pattern.
	 *
	 * @param format The SLF4J message pattern that will be parsed and formatted.
	 * @param args   The variable list of arguments to be substituted in place of the formatting anchor.
	 * @return a formatted text string.
	 */
	default String format(final String format, final Object... args) {
		return arrayFormat(format, args, null).getMessage();
	}

}
