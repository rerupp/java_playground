/*
 * Copyright (c) 2016 Thomson Reuters/ONESOURCE. All rights reserved.
 */

package utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * A builder that can be used to build any POJO. It is modeled after a GenericBuilder described in a Stack Overflow
 * article discussing how to implement the builder pattern in Java 8.
 *
 * @param <T> the type of bean that will be created and initialized.
 * @author Rick Rupp
 */
@SuppressWarnings({"JavaDoc", "unused", "WeakerAccess"})
public class PojoBuilder<T> {

	private final Supplier<T> pojoCreator;
	private final List<Consumer<T>> beanSetters;

	protected PojoBuilder(final Supplier<T> beanCreator) {
		pojoCreator = Objects.requireNonNull(beanCreator, "The bean instance creator Supplier cannot be null...");
		beanSetters = new ArrayList<>();
	}

	/**
	 * The public API to instantiate a POJO builder.
	 *
	 * @param pojo a supplier that returns a new instance of the POJO that will be built.
	 * @param <T>  the type of POJO being built.
	 * @return a POJO builder that can create and initialize an instance of the POJO.
	 */
	public static <T> PojoBuilder<T> of(final Supplier<T> pojo) {
		return new PojoBuilder<>(pojo);
	}

	/**
	 * Clears the bean setters.
	 *
	 * @see #with(BiConsumer, Object)
	 * @see #with(Consumer)
	 */
	public void resetBeanSetters() {
		beanSetters.clear();
	}

	/**
	 * Create an instance of the POJO applying the setters that have been set into the builder.
	 *
	 * @return an initialized instance of the POJO.
	 * @see #with(BiConsumer, Object)
	 * @see #with(Consumer)
	 */
	public T build() {
		final T instance = pojoCreator.get();
		beanSetters.forEach(beanSetter -> beanSetter.accept(instance));
		return instance;
	}

	/**
	 * Adds a setter to the builder which will be used to initialize a field in the POJO when it is being built.
	 *
	 * @param beanSetter a POJO setter function that accepts the POJO instance and value that will be set on the field.
	 * @param value      the value that will be set into the POJO when it is built.
	 * @param <V>        the type of the value.
	 * @return the builder instance to allow call chaining.
	 * @see #with(Consumer)
	 * @see #build()
	 */
	public <V> PojoBuilder<T> with(final BiConsumer<T, V> beanSetter, final V value) {
		Objects.requireNonNull(beanSetter, "The bean setter Consumer cannot be null...");
		return with(instance -> beanSetter.accept(instance, value));
	}

	/**
	 * Adds a setter to the builder that will be used to initialize a field in the POJO when it is being built.
	 *
	 * @param beanSetter a POJO setter function that accepts the POJO instance and will initialize a field.
	 * @return the builder instance to allow call chaining.
	 * @see #build()
	 */
	public PojoBuilder<T> with(final Consumer<T> beanSetter) {
		beanSetters.add(Objects.requireNonNull(beanSetter, "The bean setter Consumer cannot be null..."));
		return this;
	}

}
