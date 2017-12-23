/*
 * Copyright (c) 2016 Thomson Reuters/ONESOURCE. All rights reserved.
 */

package determination.cloud.auth.authorization;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * The authorization configuration bean.
 *
 * @author Rick Rupp
 */
@Configuration
@EnableAspectJAutoProxy
@ComponentScan("determination.cloud.auth.authorization.aspects")
public class AuthorizationConfig {

	/**
	 * The bean name of the default authorize handler.
	 */
	public static final String DEFAULT_AUTHORIZE_HANDLER_BEAN_NAME = "defaultAuthorizeHandler";

}
