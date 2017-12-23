/*
 * Copyright (c) 2016 Thomson Reuters/ONESOURCE. All rights reserved.
 */

package determination.cloud.auth.authorization;

import determination.cloud.auth.authorization.annotations.Authorization;
import determination.cloud.auth.authorization.annotations.AuthorizeParameter;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.util.ReflectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * The authorization handler API.
 *
 * @author Rick Rupp
 */
@FunctionalInterface
public interface AuthorizeHandler {

	/**
	 * Mines the class method associated with a join point.
	 *
	 * @param joinPoint the {@code JoinPoint} that will be used to mine the associated method. It must not be {@code null}.
	 * @return the associated {@code Method}. It will never be {@code null}.
	 * @throws AuthorizeHandlerException if the join point is null or is not associated with a class method.
	 * @see #requireNotNull(Object, String, Object...)
	 */
	default Method getSignatureMethod(final JoinPoint joinPoint) {
		final Signature signature = requireNotNull(joinPoint, "The JoinPoint cannot be null...").getSignature();
		if (!(signature instanceof MethodSignature)) {
			throw new AuthorizeHandlerException(joinPoint.toLongString() + " is not associated with a class method...");
		}
		return ((MethodSignature) signature).getMethod();
	}

	/**
	 * Mines the target method associated with a join point.
	 *
	 * @param joinPoint the {@code JoinPoint} that will be used to mine the associated target method. It must not be {@code null}.
	 * @return the associated target {@code Method}. It will never be {@code null}.
	 * @throws AuthorizeHandlerException if the join point is not associated with a class method.
	 * @see #getSignatureMethod(JoinPoint)
	 * @see #requireNotNull(Object, String, Object...)
	 */
	default Method getTargetMethod(final JoinPoint joinPoint) {
		final Method signatureMethod = getSignatureMethod(joinPoint);
		final Class target = joinPoint.getSourceLocation().getWithinType();
		final Method targetMethod = ReflectionUtils.findMethod(target, signatureMethod.getName(), signatureMethod.getParameterTypes());
		return requireNotNull(targetMethod, "Did not find a target method for %s...", joinPoint.toLongString());
	}

	/**
	 * Mines {@code @AuthorizeParameter} annotations that may be associated with a methods parameters. If the method has no
	 * parameters a zero-length array will be returned. The order of the array will match the order or parameters on the method.
	 * The returned array can be sparse in the case where a parameter does not have a {@code @AuthorizeParameter}
	 * associate with it.
	 *
	 * @param method    the method whose parameters will be examined.
	 * @return the array of {@code @AuthorizeParameter} associated with the method parameters.
	 * @throws AuthorizeHandlerException if the method is {@code null}.
	 * @see #requireNotNull(Object, String, Object...)
	 */
	default AuthorizeParameter[] getMethodAuthorizeParameters(final Method method) {
		final Annotation[][] annotations = requireNotNull(method, "The Method cannot be null...").getParameterAnnotations();
		final AuthorizeParameter[] authorizeParameters = new AuthorizeParameter[annotations.length];
		for (int parameter = 0; parameter < annotations.length; parameter++) {
			authorizeParameters[parameter] = null;
			for (final Annotation annotation : annotations[parameter]) {
				if (AuthorizeParameter.class.isAssignableFrom(annotation.annotationType())) {
					authorizeParameters[parameter] = (AuthorizeParameter) annotation;
				}
			}
		}
		return authorizeParameters;
	}

	/**
	 * If the object is null throws a {@code AuthorizeHandlerException} with the specified message.
	 *
	 * @param object  the object to check.
	 * @param message the {@code String.format(String, Object...)} exception message if the object is null.
	 * @param values  the optional values for the formatted exception message.
	 * @param <T>     the object type.
	 * @return the object allowing method chaining. It will never be {@code null}.
	 * @throws AuthorizeHandlerException if the object is {@code null}.
	 */
	default <T> T requireNotNull(final T object, final String message, final Object... values) {
		if (null == object) {
			throw new AuthorizeHandlerException(String.format(message, values));
		}
		return object;
	}

	/**
	 * Used to authorize access to functionality. If authorization is not granted an exception
	 * will be thrown.
	 *
	 * @param authorization the permission required to access the functionality.
	 * @param joinPoint     the join point associated with the functionality.
	 */
	void authorize(Authorization authorization, JoinPoint joinPoint);

}
