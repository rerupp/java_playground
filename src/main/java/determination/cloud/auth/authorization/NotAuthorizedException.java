/*
 * Copyright (c) 2016 Thomson Reuters/ONESOURCE. All rights reserved.
 */

package determination.cloud.auth.authorization;

/**
 * What's thrown if the caller is not authorized to access the manager.
 *
 * @author Rick Rupp
 */
public class NotAuthorizedException extends RuntimeException {

	private static final long serialVersionUID = -4966045082824055017L;

	/**
	 * The exception thrown when a caller is not authorized to access a manager.
	 *
	 * @param message the message indicating why a caller cannot access the manager.
	 */
	public NotAuthorizedException(final String message) {
		super(message);
	}
}
