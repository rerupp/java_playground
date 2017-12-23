package spring.rest.controller.endpoints;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import spring.rest.service.JsonService;

import java.util.Map;
import java.util.Objects;

@SuppressWarnings("JavaDoc")
@RestController
@RequestMapping(value = JsonController.PATH)
public class JsonController {

	static final String PATH = "/";

	private final JsonService jsonService;

	@Autowired
	JsonController(final JsonService jsonExampleService) {
		this.jsonService = Objects.requireNonNull(jsonExampleService, "The JSON service cannot be null...");
	}

	@RequestMapping(method = RequestMethod.POST)
	ResponseEntity getArbitraryJson(@RequestBody final Map<String, Object> json) throws Exception {
		return new ResponseEntity<>(jsonService.jsonConsumer(json), HttpStatus.OK);
	}

}
