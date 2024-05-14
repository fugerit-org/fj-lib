package org.fugerit.java.core.util.checkpoint;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SimpleCheckpoint {

    @Getter
    private long baseTime;

    public SimpleCheckpoint() {
        this( System.currentTimeMillis() );
    }

    public SimpleCheckpoint(long baseTime) {
        this.baseTime = baseTime;
    }

    public long getDiffMillis() {
        return System.currentTimeMillis() - this.getBaseTime();
    }

    public String getFormatTimeDiffMillis() {
        return CheckpointUtils.formatTimeDiffMillis( this.getBaseTime(), System.currentTimeMillis() );
    }

}
