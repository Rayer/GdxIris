package com.dr.iris.Objects;

import com.badlogic.gdx.math.Vector2;
import com.dr.iris.character.GameActor;

import java.util.Random;

/**
 * Created by Rayer on 4/30/15.
 */
public class SweepingBulletGroup implements BulletGroupSpec {

    static final float FIRE_INTERVAL = 0.125f;
    static final float SWEEP_ANGEL = 30.0f;
    static final float FIRE_DURATION = 6.0f;

    float nextBulletCooldown = 0;
    float initAngle = 0;
    float currentAngle = 0;
    float fireRemaining = FIRE_DURATION;
    float bulletSpeed;

    GameActor firer;

    public SweepingBulletGroup(GameActor firer, float angle, float speed, float duration) {
        this.firer = firer;
        initAngle = angle;
        currentAngle = angle;
        bulletSpeed = speed;
        fireRemaining = duration;
    }

    @Override
    public boolean update(float delta) {
        fireRemaining -= delta;
        nextBulletCooldown -= delta;
        if (fireRemaining < 0) return false;
        if (nextBulletCooldown > 0) return true;

        nextBulletCooldown = FIRE_INTERVAL;

        Random r = new Random();
        int offset = r.nextInt(30);
        currentAngle = r.nextBoolean() ? initAngle - offset : initAngle + offset;

        Vector2 bulletVector = new Vector2(1, 1).nor().setAngle(currentAngle);

        new BulletFactory.LinearBulletBuilder(firer.getCenterX(), firer.getCenterY()).setDirAndSpeed(bulletVector, bulletSpeed).setFrom(firer).createBullet();

        return true;
    }
}
