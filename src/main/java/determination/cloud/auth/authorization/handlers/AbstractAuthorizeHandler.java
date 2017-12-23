/*
 * Copyright (c) 2016 Thomson Reuters/ONESOURCE. All rights reserved.
 */

package determination.cloud.auth.authorization.handlers;

import determination.cloud.auth.authorization.AuthorizeHandler;
import determination.cloud.auth.authorization.AuthorizeHandlerException;
import determination.cloud.auth.authorization.annotations.Authorization;
import determination.cloud.auth.authorization.annotations.AuthorizeParameter;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.MessageFormatter;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

/**
 * The bar package authorization handler.
 *
 * @author Rick Rupp
 */
public abstract class AbstractAuthorizeHandler implements AuthorizeHandler, MessageFormatter {

	private final Logger log;
	private final Map<String, Function<Object[], Object>> authorizeParameterGetters;

	/**
	 * Initialize the common authorize handler functionality.
	 */
	protected AbstractAuthorizeHandler() {
		log = LoggerFactory.getLogger(getClass());
		authorizeParameterGetters = new ConcurrentHashMap<>();
	}

	/**
	 * Get the Lambda function that will mine the argument used for authorization from the method being invoked.
	 *
	 * @param authorization the authorization associated with the join point.
	 * @param joinPoint     the join point of the method being invoked.
	 * @return a function that will mine the authorization argument. It will never be {@code null}.
	 * @see #createAuthorizeParameterFunction(String, Authorization, JoinPoint)
	 */
	protected Function<Object[], Object> getAuthorizeParameterFunction(final Authorization authorization, final JoinPoint joinPoint) {

		// the signature of the function is the target class name combined with the joint point signature
		final Method targetMethod = getTargetMethod(joinPoint);

		// get the function that will mine the authorization parameter
		return authorizeParameterGetters.computeIfAbsent(targetMethod.getDeclaringClass().getCanonicalName() + "=" + joinPoint.toLongString(),
														 signature -> createAuthorizeParameterFunction(signature, authorization, joinPoint));

	}

	/**
	 * Create the Lambda function that will mine the argument used for authorization from the method being invoked.
	 *
	 * @param signature     the signature associated with the function.
	 * @param authorization the authorization associated with the join point.
	 * @param joinPoint     the join point of the method being invoked.
	 * @return a function that will mine the authorization argument. It will never be {@code null}.
	 */
	@SuppressWarnings("WeakerAccess")
	protected Function<Object[], Object> createAuthorizeParameterFunction(final String signature,
																		  final Authorization authorization,
																		  final JoinPoint joinPoint) {
		// find the first authorization parameter on the target method
		final boolean isAuthorizationTagged = StringUtils.isNotBlank(authorization.tag());
		final AuthorizeParameter[] targetAuthorizeParameters = getMethodAuthorizeParameters(getTargetMethod(joinPoint));
		int i = 0;
		for (; i < targetAuthorizeParameters.length; i++) {
			if ((null != targetAuthorizeParameters[i]) &&
					(!isAuthorizationTagged || authorization.tag().equals(targetAuthorizeParameters[i].value()))) {
				break;
			}
		}
		// if the authorization has a tag and you didn't find a authorize parameter that matches it there's a problem
		if (isAuthorizationTagged && i >= targetAuthorizeParameters.length) {
			throw new AuthorizeHandlerException(format("Did not find authorization tag '{}' for {}", authorization.tag(), signature));
		}
		// return a Lambda that will mine the authorization parameter
		getLogger().info("Created argument miner for {}", signature);
		final int authorizeParameterIndex = (i < targetAuthorizeParameters.length) ? i : 0;
		return args -> args[authorizeParameterIndex];
	}

	protected Logger getLogger() {
		return log;
	}

}
