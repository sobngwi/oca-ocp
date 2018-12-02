package org.sobngwi.oca.dates;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

public class ZonedDateTimeSample {

    private static Logger log = Logger.getLogger(ZonedDateTimeSample.class.getName());


    public static void main(String[] args) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd MMMM  yyyy  hh:mm:ss,n a");

        // Leaving from San Francisco on July 20, 2013, at 7:30 p.m.
        LocalDateTime leaving = LocalDateTime.of(2013, Month.JULY, 20, 19, 30, 55, 777);
        ZoneId leavingZone = ZoneId.of("America/Los_Angeles");
        ZonedDateTime departure = ZonedDateTime.of(leaving, leavingZone);
        log.info("Leaving on : " + departure.format(format));


        // Flight is 10 hours and 50 minutes, or 650 minutes
        log.info(" Flight is 10 hours and 50 minutes, or 650 minutes") ;
        ZoneId arrivingZone = ZoneId.of("Asia/Tokyo");
        ZonedDateTime arrival = departure.withZoneSameInstant(arrivingZone)
                .plusMinutes(650);
        log.info("ARRIVING on : " + arrival.format(format));

        if (arrivingZone.getRules().isDaylightSavings(arrival.toInstant()))
            log.info("  (" + arrivingZone + " daylight saving time will be in effect.)" +
                    arrivingZone);
        else
            log.info("  (" + arrivingZone  + " standard time will be in effect.)");

    }
}
