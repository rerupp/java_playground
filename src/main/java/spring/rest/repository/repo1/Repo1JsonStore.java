package spring.rest.repository.repo1;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import spring.rest.repository.JsonStore;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("JavaDoc")
@Profile(Repo1Configuration.PROFILE)
@Repository
public class Repo1JsonStore implements JsonStore {

	@Override
	public Map<String, Object> save(final Map<String, Object> json) {
		final Map<String, Object> updatedJson = new HashMap<>(json);
		updatedJson.put("id", Repo1Configuration.PROFILE);
		return updatedJson;
	}

}
