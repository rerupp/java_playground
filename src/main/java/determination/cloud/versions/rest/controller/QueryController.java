package determination.cloud.versions.rest.controller;

import determination.cloud.versions.rest.RestConfiguration;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * This is a sample REST controller to test limits of the URI using query prameters.
 *
 * @author Rick Rupp
 */
@SuppressWarnings("JavaDoc")
@RestController
@RequestMapping(value = QueryController.PATH)
public class QueryController {

	static final String PATH = "/";

	@RequestMapping(method = RequestMethod.GET)
	ResponseEntity findWithQuery(@RequestParam(value="queryParam", required = false) final String queryParam) throws Exception {
		final Map<String, Object> response = new HashMap<>();
		if (StringUtils.isEmpty(queryParam)) {
			response.put("hasQuery", false);
		} else {
			response.put("query length", queryParam.length());
			response.put("queryParam", queryParam);
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
