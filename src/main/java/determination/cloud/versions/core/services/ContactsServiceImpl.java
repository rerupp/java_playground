/*
 * Copyright (c) 2016 Thomson Reuters/ONESOURCE. All rights reserved.
 */

package determination.cloud.versions.core.services;

import determination.cloud.versions.core.model.Contact;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The implementation of the REST service.
 *
 * @author Rick Rupp
 */
@Service
public class ContactsServiceImpl implements ContactsService {

	private long idGenerator = 1L;
	private final Map<Long, Contact> contacts = new HashMap<>(100);

	@Override
	public synchronized Contact add(final Contact model) {
		model.setId(idGenerator++);
		contacts.put(model.getId(), model);
		return model;
	}

	@Override
	public synchronized Contact get(final long id) {
		return contacts.get(id);
	}

	@Override
	public synchronized Collection<Contact> find(final String firstName, final String lastName) {
		return contacts.values()
					   .stream()
					   .filter(instance -> (firstName == null) || firstName.equalsIgnoreCase(instance.getFirstName()))
					   .filter(instance -> (lastName == null) || lastName.equalsIgnoreCase(instance.getLastName()))
					   .collect(Collectors.toList());
	}

	@Override
	public synchronized boolean delete(final long id) {
		return null != contacts.remove(id);
	}

	@Override
	public synchronized Contact update(final Contact contact) {
		return (null == contacts.replace(contact.getId(), contact)) ? null : contacts.get(contact.getId());
	}

}
