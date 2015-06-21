package com.dr.iris.character;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.dr.iris.Objects.BulletFactory;
import com.dr.iris.action.ActionBase;
import com.dr.iris.event.*;
import com.dr.iris.log.Log;
import com.dr.iris.ui.LockOnTarget;
import com.dr.iris.ui.UIObjectsManager;

/**
 * Created by Rayer on 4/26/15.
 */
public class SimpleEnemyActor extends GameActor {

    Log log = Log.getLogger(this.getClass());
    EventProxy eventProxy = new EventSelfParseProxy(this);

    LockOnTarget uiTarget;
    // notify user
    BitmapFont bulletColldownNotify;
    BitmapFont moveCooldownNotify;

    Integer currentMove = 0;
    Integer currentBullet = 0;

    public SimpleEnemyActor(String characterName) {
        super(characterName);
        setPosition(200, 200);

        uiTarget = new LockOnTarget(this, false);
        UIObjectsManager.getInst().addUIObject(uiTarget);

        bulletColldownNotify = new BitmapFont();
        bulletColldownNotify.setColor(Color.RED);
        bulletColldownNotify.setScale(0.8f);

        moveCooldownNotify = new BitmapFont();
        moveCooldownNotify.setColor(Color.LIGHT_GRAY);
        moveCooldownNotify.setScale(0.8f);

    }

    public ActionBase getCurrentBullet() {
        return getActorSpec().getBulletActions().get(currentBullet);
    }

    public int getBulletNum() {
        return getActorSpec().getBulletActions().size();
    }

    public Integer getCurrentBulletCooldown() {
        return (int)getCurrentBullet().actionCooldown;
    }

    private void bulletAction(float delta) {

        if(getCurrentBullet() != null && getCurrentBullet().action(this, delta)) {
            ++currentBullet;
            if(currentBullet >= getBulletNum()) {
                currentBullet = 0;
            }
        }
    }

    public ActionBase getCurrentMove() {
        return getActorSpec().getMoveActions().get(currentMove);
    }

    public int getMoveNum() {
        return getActorSpec().getMoveActions().size();
    }

    public Integer getCurrentMoveCooldown() {
        return (int)getCurrentMove().actionCooldown;
    }

    private void moveAction(float delta) {

        if(getCurrentMove() != null && getCurrentMove().action(this, delta)) {
            ++currentMove;
            if(currentMove >= getMoveNum()) {
                currentMove = 0;
            }
        }
    }

    public void getClicked() {
        uiTarget.setSpinning(true);
        EventNexus.getInst().sendEvent(eventProxy, EventFactory.createEventByPrototype(EventPrototypes.NOTIFY_UNCLICK));
        //EventNexus.getInst().sendEvent(eventProxy, EventFactory.createEventByPrototype("TEST_MULTIPARAM_EVENT", 12, "Test", 11.4f));
    }

    /**
     * Event
     */
    // --------------------------------------------------------------
    @SuppressWarnings("unused")
    @EventHandler("NOTIFY_UNCLICK")
    public void handle_unclick() {
        uiTarget.setSpinning(false);
    }

    @SuppressWarnings("unused")
    @EventHandler("NOTIFY_FIRE_BOMB")
    public void handle_fire_bomb(@EventParameter("target_x")Float x, @EventParameter("target_y")Float y) {
        log.debug("Fire exp_bomb!");
        new BulletFactory.ExpEnemyBombBuilder(getX(), getY(), x, y).setFrom(this).createBullet();
    }

    /**
     * Override
     */
    // --------------------------------------------------------------
    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        // draw notify
        bulletColldownNotify.draw(batch, getCurrentBulletCooldown().toString(), getX()+getWidth(), getY()+5);
        moveCooldownNotify.draw(batch, getCurrentMoveCooldown().toString(), getX() + getWidth(), getY() + getHeight());
    }

    @Override
    public Faction getFaction() {
        return Faction.ENEMY;
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        bulletAction(delta);
        moveAction(delta);
    }
}
