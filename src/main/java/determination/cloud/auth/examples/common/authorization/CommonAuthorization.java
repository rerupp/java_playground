/*
 * Copyright (c) 2016 Thomson Reuters/ONESOURCE. All rights reserved.
 */

package determination.cloud.auth.examples.common.authorization;

import determination.cloud.auth.authorization.Action;
import determination.cloud.auth.authorization.AuthorizationComponent;
import determination.cloud.auth.examples.common.managers.CommonManagerImpl;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A singleton that manages authorization for common cases. The authorization is gated by a {@link Action} collection that is specific
 * to a {@link AuthorizationComponent} and an identifier.
 *
 * @author Rick Rupp
 */
public final class CommonAuthorization {

	private static final Map<String, Map<Long, Set<Action>>> COMPONENT_AUTHORIZATIONS = new ConcurrentHashMap<>();
	private static final Set<Action> DEFAULT_ACTIONS = EnumSet.noneOf(Action.class);
	private static final Logger LOG = LoggerFactory.getLogger(CommonManagerImpl.class);

	/**
	 * Try to prevent others from instantiating an instance of the class.
	 */
	private CommonAuthorization() {
	}

	/**
	 * Checks to see if a component has authorization to an identifier. The identifier will be ignored when checking authorization
	 * for the {@link Action#CREATE CREATE} action.
	 *
	 * @param component the authorization component whose identifier will be checked.
	 * @param action    the action that will be checked.
	 * @param id        the identifier associated with the component.
	 * @return {@code true} if the action is set, {@code false} otherwise.
	 * @throws NullPointerException if the component is not provided.
	 */
	@SuppressWarnings("WeakerAccess")
	public static boolean isAuthorized(final AuthorizationComponent component, final Action action, final Long id) {
		// special case the CREATE action so it is not specific to an ID
		final Set<Action> actions = getAuthorizations(component).get((action == Action.CREATE) ? null : id);
		return ObjectUtils.defaultIfNull(actions, DEFAULT_ACTIONS).contains(action);
	}

	/**
	 * Initialize the {@code Action} collection that will be used to gate access to the {@code AuthorizationComponent} and
	 * associated identifier.
	 *
	 * @param component the authorization component the actions are associated with.
	 * @param id        the identifier associated with the component.
	 * @param actions   the actions allowed.
	 * @throws NullPointerException if the component is not provided.
	 */
	public static void set(final AuthorizationComponent component, final Long id, final Set<Action> actions) {
		getAuthorizations(component).put(id, actions);
	}

	/**
	 * Used internally to get the collection of identifier actions associated with the authorization component.
	 *
	 * @param component the authorization component actions are associated with.
	 * @return the collection of identifiers and actions associated with the component (it will never be {@code null}).
	 * @throws NullPointerException if the component is not provided.
	 */
	private static Map<Long, Set<Action>> getAuthorizations(final AuthorizationComponent component) {
		Objects.requireNonNull(component, "AuthorizationComponent cannot be null...");
		return COMPONENT_AUTHORIZATIONS.computeIfAbsent(component.getName(), ignore -> {
			LOG.info("Creating authorization for {}", component);
			// a concurrent map doesn't allow null keys so use a synchronized version
			return Collections.synchronizedMap(new HashMap<>());
		});
	}

	/**
	 * Used by unit tests to set authorization to a pristine state.
	 */
	public static void clear() {
		COMPONENT_AUTHORIZATIONS.clear();
	}

}
