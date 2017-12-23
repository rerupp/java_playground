package spring.rest.service;

import java.util.Map;

@SuppressWarnings("JavaDoc")
public interface JsonService {

	public Map<String, Object> jsonConsumer(final Map<String, Object> json);

}
