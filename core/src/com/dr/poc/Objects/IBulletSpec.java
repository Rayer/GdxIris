package com.dr.poc.Objects;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by Rayer on 4/21/15.
 */
public interface IBulletSpec {
    long getMaxTTL();
    Vector2 getDirection();
    Vector2 getStart();
    float getVelocity();
}
