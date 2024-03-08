package test.org.fugerit.java.core.db.dao.daogen;

import lombok.extern.slf4j.Slf4j;
import org.fugerit.java.core.db.daogen.IdFinderNG;
import org.junit.Assert;
import org.junit.Test;

@Slf4j
public class TestIdFinderNG {

    private static final long TEST = 1000L;

    @Test
    public void testIdFinderNg() {
        IdFinderNG finder = new IdFinderNG();
        finder.setId( TEST );
        log.info( "finder : {}", finder );
        Assert.assertEquals( TEST, finder.getId().longValue() );
    }

}
