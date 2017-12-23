package spring.rest.repository.repo1;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@SuppressWarnings("JavaDoc")
@Configuration
@Profile(Repo1Configuration.PROFILE)
@ComponentScan(includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, value= Repository.class))
public class Repo1Configuration {

	public static final String PROFILE = "repo1";
}
