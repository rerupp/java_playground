/*
 * Copyright (c) 2016 Thomson Reuters/ONESOURCE. All rights reserved.
 */

package spring.rest;

import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.ConfigurableEnvironment;
import spring.rest.controller.RestConfiguration;
import spring.rest.repository.RepositoryConfiguration;
import spring.rest.repository.repo1.Repo1Configuration;
import spring.rest.service.ServicesConfiguration;

@SuppressWarnings("JavaDoc")
@SpringBootApplication
@EnableAutoConfiguration
@Import({RestConfiguration.class, ServicesConfiguration.class, RepositoryConfiguration.class, RepositoryConfiguration.class})
public class RestMapExample implements CommandLineRunner {

	/**
	 * Starts the REST version example.
	 *
	 * @param args the command line arguments.
	 */
	public static void main(final String... args) {
		new RestMapExampleApplication(RestMapExample.class).run();
	}

	@Override
	public void run(final String... strings) throws Exception {
		// don't join unless the commandline parameter is wait...
		if (strings.length > 0 && "wait".equalsIgnoreCase(strings[0])) {
			LoggerFactory.getLogger(RestMapExample.class).info("Press CTRL-C to exist the REST example.");
			Thread.currentThread().join();
		}
	}

	private static class RestMapExampleApplication extends SpringApplication {

		RestMapExampleApplication(final Object... sources) {
			super(sources);
		}

		@Override
		protected void configureProfiles(final ConfigurableEnvironment env, final String[] args) {
			env.setActiveProfiles(Repo1Configuration.PROFILE);
			super.configureProfiles(env, args);
		}

	}
}
