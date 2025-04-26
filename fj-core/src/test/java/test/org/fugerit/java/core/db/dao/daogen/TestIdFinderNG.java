package test.org.fugerit.java.core.db.dao.daogen;

import lombok.extern.slf4j.Slf4j;
import org.fugerit.java.core.db.daogen.IdFinderNG;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
class TestIdFinderNG {

    private static final long TEST = 1000L;

    @Test
    void testIdFinderNg() {
        IdFinderNG finder = new IdFinderNG();
        finder.setId( TEST );
        log.info( "finder : {}", finder );
        Assertions.assertEquals( TEST, finder.getId().longValue() );
    }

}
