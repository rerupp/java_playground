/*
 * Copyright (c) 2016 Thomson Reuters/ONESOURCE. All rights reserved.
 */

package determination.cloud.auth.examples.custom.managers;

import determination.cloud.auth.examples.custom.dtos.CustomDTO;

/**
 * The Custom service API.
 *
 * @author Rick Rupp
 */
public interface CustomManager {

	/**
	 * Find a Custom DTO.
	 *
	 * @param id the Custom DTO that should be found.
	 * @return the Custom DTO or {@code null} if not found.
	 */
	CustomDTO find(long id);

	/**
	 * Add a Custom DTO. This API exists to demonstrate that the authorization aspect can be
	 * used on methods that have more that 1 argument.
	 *
	 * @param dto the DTO that will be added.
	 * @return the updated DTO instance.
	 * @throws NullPointerException if the DTO is {@code null}.
	 */
	CustomDTO add(CustomDTO dto);

	/**
	 * Delete a Custom DTO.
	 *
	 * @param id the Custom DTO that should be deleted.
	 * @return {@code true} if the DTO was deleted, {@code false} otherwise.
	 */
	boolean delete(long id);

	/**
	 * Changes the description of the custom DTO.
	 *
	 * @param description the new description.
	 * @param id          the identifier of the DTO whose description will be changed.
	 */
	void setDescription(String description, long id);

	/**
	 * Updates the custom DTO to reflect the association to the {@code CommonDTO}.
	 *
	 * @param id           the identifier of the DTO whose association will be changed.
	 * @param associatedId the identifier of the {@code CommonDTO} that will be associated with the custom DTO.
	 * @return the updated custom DTO.
	 */
	CustomDTO updateAssociation(long id, long associatedId);

	/**
	 * Restores the manager to a pristine state.
	 */
	void reset();

}
