package com.dr.iris.Objects;

import com.badlogic.gdx.math.Vector2;
import com.dr.iris.character.GameActor;

/**
 * Created by Rayer on 4/28/15.
 */
public class TracingBulletSpec extends BaseBulletSpec {


    GameActor target;
    float speed;
    GameActor from;
    Vector2 currentPos;

    public TracingBulletSpec(float startX, float startY, GameActor targetActor, float speed, float ttl) {
        currentPos = new Vector2(startX, startY);
        target = targetActor;
        this.speed = speed;
        this.ttl = ttl;
    }

    @Override
    public boolean update(float delta) {

        ttl -= delta;
        if (ttl < 0) {
            isAlive = false;
            return false;
        }

        float deltaX = currentPos.x - target.getCenterX();
        float deltaY = currentPos.y - target.getCenterY();

        Vector2 velocity = new Vector2(deltaX, deltaY).setLength(speed * delta);
        currentPos = currentPos.sub(velocity);
        //logger.debug_s("Pos : " + currentPos);
        return true;
    }

    @Override
    public Vector2 getCurPos() {
        return currentPos;
    }

    @Override
    public GameActor getFrom() {
        return from;
    }

    @Override
    public void setFrom(GameActor object) {
        from = object;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }
}
