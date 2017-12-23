/*
 * Copyright (c) 2016 Thomson Reuters/ONESOURCE. All rights reserved.
 */

package determination.cloud.auth.examples.common.managers;

import determination.cloud.auth.authorization.Action;
import determination.cloud.auth.authorization.AuthorizationComponent;
import determination.cloud.auth.authorization.annotations.Authorization;
import determination.cloud.auth.authorization.annotations.Authorize;
import determination.cloud.auth.examples.common.dtos.CommonDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * The implementation of the common manager.
 *
 * @author Rick Rupp
 */
public class CommonManagerImpl implements CommonManager {

	private final Logger log = LoggerFactory.getLogger(CommonManagerImpl.class);
	private final Map<Long, CommonDTO> instances = new ConcurrentHashMap<>();
	private final AtomicLong idGenerator = new AtomicLong(0L);

	@Authorize(@Authorization(component = AuthorizationComponent.COMMON, action = Action.READ))
	@Override
	public CommonDTO find(final long id) {
		final CommonDTO dto = instances.get(id);
		if (null == dto) {
			log.info("({}) Did not find CommonDTO id={}.", Action.READ, id);
		} else {
			log.info("({}) CommonDTO: {}", Action.READ, dto);
		}
		return dto;
	}

	@Authorize(@Authorization(component = AuthorizationComponent.COMMON, action = Action.CREATE))
	@Override
	public CommonDTO add(final CommonDTO dto) {
		Objects.requireNonNull(dto, "The CommonDTO cannot be null for add...").setId(idGenerator.getAndIncrement());
		instances.put(dto.getId(), dto);
		log.info("({}) CommonDTO: {}", Action.CREATE, dto);
		return dto;
	}

	@Authorize(@Authorization(component = AuthorizationComponent.COMMON, action = Action.UPDATE))
	@Override
	public CommonDTO update(final CommonDTO dto) {
		Objects.requireNonNull(dto, "The CommonDTO cannot be null for update...");
		Objects.requireNonNull(dto.getId(), "The CommonDTO identifier cannot be null for update...");
		final CommonDTO previousVersion = instances.replace(dto.getId(), dto);
		if (null == previousVersion) {
			log.error("({}) A prior version of CommonDTO: {} was not found...", Action.UPDATE, dto);
		} else {
			log.info("({}) CommonDTO: {}", Action.UPDATE, dto);
		}
		return dto;
	}

	@Authorize(@Authorization(component = AuthorizationComponent.COMMON, action = Action.DELETE))
	@Override
	public boolean delete(final CommonDTO dto) {
		boolean deleted = false;
		if (null == dto) {
			log.warn("({}) Cannot delete CommonDTO when instance is null...", Action.DELETE);
		} else if (null == dto.getId()) {
			log.warn("({}) Cannot delete CommonDTO when id is null...", Action.DELETE);
		} else {
			deleted = delete(dto.getId());
		}
		return deleted;
	}


	@Authorize(@Authorization(component = AuthorizationComponent.COMMON, action = Action.DELETE))
	@Override
	public boolean delete(final long id) {
		final CommonDTO dto = instances.remove(id);
		if (null == dto) {
			log.warn("({}) Did not find CommonDTO id={} to delete...", Action.DELETE, id);
		} else {
			log.info("({}) Deleted CommonDTO: {}", Action.DELETE, dto);
		}
		return null != dto;
	}

	@Override
	public void reset() {
		instances.clear();
		idGenerator.set(0L);
	}

}
