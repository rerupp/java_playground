package spring.rest.repository;

import java.util.Map;

@SuppressWarnings("JavaDoc")
public interface JsonStore {

	Map<String, Object> save(Map<String, Object> json);

}
