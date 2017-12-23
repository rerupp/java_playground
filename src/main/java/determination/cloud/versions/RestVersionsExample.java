/*
 * Copyright (c) 2016 Thomson Reuters/ONESOURCE. All rights reserved.
 */

package determination.cloud.versions;

import determination.cloud.versions.core.CoreConfiguration;
import determination.cloud.versions.rest.RestConfiguration;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/**
 * A simple example of REST versioning through content negotiations. The pom includes the spring boot plugin so it will create
 * an executable jar. The pom configures the start-class property to {@code RestVersionsExample}. The example can also be run
 * via Maven using {@code mvn spring-boot:run}. In order to run from Intellij create a Spring Boot run configuration.
 *
 * @author Rick Rupp
 */
@SpringBootApplication
@EnableAutoConfiguration
@Import({RestConfiguration.class, CoreConfiguration.class})
public class RestVersionsExample implements CommandLineRunner {

	/**
	 * Starts the REST version example.
	 *
	 * @param args the command line arguments.
	 */
	public static void main(final String... args) {
		SpringApplication.run(RestVersionsExample.class, args).close();
	}

	@Override
	public void run(final String... strings) throws Exception {
		// don't join unless the commandline parameter is wait...
		if (strings.length > 0 && "wait".equalsIgnoreCase(strings[0])) {
			LoggerFactory.getLogger(RestVersionsExample.class).info("Press CTRL-C to exist the REST example.");
			Thread.currentThread().join();
		}
	}

}
