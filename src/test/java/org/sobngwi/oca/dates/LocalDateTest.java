package org.sobngwi.oca.dates;

import org.junit.Before;
import org.junit.Test;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.UnsupportedTemporalTypeException;
import java.util.logging.Logger;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;

public class LocalDateTest {

    private final Logger log = Logger.getLogger(LocalDateTest.class.getCanonicalName());
    private LocalDate todayIs27112018;
    private int dayOfMonth;
    private int year;
    private int month;
    private Period oneMonth;
    private Period singleMonth;
    private LocalDate januaryOne;
    private Period longTime;
    private Period longTimeNormalized;


    @Before
    public void setUp(){
        todayIs27112018 = LocalDate.of(2018, Month.NOVEMBER, 27);
        dayOfMonth = todayIs27112018.getDayOfMonth();
        year = todayIs27112018.getYear();
        month= todayIs27112018.getMonthValue();
        oneMonth = Period.of(0, 0, 31);
        singleMonth = Period.of(0, 1, 0);
        januaryOne = LocalDate.of(2018, 1, 1);
        longTime = Period.of(1, 14, 366);
        longTimeNormalized = longTime.normalized();
   }

    @Test( expected = NullPointerException.class)
    public void add_null_as_parameter_throws_NPE(){
        todayIs27112018.minus(null);
        fail();
    }

   @Test( expected = UnsupportedTemporalTypeException.class)
    public void add_hours_to_days_throws_exception(){
       todayIs27112018.plus(2, ChronoUnit.HOURS);
       fail();
    }

    @Test( expected = UnsupportedTemporalTypeException.class)
    public void add_minutes_to_days_throws_exception(){
        todayIs27112018.plus(1, ChronoUnit.MINUTES);
        fail();
    }

    @Test( expected = UnsupportedTemporalTypeException.class)
    public void add_seconds_to_days_throws_exception(){
        todayIs27112018.plus(1, ChronoUnit.SECONDS);
        fail();
    }

    @Test(expected =  DateTimeException.class)
    public void invalid_month_on_date_creation_throws_an_exception(){
        // month value is 1 base ie 1= January
        //Invalid value for MonthOfYear (valid values 1 - 12)
        todayIs27112018 = LocalDate.of(2018, 0, 27);
        fail();
    }

    @Test(expected =  DateTimeException.class)
    public void invalid_day_on_date_creation_throws_an_exception(){
       //Invalid value for DayOfMonth (valid values 1 - 28/31)
       todayIs27112018 = LocalDate.of(2018, 1, 32);
       fail();
    }

    @Test
    public void localdate_class_is_immutable(){

       todayIs27112018.plus(1, ChronoUnit.YEARS);
       todayIs27112018.plus(1, ChronoUnit.MONTHS);
       todayIs27112018.plus(1, ChronoUnit.DAYS);

       assertEquals(todayIs27112018.getDayOfMonth(), dayOfMonth);
       assertEquals(todayIs27112018.getMonthValue(), month);
       assertEquals(todayIs27112018.getYear(), year);

    }

    @Test
    public void add_a_number_a_day_more_than_dayOnmonthAllow_change_the_month() {
       LocalDate fourDaysLater = todayIs27112018.plus(4, ChronoUnit.DAYS);

       assertNotEquals(todayIs27112018.getMonth(), fourDaysLater.getMonth());
       assertEquals(fourDaysLater.getMonth(), Month.DECEMBER);
       assertEquals(fourDaysLater.getDayOfMonth(), 1);
    }

    @Test
    public void add_a_number_a_day_less_than_dayOnmonthAllow_maintain_the_month() {
        LocalDate threeDaysLater = todayIs27112018.plus(3, ChronoUnit.DAYS);

        assertEquals(todayIs27112018.getMonth(), threeDaysLater.getMonth());
        assertEquals(threeDaysLater.getMonth(), Month.NOVEMBER);
        assertEquals(threeDaysLater.getDayOfMonth(), 30);
    }

    @Test
    public void addMoreThan62DaysOnLocalDate(){
        LocalDate alainDateOfBirth = todayIs27112018.plus(38, ChronoUnit.DAYS);

        assertEquals(alainDateOfBirth.getMonth(), Month.JANUARY);
        assertEquals(alainDateOfBirth.getDayOfMonth(), 4);

    }

    @Test
    public void addPeriodToLocaldatesDependsOnContext(){

       LocalDate thirtyOneDaysPeriodLater = januaryOne.plus(oneMonth);
       LocalDate thirtyOneDay2TimessPeriodLater = januaryOne.plus(oneMonth).plus(oneMonth);
       LocalDate oneMonthPeriodLater = januaryOne.plus(singleMonth);
       LocalDate oneMonth2TimesPeriodLater = januaryOne.plus(singleMonth).plus(singleMonth);

        assertEquals(thirtyOneDaysPeriodLater.getMonth(), Month.FEBRUARY);
        assertEquals(thirtyOneDaysPeriodLater.getDayOfMonth(), 1);
        assertEquals(oneMonthPeriodLater.getMonth(), Month.FEBRUARY);
        assertEquals(oneMonthPeriodLater.getDayOfMonth(), 1);

        assertEquals(thirtyOneDay2TimessPeriodLater.getMonth(), Month.MARCH);
        assertEquals(thirtyOneDay2TimessPeriodLater.getDayOfMonth(), 4);
        assertEquals(oneMonth2TimesPeriodLater.getMonth(), Month.MARCH);
        assertEquals(oneMonth2TimesPeriodLater.getDayOfMonth(), 1);

    }

    @Test
    public void should_normalize_period_month_and_year(){

        assertThat(longTime.toString(), equalTo("P1Y14M366D"));
        assertThat(longTimeNormalized.toString(), equalTo("P2Y2M366D"));
        assertThat(longTime.getDays(), equalTo(longTimeNormalized.getDays()));

    }


    @Test
    public void formatDates(){
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd MM yyyy");

        String formatedDate = todayIs27112018.format(fmt);

        assertThat(formatedDate, allOf(containsString("27"), containsString("11"),
                containsString("2018")));

        fmt = DateTimeFormatter.ofPattern("dd MMM yyyy"); // print month (MMM) as nov.
        formatedDate = todayIs27112018.format(fmt);
        assertThat(formatedDate, allOf(containsString("27"), containsString("nov."),
                containsString("2018")));

        fmt = DateTimeFormatter.ofPattern("dd MMMM yyyy"); // print month (MMMN) as november.
        formatedDate = todayIs27112018.format(fmt);
        assertThat(formatedDate, allOf(containsString("27"), containsString("novembre"),
                containsString("2018")));

        fmt = DateTimeFormatter.ofPattern(" MMMM yyyy"); // dont print day (0 digit ) as november.
        formatedDate = todayIs27112018.format(fmt);
        assertThat(formatedDate, allOf(not(containsString("27")), containsString("novembre"),
                containsString("2018")));

        fmt = DateTimeFormatter.ofPattern("d MMMM yyyy"); //  Print 1 digit day only  as november.
        formatedDate = todayIs27112018.minusDays(20).format(fmt);
        assertThat(formatedDate , allOf ( not(containsString("27")), containsString("7"),
                containsString("novembre"), containsString("2018") ));

        fmt = DateTimeFormatter.ofPattern("dd MMMM yyyy"); //  Print 2 digit day  (with 0  ) as november.
        formatedDate = todayIs27112018.minusDays(20).format(fmt);
        assertThat(formatedDate , allOf ( containsString("07"),
                containsString("novembre"), containsString("2018") ));

        fmt = DateTimeFormatter.ofPattern("dd MMMM yy"); //  Print 2 digit year 18.
        formatedDate = todayIs27112018.format(fmt);
        log.info(formatedDate);
        assertThat(formatedDate , allOf ( containsString("27"),
                containsString("novembre"), not(containsString("2018")),
                containsString("18") ));

        fmt = DateTimeFormatter.ofPattern("dd MMMM yyyyyy"); //  Print 6 digit year 002018.
        formatedDate = todayIs27112018.format(fmt);
        log.info(formatedDate);
        assertThat(formatedDate , allOf ( containsString("27"),
                containsString("novembre"),
                containsString("002018") ));

        fmt = DateTimeFormatter.ofPattern("dd MMMM y  hh:mm:ss a"); //  Print 2018 (for y digit year) .
        formatedDate = todayIs27112018.atStartOfDay().format(fmt);
        log.info(formatedDate);
        assertThat(formatedDate , allOf ( containsString("27"),
                containsString("novembre"),
                containsString("2018") ));
    }

}
