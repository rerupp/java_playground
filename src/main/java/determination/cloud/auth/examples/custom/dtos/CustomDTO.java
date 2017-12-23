/*
 * Copyright (c) 2016 Thomson Reuters/ONESOURCE. All rights reserved.
 */

package determination.cloud.auth.examples.custom.dtos;

import determination.cloud.auth.examples.common.dtos.CommonDTO;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.text.StrBuilder;

/**
 * The Custom DTO example. The scenario is somewhat contrived however the DTO has an
 * association to the {@link CommonDTO} to demonstrate how authorizations could be
 * interrelated.
 *
 * @author Rick Rupp
 */
@SuppressWarnings("unused")
public class CustomDTO {

	private Long id;
	private String description;
	private CommonDTO association;

	/**
	 * Construct a custom DTO instance that is not initialized.
	 */
	public CustomDTO() {
		id = null;
		description = null;
		association = null;
	}

	/**
	 * Construct a copy of the custom DTO.
	 *
	 * @param other the DTO that will be copied.
	 */
	public CustomDTO(final CustomDTO other) {
		id = other.id;
		description = other.description;
		association = (null == other.association) ? null : new CommonDTO(other.association);
	}

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public CommonDTO getAssociation() {
		return association;
	}

	public void setAssociation(final CommonDTO association) {
		this.association = association;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(7, 13).append(id).append(description).append(association).toHashCode();
	}

	@Override
	public boolean equals(final Object other) {
		if (other == null) {
			return false;
		}
		if (other == this) {
			return true;
		}
		if (other.getClass() != getClass()) {
			return false;
		}
		final CustomDTO rhs = (CustomDTO) other;
		// do not use appendSuper here because the super class is Object and equals will return false unless it is the same object...
		return new EqualsBuilder().append(id, rhs.id)
								  .append(description, rhs.description)
								  .append(association, rhs.association)
								  .isEquals();
	}

	@Override
	public String toString() {
		return new StrBuilder().append('{')
							   .append("id:").append(String.valueOf(id))
							   .append(",description:").append((null == description) ? "null" : ("\"" + description + "\""))
							   .append(",association:").append((null == association) ? "null" : association.toString())
							   .append('}')
							   .toString();
	}

}
