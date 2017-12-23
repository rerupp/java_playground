package spring.rest.repository.repo2;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@SuppressWarnings("JavaDoc")
@Configuration
@Profile(Repo2Configuration.PROFILE)
public class Repo2Configuration {

	@SuppressWarnings("WeakerAccess")
	public static final String PROFILE = "repo2";

}
