/*
 * Copyright 2015 Thomson Reuters/ONESOURCE. All rights reserved.
 */

package tds.datetime;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Email examples for EA time questions.
 *
 * @author Rick Rupp on 3/9/15.
 */
public class DateTimeExampleTest {

    @Test
    public void zoneId() {
        final ZoneId zoneId = ZoneId.of("US/Eastern");
        println("zoneId: %s", zoneId);
    }

//    @Test
//    public void availableZoneIds() {
//        println("availableZoneIds");
//        ZoneId.getAvailableZoneIds().stream().filter(zoneId -> zoneId.startsWith("US")).forEach(System.out::println);
//    }

    @Test
    public void example() {
//		final ZoneId eastern = ZoneId.of("US/Eastern");
//		final ZoneId pacific = ZoneId.of("US/Pacific");

        println("example");
        println(ZonedDateTime.of(2015, 3, 9, 0, 0, 0, 0, ZoneId.of("UTC")));
        println(ZonedDateTime.of(2015, 3, 9, 0, 0, 0, 0, ZoneId.of("US/Eastern")));
        println(ZonedDateTime.of(2015, 3, 9, 0, 0, 0, 0, ZoneId.of("US/Pacific")));

        final LocalDateTime localDateTime = LocalDateTime.of(2015, 3, 9, 0, 0, 0, 0);
        println("localtime: %s", localDateTime);
        println("localtime UTC: %s", ZonedDateTime.of(localDateTime, ZoneId.of("UTC")));
        println("withZoneSameInstant: %s", ZonedDateTime.of(localDateTime, ZoneId.of("UTC")).withZoneSameInstant(ZoneId.of("US/Pacific")));
    }


    @Test
    public void parser() {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM-dd-yyyy");
//                                                             .withZone(ZoneId.of("UTC"));
        final LocalDate utcDate = LocalDate.parse("Jun-09-2015", formatter);
        final Date date = Date.from(utcDate.atStartOfDay(ZoneId.of("UTC")).toInstant());
        println("UTC Date='%s' Date='%s'", utcDate, date);
    }

    @Test public void compareTo() throws Exception {
        Date date1 = new Date();
        Thread.sleep(100);
        Date date2 = new Date();
        System.out.printf("date1-date2: %d\n", new CompareToBuilder().append(date1, date2).toComparison());
        System.out.printf("date2-date1: %d\n", new CompareToBuilder().append(date2, date1).toComparison());
        System.out.printf("null-date2: %d\n", new CompareToBuilder().append(null, date2).toComparison());
        System.out.printf("date1-null: %d\n", new CompareToBuilder().append(date1, null).toComparison());
    }

    @Test
    public void dateOffsetTest() {
        final Date now = new Date();
        final Date yesterday = Date.from(now.toInstant().minusMillis(24 * 60 * 60 * 1000));
        System.out.printf("now=%s yesterday=%s", now.toInstant(), yesterday.toInstant());
    }

//
//    @Test
//    public void timeZoneOffsets() {
//        final List<Pair<ZoneId, Integer>> zoneAndOffset = new LinkedList<>();
//        ZoneId.getAvailableZoneIds().forEach(zoneId -> {
//            final ZoneId zone = ZoneId.of(zoneId);
//            final int secondsOfHour = LocalDateTime.now().atZone(zone).getOffset().getTotalSeconds();
//            zoneAndOffset.add(Pair.of(zone, secondsOfHour));
//        });
//        Collections.sort(zoneAndOffset, (lhs, rhs) -> {
//            int result = lhs.getRight() - rhs.getRight();
//            if (result == 0) {
//                result = lhs.getLeft().getId().compareTo(rhs.getLeft().getId());
//            }
//            return result;
//        });
//        zoneAndOffset.forEach(pair -> {
//            int secondsOfHour = pair.getRight();
//            final int seconds = pair.getRight() % 60;
//            secondsOfHour /= 60;
//            final int minutes = secondsOfHour % 60;
//            secondsOfHour /= 60;
//            final int hours = secondsOfHour % 60;
//            println("%s=%2d:%02d:%02d", pair.getLeft(), hours, minutes, seconds);
//        });
//    }

    void println(final Object msg) {
        System.out.println(msg);
    }

    void println(final String fmt, Object... args) {
        try {
            println(String.format(fmt, args));
        } catch (final Exception e) {
            println("Error printing '" + fmt + "': " + e.getMessage());
        }

    }
}
