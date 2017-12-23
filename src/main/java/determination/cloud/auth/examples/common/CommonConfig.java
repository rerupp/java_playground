/*
 * Copyright (c) 2016 Thomson Reuters/ONESOURCE. All rights reserved.
 */

package determination.cloud.auth.examples.common;

import determination.cloud.auth.authorization.AuthorizationConfig;
import determination.cloud.auth.authorization.AuthorizeHandler;
import determination.cloud.auth.examples.common.authorization.CommonAuthorizeHandler;
import determination.cloud.auth.examples.common.managers.CommonManager;
import determination.cloud.auth.examples.common.managers.CommonManagerImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * The Spring configuration for the common examples.
 *
 * @author Rick Rupp
 */
@SuppressWarnings("JavaDoc")
@Configuration
public class CommonConfig {

	/**
	 * Create an instance of the default authorize handler.
	 *
	 * @return the default authorize handler.
	 */
	@Bean(name = AuthorizationConfig.DEFAULT_AUTHORIZE_HANDLER_BEAN_NAME)
	public AuthorizeHandler defaultAuthorizeHandler() {
		return new CommonAuthorizeHandler();
	}

	/**
	 * Creates the common manager example bean.
	 *
	 * @return the common manager example bean.
	 */
	@Bean
	public CommonManager commonManager() {
		return new CommonManagerImpl();
	}
}
