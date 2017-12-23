/*
 * Copyright (c) 2016 Thomson Reuters/ONESOURCE. All rights reserved.
 */

package determination.cloud.auth.authorization.handlers;

import determination.cloud.auth.authorization.annotations.Authorization;
import org.aspectj.lang.JoinPoint;

/**
 * The API used to access the authorization handler manager.
 *
 * @author Rick Rupp
 */
public interface AuthorizeHandlerManager {

	/**
	 * Used to authorize access to functionality. If authorization is not granted an exception
	 * will be thrown.
	 *
	 * @param authorizations the permission required to access the functionality.
	 * @param joinPoint      the join point associated with the functionality.
	 */
	void authorize(Authorization[] authorizations, JoinPoint joinPoint);

}
