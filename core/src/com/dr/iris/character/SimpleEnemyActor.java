package com.dr.iris.character;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.dr.iris.Objects.ObjectManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by Rayer on 4/26/15.
 */
public class SimpleEnemyActor extends GameActor {

    static final float BULLET_PERIOD = 5.0f;
    float bulletCooldown = BULLET_PERIOD;
    Logger logger = LogManager.getLogger(this.getClass());

    public SimpleEnemyActor(String characterName) {
        super(characterName);
        setPosition(200, 200);
        addAction(Actions.moveTo(220, 270));
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        bulletCooldown -= delta;
        if(bulletCooldown < 0) {
            bulletCooldown = BULLET_PERIOD;
            fireBullet();
        }
    }

    private void fireBullet() {
        logger.info("Fire!");
        ObjectManager.getInst().createExpBulletGroup(new Vector2(getX(), getY()), 60.0f, 60, this);
    }


}
