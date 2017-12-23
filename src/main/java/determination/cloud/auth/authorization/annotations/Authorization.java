/*
 * Copyright (c) 2016 Thomson Reuters/ONESOURCE. All rights reserved.
 */

package determination.cloud.auth.authorization.annotations;

import determination.cloud.auth.authorization.Action;
import determination.cloud.auth.authorization.AuthorizationComponent;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Establishes the permissions required for an authorization cross cutting concern.
 *
 * @author Rick Rupp
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface Authorization {

	/**
	 * The application component associate with the required permission.
	 *
	 * @return the application component.
	 */
	AuthorizationComponent component();

	/**
	 * The application action that will be applied.
	 *
	 * @return the application action.
	 */
	Action action();

	/**
	 * The bean name of the application component handler that will be used to authorize access.
	 *
	 * @return the application component authorization handler.
	 */
	String handler() default "";

	/**
	 * An optional string that can associate a {@link AuthorizationComponent} to a handler.
	 * @return a string to associate a handler with a {@code AuthorizationComponent}.
	 */
	String tag() default "";

}
