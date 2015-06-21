package com.dr.iris.Objects;

import com.badlogic.gdx.math.Vector2;
import com.dr.iris.character.GameActor;

/**
 * Created by Rayer on 4/25/15.
 */
public class CircularBulletSpec extends BaseBulletSpec {

    float ttl;
    Vector2 velocity;
    float angelPerSec = 180;
    float currentAngel = 0;
    float circularSize = 5;

    GameActor from;
    boolean isAlive = true;


    public CircularBulletSpec(Vector2 pos, Vector2 velocity, float angelPerSec, float circularSize, float ttl) {
        super(pos.x, pos.y);
        setSpec(velocity, angelPerSec, circularSize, ttl);
    }



    public CircularBulletSpec(Vector2 pos, Vector2 direction, float speed, float angelPerSec, float circularSize, float ttl) {
        super(pos.x, pos.y);
        direction = direction.nor();
        direction.setLength(speed);
        setSpec(direction, angelPerSec, circularSize, ttl);
    }



    private void setSpec(Vector2 velocity, float angelPerSec, float circularSize, float ttl) {
        this.ttl = ttl;
        this.velocity = new Vector2(velocity);
        this.angelPerSec = angelPerSec;
        this.circularSize = circularSize;
    }



    @Override
    public boolean update(float delta) {

        if(isAlive == false) return false;

        ttl -= delta;
        if (ttl < 0) {
            return false;
        }

        float speedX = velocity.x * delta;
        float speedY = velocity.y * delta;
        Vector2 pos = new Vector2(getX(), getY()).add(speedX, speedY);

        Vector2 unit = new Vector2(1, 1).nor();
        unit.setAngle(currentAngel += angelPerSec * delta);

        //logger.debug_s("Pos before modified : " + pos);
        pos = pos.add(unit.x * circularSize, unit.y * circularSize);
        //logger.debug_s("Pos after modified : " + pos);
        setXY(pos.x, pos.y);
        return true;
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
    public void setAlive(boolean b) {
        isAlive = b;
    }

}
