package org.humanResources.time;

import java.time.Instant;

public interface TimeProvider {

    public long currentTimeMillis();
    public Instant newInstant();
    public void setNowInstant(Instant now);
}