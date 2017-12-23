/*
 * Copyright (c) 2016 Thomson Reuters/ONESOURCE. All rights reserved.
 */

package determination.cloud.auth.authorization.handlers;

import determination.cloud.auth.authorization.AuthorizationConfig;
import determination.cloud.auth.authorization.AuthorizeHandler;
import determination.cloud.auth.authorization.AuthorizeHandlerException;
import determination.cloud.auth.authorization.annotations.Authorization;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.stereotype.Component;
import utils.MessageFormatter;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * The default implementation of the manager that routes authorization requests to the corresponding authorization handler.
 *
 * @author Rick Rupp
 */
@Component
class AuthorizeHandlerManagerImpl extends ApplicationObjectSupport implements AuthorizeHandlerManager, MessageFormatter {

	private final ConcurrentMap<String, AuthorizeHandler> authorizationHandlers = new ConcurrentHashMap<>();

	@Override
	public void authorize(final Authorization[] authorizations, final JoinPoint joinPoint) {

		for (final Authorization authorization : authorizations) {
			final String handler = StringUtils.defaultIfBlank(authorization.handler(), AuthorizationConfig.DEFAULT_AUTHORIZE_HANDLER_BEAN_NAME);
			final AuthorizeHandler authorizeHandler = authorizationHandlers.computeIfAbsent(handler, beanName -> {
				final Object beanInstance = getApplicationContext().getBean(beanName);
				if (! (beanInstance instanceof AuthorizeHandler)) {
					throw new AuthorizeHandlerException(format("Expected '{}' to be an AuthorizeHandler not a '{}'...",
															   beanName,
															   beanInstance.getClass().getCanonicalName()));
				}
				return (AuthorizeHandler) beanInstance;
			});

			authorizeHandler.authorize(authorization, joinPoint);
		}
	}

}
