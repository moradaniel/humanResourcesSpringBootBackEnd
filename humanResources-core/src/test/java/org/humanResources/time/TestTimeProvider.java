package org.humanResources.time;

import java.time.Instant;

public class TestTimeProvider implements TimeProvider {

    Instant now;

    public TestTimeProvider(Instant now) {
        this.now = now;
    }

    public long currentTimeMillis(){
        return now.toEpochMilli();
    }

    public Instant newInstant(){
        return now;
    }
    
    public void setNowInstant(Instant now) {
    	this.now = now;
    }

}
