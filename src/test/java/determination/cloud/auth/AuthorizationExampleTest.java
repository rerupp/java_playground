/*
 * Copyright (c) 2016 Thomson Reuters/ONESOURCE. All rights reserved.
 */

package determination.cloud.auth;

import determination.cloud.auth.authorization.Action;
import determination.cloud.auth.authorization.AuthorizationComponent;
import determination.cloud.auth.authorization.NotAuthorizedException;
import determination.cloud.auth.examples.common.authorization.CommonAuthorization;
import determination.cloud.auth.examples.common.dtos.CommonDTO;
import determination.cloud.auth.examples.common.managers.CommonManager;
import determination.cloud.auth.examples.custom.dtos.CustomDTO;
import determination.cloud.auth.examples.custom.managers.CustomManager;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import utils.PojoBuilder;

import java.util.EnumSet;
import java.util.function.BooleanSupplier;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * The example authorization test cases.
 *
 * @author Rick Rupp on 4/11/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AuthorizationExample.class})
@DirtiesContext
public class AuthorizationExampleTest {

	@Autowired
	private CommonManager commonManager;

	@Autowired
	private CustomManager customManager;

	@After
	public void after() {
		commonManager.reset();
		customManager.reset();
		CommonAuthorization.clear();
	}

	@Test
	public void testCommonService() {

		// verify a pristine common environment
		assumeNotAuthorizedException("expected CREATE to fail", () -> commonManager.add(new CommonDTO()));
		assumeNotAuthorizedException("expected UPDATE to fail for bogus DTO", () -> commonManager.update(new CommonDTO()));
		assumeNotAuthorizedException("expected READ to fail for bogus ID", () -> commonManager.find(0));
		assumeNotAuthorizedException("expected DELETE to fail for bogus ID", () -> commonManager.delete(0));
		assumeNotAuthorizedException("expected DELETE to fail for bogus DTO", () -> commonManager.delete(new CommonDTO()));

		// create a common DTO
		CommonAuthorization.set(AuthorizationComponent.COMMON, null, EnumSet.of(Action.CREATE));
		final CommonDTO commonDTO = commonManager.add(PojoBuilder.of(CommonDTO::new)
																 .with(CommonDTO::setDescription, "initial DTO version")
																 .build());

		// go read it
		CommonAuthorization.set(AuthorizationComponent.COMMON, commonDTO.getId(), EnumSet.of(Action.READ));
		assumeTrue("expected to find CommonDTO", () -> commonManager.find(commonDTO.getId()).equals(commonDTO));

		// update it
		CommonAuthorization.set(AuthorizationComponent.COMMON, commonDTO.getId(), EnumSet.of(Action.READ, Action.UPDATE));
		commonDTO.setDescription("an updated version of the DTO");
		assumeTrue("expected to update CommonDTO", () -> null != commonManager.update(commonDTO));
		assumeTrue("expected to find updated CommonDTO", () -> commonManager.find(commonDTO.getId()).equals(commonDTO));

		// delete it
		CommonAuthorization.set(AuthorizationComponent.COMMON, commonDTO.getId(), EnumSet.of(Action.READ, Action.DELETE));
		assumeTrue("expected to update CommonDTO", () -> commonManager.delete(commonDTO));
		assumeTrue("expected to find updated CommonDTO", () -> null == commonManager.find(commonDTO.getId()));
	}

	@Test
	public void testCustom() {

		// verify a pristine custom environment
		assumeNotAuthorizedException("expected custom add to fail", () -> customManager.add(new CustomDTO()));
		assumeNotAuthorizedException("expected custom find to fail for bogus ID", () -> customManager.find(0L));
		assumeNotAuthorizedException("expected custom delete to fail for bogus ID", () -> customManager.delete(0L));
		assumeNotAuthorizedException("expected set description to fail for bogus ID", () -> customManager.setDescription(null, 0L));
		assumeNotAuthorizedException("expected update association to fail for bogus ID", () -> customManager.updateAssociation(0L, 0L));

		// create a custom DTO
		CommonAuthorization.set(AuthorizationComponent.CUSTOM, null, EnumSet.of(Action.CREATE));
		final CustomDTO customDTO = customManager.add(PojoBuilder.of(CustomDTO::new)
																 .with(CustomDTO::setDescription, "the first DTO version")
																 .build());

		// try to read it
		CommonAuthorization.set(AuthorizationComponent.CUSTOM, customDTO.getId(), EnumSet.of(Action.READ));
		assumeTrue("expected to find initial CustomDTO", () -> customManager.find(customDTO.getId()).equals(customDTO));

		// update the description
		CommonAuthorization.set(AuthorizationComponent.CUSTOM, customDTO.getId(), EnumSet.of(Action.READ, Action.UPDATE));
		final String description = "the updated custom DTO description";
		customManager.setDescription(description, customDTO.getId());
		assumeTrue("expected custom DTO description to be updated", () -> customManager.find(customDTO.getId()).getDescription().equals(description));

		// update the association
		CommonAuthorization.set(AuthorizationComponent.COMMON, null, EnumSet.of(Action.CREATE));
		final CommonDTO association = commonManager.add(PojoBuilder.of(CommonDTO::new)
																   .with(CommonDTO::setDescription, "the CommonDTO description")
																   .build());
		CommonAuthorization.set(AuthorizationComponent.COMMON, association.getId(), EnumSet.of(Action.READ));
		assumeTrue("expected to update association for custom DTO", () -> null != customManager.updateAssociation(customDTO.getId(),
																												  association.getId()));
		assumeTrue("expected custom DTO to reflect association update",
				   () -> customManager.find(customDTO.getId()).equals(PojoBuilder.of(CustomDTO::new)
																				 .with(CustomDTO::setId, customDTO.getId())
																				 .with(CustomDTO::setDescription, customDTO.getDescription())
																				 .with(CustomDTO::setAssociation, association)
																				 .build()));

		// turn around and try to update the association again without READ access to the common DTO
		CommonAuthorization.set(AuthorizationComponent.COMMON, association.getId(), null);
		assumeNotAuthorizedException("expected error trying to update association", () -> customManager.updateAssociation(customDTO.getId(),
																														  association.getId()));

		// now verify it can be deleted
		CommonAuthorization.set(AuthorizationComponent.CUSTOM, customDTO.getId(), EnumSet.of(Action.DELETE));
		assumeTrue("expected the custom DTO to be deleted", () -> customManager.delete(customDTO.getId()));

	}

	@Test
	public void contrivedCustomScenarios() {

		// test the auto create of an association
		CommonAuthorization.set(AuthorizationComponent.CUSTOM, null, EnumSet.of(Action.CREATE));
		final CustomDTO firstContrivedExample = PojoBuilder.of(CustomDTO::new)
															.with(CustomDTO::setDescription, "first contrived example")
															.with(CustomDTO::setAssociation, PojoBuilder.of(CommonDTO::new)
																										.with(CommonDTO::setDescription, "contrived")
																										.build())
															.build();
		assumeNotAuthorizedException("expected first contrived example to fail", () -> customManager.add(firstContrivedExample));

		// add create authorization for a common DTO
		CommonAuthorization.set(AuthorizationComponent.COMMON, null, EnumSet.of(Action.CREATE));
		final CustomDTO firstContrivedExampleResult = customManager.add(firstContrivedExample);
		assertNotNull("Expected the first contrived example to be added...", firstContrivedExampleResult);
		final CommonDTO association = firstContrivedExample.getAssociation();
		assertNotNull("Expected the first contrived example association to have an ID...", association.getId());

		// work through the auto populate example
		final CustomDTO secondContrivedExample = PojoBuilder.of(CustomDTO::new)
														   .with(CustomDTO::setDescription, "second contrived example")
														   .with(CustomDTO::setAssociation, PojoBuilder.of(CommonDTO::new)
																									   .with(CommonDTO::setId, association.getId())
																									   .build())
														   .build();
		assumeNotAuthorizedException("expected first contrived example to fail", () -> customManager.add(firstContrivedExample));
		CommonAuthorization.set(AuthorizationComponent.COMMON, association.getId(), EnumSet.of(Action.READ));
		final CustomDTO secondContrivedExampleResult = customManager.add(secondContrivedExample);
		assertNotNull("Expected the second contrived example to be added...", secondContrivedExampleResult);
		assertTrue("Expected contrived example associations to match", association.equals(secondContrivedExampleResult.getAssociation()));
	}

	private void assumeTrue(final String message, final BooleanSupplier assumption) {

		final boolean assumptionResult;
		try {
			assumptionResult = assumption.getAsBoolean();
		} catch (final Exception e) {
			throw new AssertionError("Did not expect Exception running the assumption!", e);
		}

		if (! assumptionResult) {
			throw new AssertionError(message);
		}
	}


	private void assumeNotAuthorizedException(final String message, final Executable test) {
		try {
			test.execute();
			fail(message);
		} catch (final NotAuthorizedException ignore) {
			// don't do anything since you're expecting to be not authorized
		}
	}

	@FunctionalInterface
	public interface Executable {
		void execute();
	}
}