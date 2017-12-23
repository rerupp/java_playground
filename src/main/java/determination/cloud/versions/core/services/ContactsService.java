/*
 * Copyright (c) 2016 Thomson Reuters/ONESOURCE. All rights reserved.
 */

package determination.cloud.versions.core.services;

import determination.cloud.versions.core.model.Contact;

import java.util.Collection;

/**
 * The example REST service.
 *
 * @author Rick Rupp
 */
public interface ContactsService {

	/**
	 * The service that adds a model instance.
	 *
	 * @param model the instance that will be added.
	 * @return the contact that was added.
	 */
	Contact add(Contact model);

	/**
	 * The service that will update a model instance.
	 *
	 * @param contact the model updates.
	 * @return the updated contact or {@code null} if the contact does not exist.
	 */
	Contact update(Contact contact);

	/**
	 * The service that gets a model instance.
	 *
	 * @param id the id of the model instance that will be returned.
	 * @return the instance or {@code null} if not found.
	 */
	Contact get(long id);

	/**
	 * The service that gets all model instances.
	 *
	 * @param firstName filters the results using the contacts first name (can be {@code null}).
	 * @param lastName  filters the results using the contacts last name (can be {@code null}).
	 * @return the instance or {@code null} if not found.
	 */
	Collection<Contact> find(String firstName, String lastName);

	/**
	 * The service that deletes a model instance.
	 *
	 * @param id the ide of the model instance that will be deleted.
	 * @return the instance that was deleted of {@code null} if not found.
	 */
	boolean delete(long id);
}
