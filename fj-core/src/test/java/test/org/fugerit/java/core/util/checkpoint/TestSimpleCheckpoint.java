package test.org.fugerit.java.core.util.checkpoint;

import lombok.extern.slf4j.Slf4j;
import org.fugerit.java.core.util.checkpoint.SimpleCheckpoint;
import org.junit.jupiter.api.Test;

@Slf4j
class TestSimpleCheckpoint {

    @Test
    void testCheckpoint() {
        SimpleCheckpoint checkpoint = new SimpleCheckpoint();
        log.info( "testCheckpoint 1 {}ms", checkpoint.getDiffMillis() );
        log.info( "testCheckpoint 2 {}ms", checkpoint.getFormatTimeDiffMillis() );
    }

}
