/*
 * Copyright (c) 2016 Thomson Reuters/ONESOURCE. All rights reserved.
 */

package determination.cloud.versions.rest.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * A simple DTO to demonstrate REST versioning.
 *
 * @author Rick Rupp
 */
@SuppressWarnings("unused")
public class ContactDTO {

	private long id;
	private String firstName;
	private String lastName;

	// the phone attribute was added in version 2
	@JsonInclude(JsonInclude.Include.NON_EMPTY) private String phone;

	public long getId() {
		return id;
	}

	public void setId(final long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(final String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(final String lastName) {
		this.lastName = lastName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(final String phone) {
		this.phone = phone;
	}

}
