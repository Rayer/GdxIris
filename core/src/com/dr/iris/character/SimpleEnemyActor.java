package com.dr.iris.character;

import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.dr.iris.Objects.BulletGroupSpec;
import com.dr.iris.Objects.ObjectManager;
import com.dr.iris.Objects.SweepingBulletGroup;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Random;

/**
 * Created by Rayer on 4/26/15.
 */
public class SimpleEnemyActor extends GameActor {

    static final float BULLET_PERIOD = 6.0f;
    static final float MOVE_PERIOD = 4.0f;
    float bulletCooldown = 0;
    float moveCooldown = MOVE_PERIOD;
    Logger logger = LogManager.getLogger(this.getClass());

    public SimpleEnemyActor(String characterName) {
        super(characterName);
        setPosition(200, 200);

        Random random = new Random();
        bulletCooldown = random.nextInt(5);
        moveCooldown = random.nextInt(5);
    }

    @Override
    public Faction getFaction() {
        return Faction.ENEMY;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        bulletCooldown -= delta;
        if(bulletCooldown < 0) {
            bulletCooldown = BULLET_PERIOD;
            fireBullet();
        }

        moveCooldown -= delta;
        if (moveCooldown < 0) {
            moveCooldown = MOVE_PERIOD;

            Random random = new Random();
            addAction(Actions.moveTo(random.nextInt(400), random.nextInt(400), MOVE_PERIOD));
        }
    }


    private void fireBullet() {

        Random r = new Random();

        BulletGroupSpec spec = new SweepingBulletGroup(this, r.nextInt(360), 180.0f, 4.0f);
        ObjectManager.getInst().createBulletGroup(spec);
    }


}
