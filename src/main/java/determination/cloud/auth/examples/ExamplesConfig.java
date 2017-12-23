/*
 * Copyright (c) 2016 Thomson Reuters/ONESOURCE. All rights reserved.
 */

package determination.cloud.auth.examples;

import determination.cloud.auth.examples.common.CommonConfig;
import determination.cloud.auth.examples.custom.CustomConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * The Spring configuration for the examples.
 *
 * @author Rick Rupp
 */
@Configuration
@Import({CommonConfig.class, CustomConfig.class})
public class ExamplesConfig {
}
