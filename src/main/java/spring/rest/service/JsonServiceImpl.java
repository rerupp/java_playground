package spring.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.rest.repository.JsonStore;

import java.util.Map;
import java.util.Objects;

@SuppressWarnings("JavaDoc")
@Service
class JsonServiceImpl implements JsonService {

	private final JsonStore jsonStore;

	@Autowired
	JsonServiceImpl(final JsonStore jsonStore) {
		this.jsonStore = Objects.requireNonNull(jsonStore, "The JsonStore instance is required...");
	}

	@Override
	public Map<String, Object> jsonConsumer(final Map<String, Object> json) {
		return jsonStore.save(json);
	}
}
