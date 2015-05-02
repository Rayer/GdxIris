package com.dr.iris.character;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.dr.iris.Objects.BulletGroupSpec;
import com.dr.iris.Objects.ObjectManager;
import com.dr.iris.Objects.SweepingBulletGroup;
import com.dr.iris.ui.LockOnTarget;
import com.dr.iris.ui.UIObjectsManager;

import java.util.Random;

/**
 * Created by Rayer on 4/26/15.
 */
public class SimpleEnemyActor extends GameActor {

    static final float BULLET_PERIOD = 6.0f;
    static final float MOVE_PERIOD = 4.0f;
    float bulletCooldown = 0;
    float moveCooldown = MOVE_PERIOD;

    LockOnTarget uiTarget;

    // notify user
    BitmapFont bulletColldownNotify;
    BitmapFont moveCooldownNotify;

    public SimpleEnemyActor(String characterName) {
        super(characterName);
        setPosition(200, 200);

        Random random = new Random();
        bulletCooldown = random.nextInt(5);
        moveCooldown = random.nextInt(5);

        uiTarget = new LockOnTarget(this, false);
        UIObjectsManager.getInst().addUIObject(uiTarget);

        bulletColldownNotify = new BitmapFont();
        bulletColldownNotify.setColor(Color.RED);
        bulletColldownNotify.setScale(0.8f);

        moveCooldownNotify = new BitmapFont();
        moveCooldownNotify.setColor(Color.LIGHT_GRAY);
        moveCooldownNotify.setScale(0.8f);
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

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        // draw notify
        Integer bulletCD = (int)bulletCooldown;
        Integer moveCD = (int)moveCooldown;
        bulletColldownNotify.draw(batch, bulletCD.toString(), getX()+getWidth(), getY()+5);
        moveCooldownNotify.draw(batch, moveCD.toString(), getX()+getWidth(), getY() + getHeight());
    }

    private void fireBullet() {

        Random r = new Random();

        BulletGroupSpec spec = new SweepingBulletGroup(this, r.nextInt(360), 180.0f, 4.0f);
        ObjectManager.getInst().createBulletGroup(spec);
    }

    public void getUnclicked() {
        uiTarget.setSpinning(false);
    }

    public void getClicked() {
        //notify other actors stop spinning
        for (GameActor sea : ObjectManager.getInst().getActorList()) {
            if (sea.getFaction() == Faction.NON_ENEMY) continue;
            if (sea == this) continue;

            ((SimpleEnemyActor) sea).getUnclicked();
        }
        uiTarget.setSpinning(true);
    }

}
