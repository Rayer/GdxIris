package com.dr.poc.Objects;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by Rayer on 4/22/15.
 */
public class LinearBulletSpec implements IBulletSpec {

    Vector2 start;
    Vector2 direction;


    public LinearBulletSpec(Vector2 start, Vector2 direction, float velocity) {
        this.start = start;
        this.direction = direction;
    }

    @Override
    public long getMaxTTL() {
        return 5;
    }

    @Override
    public Vector2 getDirection() {
        return direction;
    }

    @Override
    public Vector2 getStart() {
        return start;
    }


    @Override
    public float getVelocity() {
        return 1;
    }
}
