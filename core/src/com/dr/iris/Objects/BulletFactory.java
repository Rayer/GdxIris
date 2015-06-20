package com.dr.iris.Objects;

import com.badlogic.gdx.math.Vector2;
import com.dr.iris.character.GameActor;

/**
 * Created by rayer on 2015/4/27.
 */
public class BulletFactory {


    public static class LinearBulletBuilder {

        ObjectManager objMgr = ObjectManager.getInst();

        LinearBulletSpec spec;
        Vector2 dir = new Vector2(1, 0).nor();

        public LinearBulletBuilder(float x, float y) {
            spec = new LinearBulletSpec(new Vector2(x, y), dir, 80.0f, 12.0f);
        }

        public LinearBulletBuilder setDirAndSpeed(Vector2 dir, float speed) {
            spec.setDirectionAndSpeed(dir, speed);
            return this;
        }

        public LinearBulletBuilder setTTL(float ttl) {
            spec.setTtl(ttl);
            return this;
        }

        public LinearBulletBuilder setFrom(GameActor from) {
            spec.setFrom(from);
            return this;
        }

        public void createBullet() {
            objMgr.createBulletObject(spec);
        }

    }

    public static class ExpEnemyBombBuilder {

        ObjectManager objMgr = ObjectManager.getInst();

        ExpEnemyBombSpec spec;
        Vector2 dir = new Vector2(1, 0).nor();

        public ExpEnemyBombBuilder(float x, float y) {
            spec = new ExpEnemyBombSpec(new Vector2(x, y), dir, 80.0f, 12.0f);
        }

        public ExpEnemyBombBuilder setDirAndSpeed(Vector2 dir, float speed) {
            spec.setDirectionAndSpeed(dir, speed);
            return this;
        }

        public ExpEnemyBombBuilder setTTL(float ttl) {
            spec.setTtl(ttl);
            return this;
        }

        public ExpEnemyBombBuilder setFrom(GameActor from) {
            spec.setFrom(from);
            return this;
        }

        public void createBullet() {
            objMgr.createBulletObject(spec);
        }

    }

    public static class TracingBulletBuilder {
        TracingBulletSpec spec;

        public TracingBulletBuilder(GameActor from, GameActor to) {
            spec = new TracingBulletSpec(from.getCenterX(), from.getCenterY(), to, 220, 12.0f);
            spec.setFrom(from);
        }

        public TracingBulletBuilder setSpeed(float speed) {
            spec.setSpeed(speed);
            return this;
        }

        public TracingBulletBuilder setTTL(float ttl) {
            spec.setTtl(ttl);
            return this;
        }

        public void createBullet() {
            ObjectManager.getInst().createBulletObject(spec);
        }
    }

    public static class CircularBulletBuilder {

    }

}
