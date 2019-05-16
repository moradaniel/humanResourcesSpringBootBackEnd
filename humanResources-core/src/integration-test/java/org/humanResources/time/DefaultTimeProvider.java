package org.humanResources.time;

import java.time.Instant;

public class DefaultTimeProvider implements TimeProvider {

    public DefaultTimeProvider() {
    }

    public long currentTimeMillis(){
        return System.currentTimeMillis();
    }

    public Instant newInstant(){
        return Instant.now();
    }
    
    public void setNowInstant(Instant now) {
    	throw new UnsupportedOperationException();
    }

}
