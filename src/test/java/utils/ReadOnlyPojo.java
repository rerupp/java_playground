/*
 * Copyright (c) 2016 Thomson Reuters/ONESOURCE. All rights reserved.
 */

package utils;

import java.util.Date;

/**
 * This POJO demonstrates how the {@code PojoBuilder} can be used when the class is not publicly mutable.
 *
 * @author Rick Rupp
 */
@SuppressWarnings({"WeakerAccess", "JavaDoc"})
public class ReadOnlyPojo {

	private String name;
	private Integer value;
	private Date date;

	public static ReadOnlyPojo.Builder builder() {
		return new ReadOnlyPojo.Builder();
	}

	public String getName() {
		return name;
	}

	public Integer getValue() {
		return value;
	}

	public Date getDate() {
		return date;
	}

	public static class Builder extends PojoBuilder<ReadOnlyPojo> {

		public Builder() {
			super(ReadOnlyPojo::new);
		}

		public Builder setName(final String name) {
			with(instance -> instance.name = name);
			return this;
		}

		public Builder setValue(final Integer value) {
			with(instance -> instance.value = value);
			return this;
		}

		public Builder setDate(final Date date) {
			with(instance -> instance.date = date);
			return this;
		}

	}

}
