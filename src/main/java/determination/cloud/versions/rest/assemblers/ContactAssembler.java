/*
 * Copyright (c) 2016 Thomson Reuters/ONESOURCE. All rights reserved.
 */

package determination.cloud.versions.rest.assemblers;

import determination.cloud.versions.core.model.Contact;
import determination.cloud.versions.rest.dto.ContactDTO;
import determination.cloud.versions.rest.dto.ModifyContactDTO;
import org.apache.commons.lang3.StringUtils;
import utils.PojoBuilder;

/**
 * The assembler that converts between ContactDTO and Contact objects.
 *
 * @author Rick Rupp
 */
@SuppressWarnings("JavaDoc")
public final class ContactAssembler {

	public static Contact toModel(final ModifyContactDTO dto) {
		final Contact contact = previousToModel(dto);
		if (null != contact) {
			contact.setPhone(dto.getPhone());
		}
		return contact;
	}

	public static Contact previousToModel(final ModifyContactDTO dto) {
		return (null == dto) ? null : PojoBuilder.of(Contact::new)
												 .with(Contact::setFirstName, dto.getFirstName())
												 .with(Contact::setLastName, dto.getLastName())
												 .build();
	}

	public static ContactDTO toDTO(final Contact contact) {
		final ContactDTO dto = previousToDTO(contact);
		if (null != dto) {
			dto.setPhone(StringUtils.defaultString(contact.getPhone(), "NA"));
		}
		return dto;
	}

	public static ContactDTO previousToDTO(final Contact contact) {
		return (null == contact) ? null : PojoBuilder.of(ContactDTO::new)
													 .with(ContactDTO::setId, contact.getId())
													 .with(ContactDTO::setFirstName, contact.getFirstName())
													 .with(ContactDTO::setLastName, contact.getLastName())
													 .build();
	}

	private ContactAssembler() {
	}

}
