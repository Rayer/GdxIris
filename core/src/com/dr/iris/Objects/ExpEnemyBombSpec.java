package com.dr.iris.Objects;

import com.badlogic.gdx.math.Vector2;
import com.dr.iris.character.GameActor;

/**
 * Created by Rayer on 6/8/15.
 */
public class ExpEnemyBombSpec extends BaseBulletSpec {

    static final int SPREAD_CLUSTER_BULLET_COUNT = 36;
    boolean isClusterSpreaded = false;
    float destX;
    float destY;
    float speed;
    float ttl;
    GameActor from;

    public ExpEnemyBombSpec(Vector2 start, Vector2 destination, float speed, float ttl) {
        super(start.x, start.y);
        destX = destination.x;
        destY = destination.y;
        this.speed = speed;
        this.ttl = ttl;
    }

    @Override
    public boolean update(float delta) {

        if((ttl -= delta) < 0) {
            isAlive = false;
            return false;
        }

        Vector2 nowPos = new Vector2(getX(), getY());

        if((nowPos.dst2(destX, destY)) < 50) {
            isAlive = false;
            createClusters();
            return false;
        }

        Vector2 deltaVec = new Vector2(destX - getX(), destY - getY()).nor().scl(speed * delta);
        nowPos.add(deltaVec);
        setXY(nowPos.x, nowPos.y);
        return isAlive;
    }

    @Override
    public GameActor getFrom() {
        return null;
    }

    @Override
    public void setFrom(GameActor object) {

    }


    private void createClusters() {
        for(int i = 0; i < SPREAD_CLUSTER_BULLET_COUNT; ++i) {
            new BulletFactory.LinearBulletBuilder(getX(), getY()).setFrom(getFrom())
                    .setDirAndSpeed(new Vector2(0, 1).setAngle(i * 360 / SPREAD_CLUSTER_BULLET_COUNT), 125f)
                    .createBullet();
        }
    }


    public void setSpeed(float speed) {
        this.speed = speed;
    }

}