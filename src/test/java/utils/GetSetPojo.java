/*
 * Copyright (c) 2016 Thomson Reuters/ONESOURCE. All rights reserved.
 */

package utils;

import java.util.Date;

/**
 * A POJO used by the {@code PojoBuilderTest}
 *
 * @author Rick Rupp
 */
@SuppressWarnings({"WeakerAccess", "JavaDoc"})
public class GetSetPojo {

	private String name;
	private Integer value;
	private Date date;

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(final Integer value) {
		this.value = value;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(final Date date) {
		this.date = date;
	}

}
