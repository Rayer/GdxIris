package com.dr.iris.character;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.dr.iris.Objects.BulletGroupSpec;
import com.dr.iris.Objects.ObjectManager;
import com.dr.iris.Objects.SweepingBulletGroup;
import com.dr.iris.event.*;
import com.dr.iris.ui.LockOnTarget;
import com.dr.iris.ui.UIObjectsManager;

import java.util.Random;

/**
 * Created by Rayer on 4/26/15.
 */
public class SimpleEnemyActor extends GameActor {

    static final float BULLET_PERIOD = 6.0f;
    static final float MOVE_PERIOD = 4.0f;
    static final float MOVE_VELOCITY_PER_SEC = 80.0f;
    float bulletCooldown = 0;
    float moveCooldown = MOVE_PERIOD;
    LockOnTarget uiTarget;

    GridPoint2 actionSpec[] = {}; // should be a spec
    int currentStep = 0;
    int specNum = 0;

    // notify user
    BitmapFont bulletColldownNotify;
    BitmapFont moveCooldownNotify;

    EventProxy eventProxy = new EventProxy() {
        @Override
        public void handleEvent(EventProxy sender, EventInstance eventInstance) {
            if (sender == eventProxy) return;
            if (eventInstance.getPrototype() == EventPrototypes.NOTIFY_UNCLICK) {
                SimpleEnemyActor.this.getUnclicked();
            }
        }
    };

    public SimpleEnemyActor(String characterName) {
        super(characterName);
        setPosition(200, 200);

        EventNexus.getInst().registerEvent(eventProxy, EventPrototypes.NOTIFY_UNCLICK, EventPrototypes.NOTIFY_COLLIDE);
        //Random random = new Random();
        //bulletCooldown = random.nextInt(5);
        //moveCooldown = random.nextInt(5);
        bulletCooldown = 3;
        moveCooldown = 5;

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

            //Random random = new Random();
            //float moveToX = random.nextInt(600);
            //float moveToY = random.nextInt(600);

            if(actionSpec.length > 0) {
                GridPoint2 nextStep = actionSpec[currentStep];
                float moveToX = nextStep.x;
                float moveToY = nextStep.y;

                float distance = new Vector2(getX() - moveToX, getY() - moveToY).len();
                addAction(Actions.moveTo(moveToX, moveToY, distance / MOVE_VELOCITY_PER_SEC));
            }

            ++currentStep;
            if(currentStep >= specNum) {
                currentStep = 0;
            }

        }
    }

    public void setActionSpec(GridPoint2[] actionSpec) {
        this.actionSpec = actionSpec;
        specNum = actionSpec.length;
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
        uiTarget.setSpinning(true);
        EventNexus.getInst().sendEvent(eventProxy, EventFactory.createEventByPrototype(EventPrototypes.NOTIFY_UNCLICK));
    }

}
