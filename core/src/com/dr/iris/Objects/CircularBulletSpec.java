package com.dr.iris.Objects;

import com.badlogic.gdx.math.Vector2;
import com.dr.iris.character.GameActor;

/**
 * Created by Rayer on 4/25/15.
 */
public class CircularBulletSpec implements BulletSpec {

    float ttl;
    Vector2 pos;
    Vector2 velocity;
    float angelPerSec = 180;
    float currentAngel = 0;
    float circularSize = 5;

    GameActor from;

    boolean isAlive = false;

    public CircularBulletSpec(Vector2 pos, Vector2 velocity, float angelPerSec, float circularSize, float ttl) {
        setSpec(pos, velocity, angelPerSec, circularSize, ttl);
    }



    public CircularBulletSpec(Vector2 pos, Vector2 direction, float speed, float angelPerSec, float circularSize, float ttl) {
        direction = direction.nor();
        direction.setLength(speed);
        setSpec(pos, direction, angelPerSec, circularSize, ttl);
    }



    private void setSpec(Vector2 pos, Vector2 velocity, float angelPerSec, float circularSize, float ttl) {
        this.ttl = ttl;
        this.pos = new Vector2(pos);
        this.velocity = new Vector2(velocity);
        this.angelPerSec = angelPerSec;
        this.circularSize = circularSize;
        isAlive = true;
    }

    @Override
    public boolean isAlive() {
        return isAlive;
    }

    @Override
    public boolean update(float delta) {

        if (!isAlive) return false;

        ttl -= delta;
        if (ttl < 0) {
            isAlive = false;
            return false;
        }

        float speedX = velocity.x * delta;
        float speedY = velocity.y * delta;
        pos = pos.add(speedX, speedY);

        Vector2 unit = new Vector2(1, 1).nor();
        unit.setAngle(currentAngel += angelPerSec * delta);

        //logger.debug("Pos before modified : " + pos);
        pos = pos.add(unit.x * circularSize, unit.y * circularSize);
        //logger.debug("Pos after modified : " + pos);
        return true;
    }

    @Override
    public Vector2 getCurPos() {
        return pos;
    }

    @Override
    public GameActor getFrom() {
        return from;
    }


    @Override
    public void setFrom(GameActor object) {
        from = object;
    }

}
