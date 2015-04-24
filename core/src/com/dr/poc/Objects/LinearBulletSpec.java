package com.dr.poc.Objects;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by Rayer on 4/22/15.
 * This file describes LinearBulletSpec, which will cause bullet go stright
 */
public class LinearBulletSpec implements BulletSpec {

    Vector2 pos;
    Vector2 velocity;
    float ttl;

    boolean isAlive;


    public LinearBulletSpec(Vector2 start, Vector2 direction, float speed, float ttl) {
        direction = direction.nor();
        direction.setLength(speed);
        setSpec(start, direction, ttl);
    }

    private void setSpec(Vector2 start, Vector2 velocity, float ttl) {
        this.pos = new Vector2(start); //if we don't new one, it will share multiple in Bullet Group and cause trouble
        this.velocity = new Vector2(velocity);
        this.ttl = ttl;
        isAlive = true;
    }

    @Override
    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    @Override
    public boolean update(float delta) {

        if (!isAlive) return false;

        ttl -= delta;
        if (ttl < 0) {
            setAlive(false);
            return false;
        }

        float speedX = velocity.x * delta;
        float speedY = velocity.y * delta;
        pos = pos.add(speedX, speedY);

        return true;
    }

    @Override
    public Vector2 getCurPos() {
        return pos;
    }

    @Override
    public String toString() {
        return "LinearBulletSpec{" +
                "pos=" + pos +
                ", velocity=" + velocity +
                ", ttl=" + ttl +
                ", isAlive=" + isAlive +
                '}';
    }
}
