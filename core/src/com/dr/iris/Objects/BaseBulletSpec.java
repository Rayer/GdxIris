package com.dr.iris.Objects;

/**
 * Created by Rayer on 4/28/15.
 */
public abstract class BaseBulletSpec implements BulletSpec {

    float ttl;
    boolean isAlive;

    float x;
    float y;

    @Override
    public float getX() {
        return x;
    }

    @Override
    public float getY() {
        return y;
    }

    @Override
    public void setXY(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public BaseBulletSpec(float x, float y) {
        this.x = x;
        this.y = y;
        isAlive = true;
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
