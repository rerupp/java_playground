/*
 * Copyright (c) 2016 Thomson Reuters/ONESOURCE. All rights reserved.
 */

package determination.cloud.auth.examples.custom;

import determination.cloud.auth.authorization.AuthorizeHandler;
import determination.cloud.auth.examples.common.managers.CommonManager;
import determination.cloud.auth.examples.custom.authorization.CustomAuthorizeHandler;
import determination.cloud.auth.examples.custom.managers.CustomManager;
import determination.cloud.auth.examples.custom.managers.CustomManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * The Spring configuration for the Custom authorization example.
 *
 * @author Rick Rupp
 */
@Configuration
public class CustomConfig {

	/**
	 * The default {@code CustomManager} bean.
	 *
	 * @param commonManager the common manager the {@code CustomManager} uses for the association.
	 * @return the default implementation of {@code CustomManager}.
	 */
	@Autowired
	@Bean
	public CustomManager customManager(final CommonManager commonManager) {
		return new CustomManagerImpl(commonManager);
	}

	/**
	 * The specialized {@code BarManager} authorization handler.
	 *
	 * @return the default implementation of {@code AuthorizeHandler} for the bar manager.
	 */
	@Bean
	public AuthorizeHandler customAuthorizationHandler() {
		return new CustomAuthorizeHandler();
	}

}
