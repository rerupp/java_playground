/*
 * Copyright (c) 2016 Thomson Reuters/ONESOURCE. All rights reserved.
 */

package determination.cloud.auth.authorization.aspects;

import determination.cloud.auth.authorization.annotations.Authorize;
import determination.cloud.auth.authorization.NotAuthorizedException;
import determination.cloud.auth.authorization.handlers.AuthorizeHandlerManager;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * The authorization cross cutting concern.
 *
 * @author Rick Rupp
 */
@Component
@Aspect
public class AuthorizeAspect {

	private final AuthorizeHandlerManager authorizeHandlerManager;

	/**
	 * The autowired creator of the authorization aspect
	 * @param authorizeHandlerManager the authorization handler manager that will be used by the aspect.
	 */
	@Autowired
	AuthorizeAspect(final AuthorizeHandlerManager authorizeHandlerManager) {
		this.authorizeHandlerManager = Objects.requireNonNull(authorizeHandlerManager, "A AuthorizeHandlerManager is required...");
	}

	/**
	 * The point cut that selects methods that are annotated with {@code Authorization} within the
	 * {@code determination.cloud.auth} package.
	 */
	@Pointcut("execution(@determination.cloud.auth.authorization.annotations.Authorize * determination.cloud.auth..*.*(..))")
	private void managerAuthorizationRequired() {
		// the point-cut doesn't require an implementation
	}

	/**
	 * Advice that runs before methods annotated with {@code Authorization}. If the authorization is
	 * not granted an exception will be thrown.
	 *
	 * @param joinPoint the current join point of the advice.
	 * @param authorization the annotation on the method that identifies the required authorization.
	 * @throws NotAuthorizedException if authorization is not granted.
	 */
	@Before("managerAuthorizationRequired() && @annotation(authorization)")
	public void validateAuthorization(final JoinPoint joinPoint, final Authorize authorization) {
		authorizeHandlerManager.authorize(authorization.value(), joinPoint);
	}

}
