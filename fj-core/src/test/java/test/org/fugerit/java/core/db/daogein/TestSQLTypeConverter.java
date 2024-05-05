package test.org.fugerit.java.core.db.daogein;

import lombok.extern.slf4j.Slf4j;
import org.fugerit.java.core.db.daogen.SQLTypeConverter;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Slf4j
public class TestSQLTypeConverter {

    private static final int TEST_YEAR = 2024;

    @Test
    public void testLocalDate() {
        LocalDate d = SQLTypeConverter.utilDateToLocalDate(Date.valueOf( TEST_YEAR+"-05-05" ));
        log.info( "local date : {}", d );
        Assert.assertEquals( TEST_YEAR, d.getYear() );
    }

    @Test
    public void testLocalDateTime() {
        LocalDateTime d = SQLTypeConverter.utilDateToLocalDateTime(Timestamp.valueOf( TEST_YEAR+"-05-05 11:30:00.000" ));
        log.info( "local date time : {}", d );
        Assert.assertEquals( TEST_YEAR, d.getYear() );
    }

}
