/*
 * Copyright (c) 2016 Thomson Reuters/ONESOURCE. All rights reserved.
 */

package determination.cloud.auth.authorization;

/**
 * This enumeration is used to by {@code Authorization} to a identify what component is being authorized.
 *
 * @author Rick Rupp
 */
public enum AuthorizationComponent {

	/**
	 * The common component tag.
	 */
	COMMON("Comon Component"),

	/**
	 * The custom component tag.
	 */
	CUSTOM("Custom Component");

	final String name;

	/**
	 * Creates an instance of the enumeration that initializes the external name of the component.
	 * @param name the external name of the component.
	 */
	AuthorizationComponent(final String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
