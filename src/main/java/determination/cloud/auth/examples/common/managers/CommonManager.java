/*
 * Copyright (c) 2016 Thomson Reuters/ONESOURCE. All rights reserved.
 */

package determination.cloud.auth.examples.common.managers;

import determination.cloud.auth.examples.common.dtos.CommonDTO;

/**
 * The Common manager API.
 *
 * @author Rick Rupp.
 */
public interface CommonManager {

	/**
	 * Find a DTO.
	 *
	 * @param id the identifier of the DTO that should be found.
	 * @return the DTO or {@code null} if not found.
	 */
	CommonDTO find(long id);

	/**
	 * Add a DTO.
	 *
	 * @param dto the DTO that will be added.
	 * @return the updated DTO instance.
	 * @throws NullPointerException if the DTO is {@code null}.
	 */
	CommonDTO add(CommonDTO dto);

	/**
	 * Update a DTO.
	 *
	 * @param dto the DTO that will be updated.
	 * @return the DTO that will be updated.
	 * @throws NullPointerException if the DTO is {@code null}.
	 */
	CommonDTO update(CommonDTO dto);

	/**
	 * Delete a DTO.
	 *
	 * @param dto the DTO that should be deleted.
	 * @return {@code true} if the DTO was deleted, {@code false} otherwise.
	 */
	boolean delete(CommonDTO dto);

	/**
	 * Delete a DTO.
	 *
	 * @param id the identifier of the DTO that should be deleted.
	 * @return {@code true} if the DTO was deleted, {@code false} otherwise.
	 */
	boolean delete(long id);

	/**
	 * Restores the manager to a pristine state.
	 */
	void reset();

}
