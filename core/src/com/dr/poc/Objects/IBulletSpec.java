package com.dr.poc.Objects;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by Rayer on 4/21/15.
 * This Bullet Spec defines movement of Bullet
 */
public interface IBulletSpec {
    boolean isAlive();

    boolean update(float delta);

    Vector2 getCurPos();
}
