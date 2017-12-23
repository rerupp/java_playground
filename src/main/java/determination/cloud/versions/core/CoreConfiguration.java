/*
 * Copyright (c) 2016 Thomson Reuters/ONESOURCE. All rights reserved.
 */

package determination.cloud.versions.core;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * The configuration for core beans.
 *
 * @author Rick Rupp
 */
@Configuration
@ComponentScan(CoreConfiguration.SERVICES_PACKAGE)
public class CoreConfiguration {

	static final String SERVICES_PACKAGE = "determination.cloud.versions.core.services";

}
