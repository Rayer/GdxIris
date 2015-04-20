package com.dr.poc.Objects;

/**
 * Created by Rayer on 4/20/15.
 */
public class RangeBasedTTL implements TTL {

    long travelledRange;
    long thresholdDeadTimer;

    long maxTravelRange;
    long aliveTimer;

    public RangeBasedTTL() {

    }

    @Override
    public boolean isStillAlive() {
        return false;
    }

    @Override
    public long getCurrentAliveTimer() {
        return 0;
    }
}
