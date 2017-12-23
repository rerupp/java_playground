/*
 * Copyright (c) 2016 Thomson Reuters/ONESOURCE. All rights reserved.
 */

package utils;

import org.junit.Test;

import java.time.Instant;
import java.util.Date;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

/**
 * Test cases to verify the pojo builder works as expected.
 *
 * @author Rick Rupp
 */
@SuppressWarnings("JavaDoc")
public class PojoBuilderTest {

	@Test
	public void testSimpleCreate() throws Exception {

		final String name = "simple";
		final Integer value = 10;
		final Date date = new Date();

		final GetSetPojo pojo = PojoBuilder.of(GetSetPojo::new)
										   .with(GetSetPojo::setName, name)
										   .with(GetSetPojo::setValue, value)
										   .with(GetSetPojo::setDate, date)
										   .build();
		assertThat("simple create name", pojo.getName(), is(name));
		assertThat("simple create value", pojo.getValue(), is(value));
		assertThat("simple create date", pojo.getDate(), is(date));
	}

	@Test
	public void testComplexCreate() throws Exception {

		final PojoBuilder<GetSetPojo> builder = PojoBuilder.of(GetSetPojo::new);

		final String name = "complex";
		final Integer value = 100;
		final Date date = new Date();

		final GetSetPojo first = builder.with(GetSetPojo::setName, name)
										.with(GetSetPojo::setValue, value)
										.with(GetSetPojo::setDate, date)
										.build();
		assertThat("simple create name", first.getName(), is(name));
		assertThat("simple create value", first.getValue(), is(value));
		assertThat("simple create date", first.getDate(), is(date));

		final Date epoch = Date.from(Instant.EPOCH);
		final GetSetPojo second = builder.with(GetSetPojo::setDate, epoch)
										 .build();
		assertThat("expected second bean to be different instance", second, not(first));
		assertThat("simple create name", second.getName(), is(name));
		assertThat("simple create value", second.getValue(), is(value));
		assertThat("simple create date", second.getDate(), is(epoch));

		final Integer thirdValue = 54321;
		builder.resetBeanSetters();
		final GetSetPojo third = builder.with(GetSetPojo::setValue, thirdValue)
										.build();
		assertThat("expected third bean to be different instance", third, not(first));
		assertThat("expected third bean to be different instance", third, not(second));
		assertNull("name", third.getName());
		assertThat("simple create value", third.getValue(), is(thirdValue));
		assertNull("date", third.getDate());

	}

	@Test
	public void testSimpleReadOnlyPojoCreate() throws Exception {

		final String name = "simple";
		final Integer value = 10;
		final Date date = new Date();

		final ReadOnlyPojo pojo = ReadOnlyPojo.builder()
											  .setName(name)
											  .setValue(value)
											  .setDate(date)
											  .build();
		assertThat("simple create name", pojo.getName(), is(name));
		assertThat("simple create value", pojo.getValue(), is(value));
		assertThat("simple create date", pojo.getDate(), is(date));
	}

	@Test
	public void testComplexReadOnlyPojoCreate() throws Exception {

		final ReadOnlyPojo.Builder builder = ReadOnlyPojo.builder();

		final String name = "complex";
		final Integer value = 100;
		final Date date = new Date();

		final ReadOnlyPojo first = builder.setName(name)
										  .setValue(value)
										  .setDate(date)
										  .build();
		assertThat("simple create name", first.getName(), is(name));
		assertThat("simple create value", first.getValue(), is(value));
		assertThat("simple create date", first.getDate(), is(date));

		final Date epoch = Date.from(Instant.EPOCH);
		final ReadOnlyPojo second = builder.setDate(epoch)
										   .build();
		assertThat("expected second bean to be different instance", second, not(first));
		assertThat("simple create name", second.getName(), is(name));
		assertThat("simple create value", second.getValue(), is(value));
		assertThat("simple create date", second.getDate(), is(epoch));

		final Integer thirdValue = 54321;
		builder.resetBeanSetters();
		final ReadOnlyPojo third = builder.setValue(thirdValue)
										  .build();
		assertThat("expected third bean to be different instance", third, not(first));
		assertThat("expected third bean to be different instance", third, not(second));
		assertNull("name", third.getName());
		assertThat("simple create value", third.getValue(), is(thirdValue));
		assertNull("date", third.getDate());

	}

}