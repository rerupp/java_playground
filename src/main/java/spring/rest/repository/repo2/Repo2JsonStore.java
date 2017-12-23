package spring.rest.repository.repo2;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import spring.rest.repository.JsonStore;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("JavaDoc")
@Repository
@Profile(Repo2Configuration.PROFILE)
public class Repo2JsonStore implements JsonStore {

	@Override
	public Map<String, Object> save(final Map<String, Object> json) {
		final Map<String, Object> updatedJson = new HashMap<>(json);
		updatedJson.put("id", Repo2Configuration.PROFILE);
		return updatedJson;
	}

}
