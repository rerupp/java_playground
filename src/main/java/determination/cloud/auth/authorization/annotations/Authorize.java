/*
 * Copyright (c) 2016 Thomson Reuters/ONESOURCE. All rights reserved.
 */

package determination.cloud.auth.authorization.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Used to manage the authorization required to access a manager method.
 *
 * @author Rick Rupp
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Authorize {

	/**
	 * The permission required to access a manager method.
	 *
	 * @return the required permission.
	 */
	Authorization[] value();
}
