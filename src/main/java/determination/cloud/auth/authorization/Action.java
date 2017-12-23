/*
 * Copyright (c) 2016 Thomson Reuters/ONESOURCE. All rights reserved.
 */

package determination.cloud.auth.authorization;

/**
 * The actions that can be preformed by an application.
 *
 * @author Rick Rupp
 */
public enum Action {

	/**
	 * An action associated with create APIs.
	 */
	CREATE,

	/**
	 * An action associated with read APIs.
	 */
	READ,

	/**
	 * An action associated with update APIs.
	 */
	UPDATE,

	/**
	 * An action associated with delete APIs.
	 */
	DELETE

}
