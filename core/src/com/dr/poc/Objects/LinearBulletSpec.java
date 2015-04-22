package com.dr.poc.Objects;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by Rayer on 4/22/15.
 */
public class LinearBulletSpec implements IBulletSpec {

    Vector2 start;
    Vector2 end;

    LinearBulletSpec(Vector2 start, Vector2 direction, float velocity) {

    }

    @Override
    public long getMaxTTL() {
        return 2000;
    }

    @Override
    public Vector2 getDirection() {
        return null;
    }

    @Override
    public Vector2 getStart() {
        return null;
    }


    @Override
    public float getVelocity() {
        return 1;
    }
}
