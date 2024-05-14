package test.org.fugerit.java.core.util.checkpoint;

import lombok.extern.slf4j.Slf4j;
import org.fugerit.java.core.util.checkpoint.SimpleCheckpoint;
import org.junit.Test;

@Slf4j
public class TestSimpleCheckpoint {

    @Test
    public void testCheckpoint() {
        SimpleCheckpoint checkpoint = new SimpleCheckpoint();
        log.info( "testCheckpoint 1 {}ms", checkpoint.getDiffMillis() );
        log.info( "testCheckpoint 2 {}ms", checkpoint.getFormatTimeDiffMillis() );
    }

}
