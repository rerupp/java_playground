/*
 * Copyright (c) 2016 Thomson Reuters/ONESOURCE. All rights reserved.
 */
package determination.cloud.auth;

import determination.cloud.auth.authorization.AuthorizationConfig;
import determination.cloud.auth.examples.ExamplesConfig;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/**
 * The example application configuration.
 *
 * @author Rick Rupp
 */
@SpringBootApplication
@EnableAutoConfiguration
@Import({ExamplesConfig.class, AuthorizationConfig.class})
public class AuthorizationExample implements CommandLineRunner {

	/**
	 * Starts the REST version example.
	 *
	 * @param args the command line arguments.
	 */
	public static void main(final String... args) {
		SpringApplication.run(AuthorizationExample.class, args).close();
	}

	@Override
	public void run(final String... strings) throws Exception {
		// make SonarQube think it does not require a private constructor on this class
	}

}
