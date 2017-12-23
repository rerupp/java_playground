/*
 * Copyright (c) 2016 Thomson Reuters/ONESOURCE. All rights reserved.
 */

package determination.cloud.versions;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Simple tests for the REST version examples.
 *
 * @author Rick Rupp
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(RestVersionsExample.class)
@WebIntegrationTest
@DirtiesContext
public class RestVersionsExampleTest {

	@Test
	@Ignore
	public void run() throws Exception {
	}
}