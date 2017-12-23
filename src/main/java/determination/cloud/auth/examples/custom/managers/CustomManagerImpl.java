/*
 * Copyright (c) 2016 Thomson Reuters/ONESOURCE. All rights reserved.
 */

package determination.cloud.auth.examples.custom.managers;

import determination.cloud.auth.authorization.Action;
import determination.cloud.auth.authorization.AuthorizationComponent;
import determination.cloud.auth.authorization.annotations.Authorization;
import determination.cloud.auth.authorization.annotations.Authorize;
import determination.cloud.auth.authorization.annotations.AuthorizeParameter;
import determination.cloud.auth.examples.common.dtos.CommonDTO;
import determination.cloud.auth.examples.common.managers.CommonManager;
import determination.cloud.auth.examples.custom.dtos.CustomDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.MessageFormatter;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

/**
 * The default {@code CustomManager} implementation.
 *
 * @author Rick Rupp
 */
public class CustomManagerImpl implements CustomManager, MessageFormatter {

	private static final String HANDLER = "customAuthorizationHandler";

	private final Map<Long, CustomDTO> dtos = new HashMap<>();
	private final AtomicLong idGenerator = new AtomicLong(1L);
	private final Logger log = LoggerFactory.getLogger(CustomManagerImpl.class);
	private final CommonManager commonManager;

	/**
	 * Creates the {@code CustomManager} API implementation.
	 * @param commonManager the common manager used by the implementation.
	 */
	public CustomManagerImpl(final CommonManager commonManager) {
		this.commonManager = Objects.requireNonNull(commonManager, "The CommonManager cannot be null...");
	}

	@Authorize(@Authorization(component = AuthorizationComponent.CUSTOM, action = Action.CREATE, handler = HANDLER))
	@Override
	public CustomDTO add(final CustomDTO dto) {

		// if the dto includes an association verify it's state
		final CommonDTO association = Objects.requireNonNull(dto, "The CustomDTO cannot be null...").getAssociation();
		if (null != association) {

			// for this silly scenario create the association if it doesn't exist
			if (null == association.getId()) {
				dto.setAssociation(commonManager.add(association));
			} else {

				// otherwise look it up to fill out the association properly
				final CommonDTO commonDTO = commonManager.find(association.getId());
				if (null == commonDTO) {
					throw new IllegalArgumentException(format("Did not find association CommonDTO.id={}", association.getId()));
				}
				dto.setAssociation(commonDTO);
			}
		}

		dto.setId(idGenerator.getAndIncrement());
		dtos.put(dto.getId(), dto);
		log.info("({}) CustomDTO: {}", Action.CREATE, dto);
		return dto;
	}

	@Authorize(@Authorization(component = AuthorizationComponent.CUSTOM, action = Action.READ, handler = HANDLER))
	@Override
	public CustomDTO find(final long id) {
		final CustomDTO dto = dtos.get(id);
		if (null == dto) {
			log.info("({}) Did not find CustomDTO={}", Action.READ, id);
		} else {
			log.info("({}) Found CustomDTO: {}", Action.READ, dto);
		}
		return dto;
	}

	@Authorize(@Authorization(component = AuthorizationComponent.CUSTOM, action = Action.DELETE, handler = HANDLER))
	@Override
	public boolean delete(final long id) {
		final CustomDTO example = dtos.remove(id);
		if (null != example) {
			log.info("({}) deleted CustomDTO={}", Action.DELETE, example);
		} else {
			log.info("({}) did not find CustomDTO.id={}", Action.DELETE, id);
		}
		return null != example;
	}

	@Authorize(@Authorization(component = AuthorizationComponent.CUSTOM, action = Action.UPDATE, handler = HANDLER))
	@Override
	public void setDescription(final String description, @AuthorizeParameter final long id) {
		final CustomDTO dto = dtos.get(id);
		if (null != dto) {
			dto.setDescription(description);
			log.info("({}) set description for CustomDTO.id={}", Action.UPDATE, id);
		}
	}

	@Authorize({@Authorization(component = AuthorizationComponent.COMMON, action = Action.READ, tag = "association"),
				@Authorization(component = AuthorizationComponent.CUSTOM, action = Action.UPDATE, handler = HANDLER, tag = "this")})
	@Override
	public CustomDTO updateAssociation(@AuthorizeParameter("this") final long id, @AuthorizeParameter("association") final long associationId) {
		final CommonDTO association = commonManager.find(associationId);
		if (null == association) {
			throw new IllegalArgumentException(format("Did not find CommonDTO.id={}...", associationId));
		}
		final CustomDTO dto = dtos.get(id);
		if (null != dto) {
			dto.setAssociation(association);
			log.info("({}) updated association for CustomDTO: {}", Action.UPDATE, dto);
		}
		return dto;
	}

	@Override
	public void reset() {
		dtos.clear();
	}
}
