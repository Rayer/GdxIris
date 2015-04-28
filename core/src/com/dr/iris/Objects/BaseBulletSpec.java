package com.dr.iris.Objects;

/**
 * Created by Rayer on 4/28/15.
 */
public abstract class BaseBulletSpec implements BulletSpec {

    float ttl;
    boolean isAlive;

    @Override
    public boolean isAlive() {
        return ttl > 0;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public float getTtl() {
        return ttl;
    }

    public void setTtl(float ttl) {
        this.ttl = ttl;
    }




}
