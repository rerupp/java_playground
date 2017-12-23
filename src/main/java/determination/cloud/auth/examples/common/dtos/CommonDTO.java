/*
 * Copyright (c) 2016 Thomson Reuters/ONESOURCE. All rights reserved.
 */

package determination.cloud.auth.examples.common.dtos;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.text.StrBuilder;
import utils.MessageFormatter;

/**
 * The common DTO.
 *
 * @author Rick Rupp
 */
@SuppressWarnings("unused")
public class CommonDTO implements MessageFormatter {

	private Long id;
	private String description;

	/**
	 * Construct a common DTO instance that is not initialized.
	 */
	public CommonDTO() {
		id = null;
		description = null;
	}

	/**
	 * Construct a copy of the common DTO.
	 *
	 * @param other the DTO that will be copied.
	 */
	public CommonDTO(final CommonDTO other) {
		id = other.id;
		description = other.description;
	}

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(7, 13).append(id).append(description).toHashCode();
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
		final CommonDTO rhs = (CommonDTO) other;
		return new EqualsBuilder().appendSuper(super.equals(other))
								  .append(id, rhs.id)
								  .append(description, rhs.description)
								  .isEquals();
	}

	@Override
	public String toString() {
		return new StrBuilder().append('{')
							   .append("id:").append(String.valueOf(id))
							   .append(",description:").append((null == description) ? "null" : ("\"" + description + "\""))
							   .append('}')
							   .toString();
	}

}
