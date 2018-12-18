package org.sobngwi.oca.dates;

import org.junit.Test;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoUnit;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class Dates {

    @Test
    public void durationOf60secondsEqualsDurationOf1minute() {

        String durationOf6OSeconds = Duration.of(60, ChronoUnit.SECONDS).toString();
        String durationOf1minutue = Duration.of(1, ChronoUnit.MINUTES).toString();

        assertThat(durationOf1minutue, equalTo(durationOf6OSeconds));
        assertThat(durationOf1minutue, equalTo(Duration.ofMinutes(1).toString()));
    }

    @Test
    public void startMonthOfLcalDateConstructorIs_1_Based_ratherThan_0_based() {

        assertThat(LocalDate.of(2014,5,21).toString(),
                not(equalTo(LocalDate.of(2014, Month.JUNE, 21))));
        assertThat(LocalDate.of(2014,5,21),
                equalTo(LocalDate.of(2014, Month.MAY, 21)));

    }

    @Test
    public void savingTimes() {

        LocalDate date = LocalDate.of(2016, Month.MARCH, 13);
        LocalTime time = LocalTime.of(1, 30);

        ZoneId zone = ZoneId.of("US/Eastern"); // GMT-5

        ZonedDateTime dateTime1 = ZonedDateTime.of(date, time, zone);
        ZonedDateTime dateTime2 = dateTime1.plus(1, ChronoUnit.HOURS); // + 1 H

        long hours = ChronoUnit.HOURS.between(dateTime1, dateTime2);
        int clock1 = dateTime1.getHour();
        int clock2 = dateTime2.getHour();

        assertEquals(hours, 1L);
        assertThat(clock2 ,equalTo(clock1 + 2));
    }

    @Test
    public void formatterDateToDormat_formatToDate_SHORT() {
        LocalDateTime d = LocalDateTime.of(2015, 5, 10, 11, 22, 33);
        Period p = Period.of(1, 2, 3);
        d = d.minus(p);
        DateTimeFormatter f = DateTimeFormatter.
                ofLocalizedTime(FormatStyle.SHORT);

        assertThat(d.format(f),equalTo("11:22"));
        assertThat(f.format(d),equalTo("11:22"));

    }

    @Test
    public void formaterLocalisedDateTime() {

        LocalDateTime d = LocalDateTime.of(2015, 5, 10, 11, 22, 33);
        Period p = Period.ofDays(1);
        d = d.minus(p);
        DateTimeFormatter f = DateTimeFormatter.
                ofLocalizedDateTime(FormatStyle.SHORT);

        assertThat(d.format(f),equalTo("09/05/15 11:22"));
        assertThat(f.format(d),equalTo("09/05/15 11:22"));
    }
    @Test
    public void formaterLocalisedDateTimeOnlyLastPeriodCount() {

        LocalDateTime d = LocalDateTime.of(2015, 5, 10, 11, 22, 33);
        Period p = Period.ofDays(1).ofYears(2);
        d = d.minus(p);
        DateTimeFormatter f = DateTimeFormatter.
                ofLocalizedDateTime(FormatStyle.SHORT);

        assertThat(d.format(f),equalTo("10/05/13 11:22"));
        assertThat(f.format(d),equalTo("10/05/13 11:22"));
    }
}
