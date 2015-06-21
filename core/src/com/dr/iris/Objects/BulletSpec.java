package com.dr.iris.Objects;

import com.dr.iris.character.GameActor;

/**
 * Created by Rayer on 4/21/15.
 * This Bullet Spec defines movement of Bullet
 */
public interface BulletSpec {


    boolean update(float delta);

    GameActor getFrom();

    void setFrom(GameActor object);

    void setAlive(boolean b);

    float getX();

    float getY();

    void setXY(float x, float y);
}
