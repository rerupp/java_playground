/*
 * Copyright (c) 2016 Thomson Reuters/ONESOURCE. All rights reserved.
 */

package determination.cloud.auth.authorization;

/**
 * An exception that will be thrown by implementations of {@link AuthorizeHandler}.
 *
 * @author Rick Rupp
 */
public class AuthorizeHandlerException extends RuntimeException {

	private static final long serialVersionUID = -4999090876385940844L;

	/**
	 * Constructs a new authorize handler exception with the specified detail message.
	 * @param message the detailed message.
	 */
	public AuthorizeHandlerException(final String message) {
		super(message);
	}

}
