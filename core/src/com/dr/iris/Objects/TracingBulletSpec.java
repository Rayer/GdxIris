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
    //Vector2 currentPos;

    boolean isAlive = true;

    public TracingBulletSpec(float startX, float startY, GameActor targetActor, float speed, float ttl) {
        super(startX, startY);
        //currentPos = new Vector2(startX, startY);
        target = targetActor;
        this.speed = speed;
        this.ttl = ttl;
    }

    @Override
    public boolean update(float delta) {

        if(isAlive == false) return false;

        ttl -= delta;
        if (ttl < 0) {
            isAlive = false;
            return false;
        }

        float deltaX = getX() - target.getCenterX();
        float deltaY = getY() - target.getCenterY();

        Vector2 velocity = new Vector2(deltaX, deltaY).setLength(speed * delta);
        Vector2 currentPos = new Vector2(getX(), getY()).sub(velocity);
        setXY(currentPos.x, currentPos.y);
        //logger.debug_s("Pos : " + currentPos);
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

    public void setSpeed(float speed) {
        this.speed = speed;
    }
}
