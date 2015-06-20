package com.dr.iris.Objects;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by Rayer on 6/8/15.
 */
public class ExpEnemyBombSpec extends LinearBulletSpec {

    static final int SPREAD_CLUSTER_BULLET_COUNT = 36;
    boolean isClusterSpreaded = false;

    public ExpEnemyBombSpec(Vector2 start, Vector2 destination, float speed, float ttl) {
        super(start, new Vector2(destination.x - start.x, destination.y - start.y).nor(), speed, ttl);
    }

    @Override
    public boolean update(float delta) {
        boolean isAlive = super.update(delta);

        if(isAlive == false) {
            createClusters();
        }

        return isAlive;
    }

    private void createClusters() {
        for(int i = 0; i < SPREAD_CLUSTER_BULLET_COUNT; ++i) {
            new BulletFactory.LinearBulletBuilder(getX(), getY()).setFrom(getFrom())
                    .setDirAndSpeed(new Vector2(0, 1).setAngle(i * 360 / SPREAD_CLUSTER_BULLET_COUNT), 125f)
                    .createBullet();
        }
    }


}