/*
 * Copyright (c) 2016 Thomson Reuters/ONESOURCE. All rights reserved.
 */

package determination.cloud.auth.authorization.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks a method parameter as something that will be used by authorization.
 *
 * @author Rick Rupp
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface AuthorizeParameter {

	/**
	 * An optional identifier of the method parameter. This is used in the case where there are
	 * two method parameters of the same type used by a authorization handler that need to be
	 * uniquely identified.
	 *
	 * @return the method parameter identifier.
	 */
	String value() default "";

}
