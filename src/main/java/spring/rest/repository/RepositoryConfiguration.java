package spring.rest.repository;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import spring.rest.repository.repo1.Repo1Configuration;
import spring.rest.repository.repo2.Repo2Configuration;

@SuppressWarnings("JavaDoc")
@Configuration
@Import({Repo1Configuration.class, Repo2Configuration.class})
public class RepositoryConfiguration {
}
