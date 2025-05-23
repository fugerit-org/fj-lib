package test.org.fugerit.java.core.db.daogein;

import lombok.extern.slf4j.Slf4j;
import org.fugerit.java.core.db.daogen.SQLTypeConverter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalField;
import java.util.Calendar;

@Slf4j
class TestSQLTypeConverter {

    private static final int TEST_YEAR = 2024;

    private static final int TEST_HOUR = 11;

    @Test
    void testLocalDate() {
        LocalDate d = SQLTypeConverter.utilDateToLocalDate(Date.valueOf( TEST_YEAR+"-05-05" ));
        log.info( "local date : {}", d );
        Assertions.assertEquals( TEST_YEAR, d.getYear() );
        Date t = SQLTypeConverter.localDateToSqlDate( d );
        log.info( "sql date : {}", t );
        Calendar c = Calendar.getInstance();
        c.setTime(t);
        Assertions.assertEquals( TEST_YEAR, c.get( Calendar.YEAR ) );
    }

    @Test
    void testLocalDateTime() {
        LocalDateTime d = SQLTypeConverter.utilDateToLocalDateTime(Timestamp.valueOf( TEST_YEAR+"-05-05 "+TEST_HOUR+":30:00.000" ));
        log.info( "local date time : {}", d );
        Assertions.assertEquals( TEST_YEAR, d.getYear() );
        Timestamp t = SQLTypeConverter.localDateTimeToSqlTimestamp( d );
        log.info( "timestamp : {}", t );
        Calendar c = Calendar.getInstance();
        c.setTime(t);
        Assertions.assertEquals( TEST_YEAR, c.get( Calendar.YEAR ) );
    }

    @Test
    void testLocalTime() {
        LocalTime d = SQLTypeConverter.utilDateToLocalTime(Time.valueOf( TEST_HOUR+":30:00" ));
        log.info( "local time : {}", d );
        Assertions.assertEquals( TEST_HOUR, d.getHour() );
        Time t = SQLTypeConverter.localTimeToSqlTime( d );
        log.info( "time : {}", t );
        Calendar c = Calendar.getInstance();
        c.setTime(t);
        Assertions.assertEquals( TEST_HOUR, c.get( Calendar.HOUR_OF_DAY ) );
    }

}
