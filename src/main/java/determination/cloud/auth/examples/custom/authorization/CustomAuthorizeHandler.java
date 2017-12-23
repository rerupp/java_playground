/*
 * Copyright (c) 2016 Thomson Reuters/ONESOURCE. All rights reserved.
 */

package determination.cloud.auth.examples.custom.authorization;

import determination.cloud.auth.authorization.Action;
import determination.cloud.auth.authorization.AuthorizationComponent;
import determination.cloud.auth.authorization.NotAuthorizedException;
import determination.cloud.auth.authorization.annotations.Authorization;
import determination.cloud.auth.authorization.handlers.AbstractAuthorizeHandler;
import determination.cloud.auth.examples.common.authorization.CommonAuthorization;
import determination.cloud.auth.examples.custom.dtos.CustomDTO;
import org.aspectj.lang.JoinPoint;

import java.util.function.Function;

/**
 * The custom package authorization handler.
 *
 * @author Rick Rupp
 */
public class CustomAuthorizeHandler extends AbstractAuthorizeHandler {

	@Override
	public void authorize(final Authorization authorization, final JoinPoint joinPoint) {

		requireNotNull(authorization, "Authorization cannot be null...");
		final String signature = requireNotNull(joinPoint, "The JoinPoint cannot be null...").toLongString();
		getLogger().info(signature);

		// get the function that will mine the authorization parameter
		final Function<Object[], Object> authorizationParameterFunction = getAuthorizeParameterFunction(authorization, joinPoint);

		final Object argument = authorizationParameterFunction.apply(joinPoint.getArgs());
		if (argument instanceof Long) {
			checkAuthorization(authorization.component(), authorization.action(), (Long) argument);

		} else if (argument instanceof CustomDTO) {
			checkAuthorization(authorization.component(), authorization.action(), ((CustomDTO) argument).getId());

		} else {
			throw new IllegalArgumentException(String.format("Custom authorization does not support a '%s' argument...",
															 argument.getClass().getCanonicalName()));
		}
	}

	@SuppressWarnings("JavaDoc")
	private void checkAuthorization(final AuthorizationComponent component, final Action action, final Long id) {
		if (!CommonAuthorization.isAuthorized(component, action, id)) {
			throw new NotAuthorizedException(String.format("'%s' access is not allowed...", action));
		}
	}

}
