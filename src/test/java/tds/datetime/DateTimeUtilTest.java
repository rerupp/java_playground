/*
 * Copyright 2015 Thomson Reuters/ONESOURCE. All rights reserved.
 */

package tds.datetime;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class DateTimeUtilTest {

	private final Logger log = LoggerFactory.getLogger(DateTimeUtilTest.class);
	private final DateTimeUtil dateTimeUtil = new DateTimeUtil();

	@Test
	public void testDateTimeUsage() {

		final Date date = new Date();
		final Instant instant = date.toInstant();
		final LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
		final ZonedDateTime zonedDateTime = ZonedDateTime.of(localDateTime, ZoneId.systemDefault());

		println("");
		println("***** Running %s *****", new Exception().getStackTrace()[0].getMethodName());
		println("default ZoneId: " + ZoneId.systemDefault());
		println("java.util.Date: %s", date);
		println("Instant: %s", instant);
		println("LocalDateTime: %s", localDateTime);
		println("ZonedDateTime: %s", zonedDateTime);

		compare(date, zonedDateTime);
	}

	@Test
	public void testToZonedDateTime() {

		final Date date = new Date();
		final ZonedDateTime zonedDateTime = dateTimeUtil.toZonedDateTime(date);

		println("");
		println("***** Running %s *****", new Exception().getStackTrace()[0].getMethodName());
		println("java.util.Date: %s", date);
		println("ZonedDateTime: %s", zonedDateTime);

		compare(date, zonedDateTime);
	}

	@Test
	public void testToLocalDateTime() {

		final Date date = new Date();
		final LocalDateTime localDateTime = dateTimeUtil.toLocalDateTime(date);

		println("");
		println("***** Running %s *****", new Exception().getStackTrace()[0].getMethodName());
		println("java.util.Date: %s", date);
		println("LocalDateTime: %s", localDateTime);

		compare(date, localDateTime);
	}

	@Test
	public void testAdjustToUTC() {

		final Date date = new Date();
		final Date utc = dateTimeUtil.adjustToUTC(date);

		println("");
		println("***** Running %s *****", new Exception().getStackTrace()[0].getMethodName());
		println("java.util.Date (localtime): %s", date);
		println("java.util.Date (UTC): %s", utc);

		final int zoneOffsetSeconds = TimeZone.getDefault().getOffset(utc.getTime());
		assertThat("milliseconds", date.getTime() + zoneOffsetSeconds, is(utc.getTime()));
	}

	@Test
	public void testAdjustZonedDateTimeToUTC() {

		final ZonedDateTime zonedDateTime = ZonedDateTime.now();
		final ZonedDateTime utc = dateTimeUtil.adjustToUTC(zonedDateTime);

		println("");
		println("***** Running %s *****", new Exception().getStackTrace()[0].getMethodName());
		println("ZonedDateTime (localtime): %s", zonedDateTime);
		println("ZonedDateTime (UTC): %s", utc);

		assertThat("year", utc.getYear(), is(zonedDateTime.getYear()));
		assertThat("month", utc.getMonth(), is(zonedDateTime.getMonth()));
		assertThat("day", utc.getDayOfMonth(), is(zonedDateTime.getDayOfMonth()));
		assertThat("hour", utc.getHour(), is(zonedDateTime.getHour()));
		assertThat("minute", utc.getMinute(), is(zonedDateTime.getMinute()));
		assertThat("second", utc.getSecond(), is(zonedDateTime.getSecond()));
		assertThat("nano", utc.getNano(), is(zonedDateTime.getNano()));
		assertThat("ZoneId", utc.getZone(), is(ZoneOffset.UTC));
	}

	@Test
	@SuppressWarnings("deprecation")
	public void testAdjustToMidnight() {

		final Date date = new Date();
		final Date lt = dateTimeUtil.adjustToMidnight(date);

		println("");
		println("***** Running %s *****", new Exception().getStackTrace()[0].getMethodName());
		println("java.util.Date: %s", date);
		println("java.util.Date (adjusted localtime): %s", lt);

		assertThat("year", lt.getYear(), is(date.getYear()));
		assertThat("month", lt.getMonth(), is(date.getMonth()));
		assertThat("day", lt.getDay(), is(date.getDay()));
		assertThat("hour", lt.getHours(), is(0));
		assertThat("minute", lt.getMinutes(), is(0));
		assertThat("second", lt.getSeconds(), is(0));
	}

	// helpers

	private void compare(final Date date, final ZonedDateTime rhs) {
		final Calendar lhs = Calendar.getInstance();
		lhs.setTime(date);
		compare(lhs, rhs.toLocalDate());
		compare(lhs, rhs.toLocalTime());
	}

	private void compare(final Date date, final LocalDateTime rhs) {
		final Calendar lhs = Calendar.getInstance();
		lhs.setTime(date);
		compare(lhs, rhs.toLocalDate());
		compare(lhs, rhs.toLocalTime());
	}

	private void compare(final Calendar lhs, final LocalDate rhs) {
		assertThat("year", rhs.getYear(), is(lhs.get(Calendar.YEAR)));
		assertThat("month", rhs.getMonthValue() - 1, is(lhs.get(Calendar.MONTH)));
		assertThat("day", rhs.getDayOfMonth(), is(lhs.get(Calendar.DAY_OF_MONTH)));
	}

	private void compare(final Calendar lhs, final LocalTime rhs) {
		assertThat("hour", rhs.getHour(), is(lhs.get(Calendar.HOUR_OF_DAY)));
		assertThat("minute", rhs.getMinute(), is(lhs.get(Calendar.MINUTE)));
		assertThat("second", rhs.getSecond(), is(lhs.get(Calendar.SECOND)));
	}

	private void println(final String format, final Object... args) {
		try {
			log.info(String.format(format, args));
		} catch (final Exception e) {
			log.error("Error logging '" + format + "'", e);
		}
	}

}