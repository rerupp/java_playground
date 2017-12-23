/*
 * Copyright 2015 Thomson Reuters/ONESOURCE. All rights reserved.
 */

package tds.datetime;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Objects;

/**
 * Java 8 date and time helper utilities and usage examples.
 *
 * @author Rick Rupp on 1/9/15.
 */
@SuppressWarnings("WeakerAccess")
public class DateTimeUtil {

	/**
	 * Converts a {@code ZonedDateTime} to a {@code Date} object.
	 *
	 * @param zonedDateTime The date and time that will be converted.
	 * @return the corresponding date object.
	 * @throws NullPointerException if the parameter is {@code null}.
	 */
	public Date toDate(final ZonedDateTime zonedDateTime) {
		Objects.requireNonNull(zonedDateTime, "ZonedDateTime cannot be null...");
		return Date.from(Instant.from(zonedDateTime));
	}

	/**
	 * Converts a {@code LocalDateTime} to a {@code Date} object.
	 *
	 * @param localDateTime The local date and time that will be converted.
	 * @return the corresponding date object.
	 * @throws NullPointerException if the parameter is {@code null}.
	 */
	public Date toDate(final LocalDateTime localDateTime) {
		Objects.requireNonNull(localDateTime, "LocalDateTime cannot be null...");
		return Date.from(Instant.from(localDateTime));
	}

	/**
	 * Converts a {@code Date} to a {@code ZonedDateTime} object. The system default will be used as the default zone.
	 *
	 * @param date The date that will be converted.
	 * @return the corresponding zoned date and time object.
	 * @throws NullPointerException if the parameter is {@code null}.
	 */
	public ZonedDateTime toZonedDateTime(final Date date) {
		Objects.requireNonNull(date, "Date cannot be null...");
		return ZonedDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
	}

	/**
	 * Converts a {@code Date} to a {@code LocalDateTime} object. The system default will be used as the default zone.
	 *
	 * @param date The date that will be converted.
	 * @return the corresponding local date and time object.
	 * @throws NullPointerException if the parameter is {@code null}.
	 */
	public LocalDateTime toLocalDateTime(final Date date) {
		Objects.requireNonNull(date, "Date cannot be null...");
		return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
	}

	/**
	 * Converts a {@code Date} from the system default timezone into the UTC timezone. The milliseconds of the returned value
	 * will have been adjusted by the system default timezone offset.
	 *
	 * @param date The date that will be converted to UTC time.
	 * @return the date adjusted to UTC time.
	 * @throws NullPointerException if the parameter is {@code null}.
	 */
	public Date adjustToUTC(final Date date) {
		return toDate(ZonedDateTime.of(toLocalDateTime(date), ZoneOffset.UTC));
	}

	/**
	 * Converts a {@code ZonedDateTime} to be UTC without altering the local time value.
	 *
	 * @param zonedDateTime The zoned date time that will be converted.
	 * @return the zoned date time in UTC.
	 * @throws NullPointerException if the parameterr is {@code null}.
	 */
	public ZonedDateTime adjustToUTC(final ZonedDateTime zonedDateTime) {
		Objects.requireNonNull(zonedDateTime, "ZonedDateTime cannot be null...");
		return zonedDateTime.withZoneSameLocal(ZoneOffset.UTC);
	}

	/**
	 * Converts the {@code Date} such that the date portion remains intact however the time portion is set to midnight.
	 *
	 * @param date The date that will be converted.
	 * @return the date with time equal to midnight.
	 * @throws NullPointerException if the parameter is {@code null}.
	 */
	public Date adjustToMidnight(final Date date) {
		return toDate(ZonedDateTime.of(toLocalDate(date), LocalTime.MIDNIGHT, ZoneId.systemDefault()));
	}

	/**
	 * @param date
	 * @return
	 * @throws NullPointerException if the parameter is {@code null}.
	 */
	public LocalDate toLocalDate(final Date date) {
		return toZonedDateTime(date).toLocalDate();
	}

	/**
	 * @param date
	 * @return
	 * @throws NullPointerException if the parameter is {@code null}.
	 */
	public LocalTime toLocalTime(final Date date) {
		return toZonedDateTime(date).toLocalTime();
	}
}
