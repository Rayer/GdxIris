package com.dr.iris.Objects;

import com.badlogic.gdx.math.Vector2;
import com.dr.iris.character.GameActor;

/**
 * Created by Rayer on 4/22/15.
 * This file describes LinearBulletSpec, which will cause bullet go stright
 */
public class LinearBulletSpec extends BaseBulletSpec {



    Vector2 velocity;

    GameActor from;

    public LinearBulletSpec(Vector2 start, Vector2 direction, float speed, float ttl)
    {
        super(start.x, start.y);
        direction = direction.nor();
        direction.setLength(speed);
        setSpec(start, direction, ttl);
    }

    private void setSpec(Vector2 start, Vector2 velocity, float ttl) {
        this.velocity = new Vector2(velocity);
        this.ttl = ttl;
        isAlive = true;
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
        Vector2 pos = new Vector2(getX(), getY()).add(speedX, speedY);
        setXY(pos.x, pos.y);

        return true;
    }


    public Vector2 getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector2 velocity) {
        this.velocity = velocity;
    }


    public void setAlive(boolean isAlive) {
        this.isAlive = isAlive;
    }

    public void setDirectionAndSpeed(Vector2 dir, float speed) {
        this.velocity = dir.setLength(speed);
    }


    @Override
    public GameActor getFrom() {
        return from;
    }

    @Override
    public void setFrom(GameActor object) {
        from = object;
    }

    @Override
    public String toString() {
        return "LinearBulletSpec{" +
                ", velocity=" + velocity +
                ", ttl=" + ttl +
                ", isAlive=" + isAlive +
                '}';
    }
}
