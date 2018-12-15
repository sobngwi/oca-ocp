package org.sobngwi.oca.dates;

import org.junit.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.core.IsEqual.equalTo;
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
}
