/*
 * Copyright (c) 2016 Thomson Reuters/ONESOURCE. All rights reserved.
 */

package determination.cloud.auth.examples.common.authorization;

import determination.cloud.auth.authorization.Action;
import determination.cloud.auth.authorization.AuthorizationComponent;
import determination.cloud.auth.authorization.AuthorizeHandlerException;
import determination.cloud.auth.authorization.NotAuthorizedException;
import determination.cloud.auth.authorization.annotations.Authorization;
import determination.cloud.auth.authorization.handlers.AbstractAuthorizeHandler;
import determination.cloud.auth.examples.common.dtos.CommonDTO;
import org.aspectj.lang.JoinPoint;

/**
 * The bar package authorization handler.
 *
 * @author Rick Rupp
 */
public class CommonAuthorizeHandler extends AbstractAuthorizeHandler {

	@Override
	public void authorize(final Authorization authorization, final JoinPoint joinPoint) {

		requireNotNull(authorization, "Authorization cannot be null...");
		final String signature = requireNotNull(joinPoint, "The JoinPoint cannot be null...").toLongString();
		getLogger().info(signature);

		// get the function that will mine the authorization parameter
		final Object argument = getAuthorizeParameterFunction(authorization, joinPoint).apply(joinPoint.getArgs());
		if (argument instanceof Long) {
			checkAuthorization(authorization.component(), authorization.action(), (Long) argument);

		} else if (argument instanceof CommonDTO) {
			checkAuthorization(authorization.component(), authorization.action(), ((CommonDTO) argument).getId());

		} else if (null == argument) {
			checkAuthorization(authorization.component(), authorization.action(), null);

		} else {
			throw new AuthorizeHandlerException(format("'{}' is not a supported authorization parameter...", argument.getClass().getCanonicalName()));
		}
	}

	private void checkAuthorization(final AuthorizationComponent component, final Action action, final Long id) {
		if (!CommonAuthorization.isAuthorized(component, action, id)) {
			throw new NotAuthorizedException(format("'{}' access is not allowed...", action));
		}
	}

}
