package com.dr.iris.character;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.dr.iris.Objects.ObjectManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Random;

/**
 * Created by Rayer on 4/26/15.
 */
public class SimpleEnemyActor extends GameActor {

    static final float BULLET_PERIOD = 3.0f;
    static final float MOVE_PERIOD = 4.0f;
    float bulletCooldown = BULLET_PERIOD;
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
        //logger.info("Fire!");
        ObjectManager.getInst().createExpBulletGroup(new Vector2(getX(), getY()), 180.0f, 12, this);
    }


}
