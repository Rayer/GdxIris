package com.dr.iris.character;

import com.badlogic.gdx.math.Vector2;
import com.dr.iris.Objects.BulletFactory;
import com.dr.iris.Objects.ObjectManager;

/**
 * Created by Rayer on 4/28/15.
 */
public class MainActor extends GameActor {

    static final float DEF_BULLET_COOLDOWN = 0.125f; //8 shoots per second
    float bullet_cooldown = DEF_BULLET_COOLDOWN;
    boolean bullet_fire_in_cd = false;

    GameActor currentTarget;

    public MainActor(String characterName) {
        super(characterName);
    }

    @Override
    public Faction getFaction() {
        return Faction.NON_ENEMY;
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        bullet_cooldown -= delta;
        if(bullet_cooldown < 0) {
            bullet_fire_in_cd = false;
            bullet_cooldown = DEF_BULLET_COOLDOWN;
        }

        if(getFaction() == Faction.NON_ENEMY) {

            if(bullet_fire_in_cd) return;
            bullet_fire_in_cd = true;

            for(GameActor a : ObjectManager.getInst().getActorList()) {
                if(a == this || a.getFaction() == Faction.NON_ENEMY) continue;

                Vector2 deltaV = new Vector2(a.getCenterX() - getCenterX(), a.getCenterY() - getCenterY());
                if(deltaV.len2() < 22500)
                    new BulletFactory.TracingBulletBuilder(this, a).createBullet();
            }

            if (currentTarget != null) {
                new BulletFactory.TracingBulletBuilder(this, currentTarget).createBullet();
            }
        }

    }

    public void setShootingTo(GameActor actor) {
        if (actor.getFaction() == getFaction()) return;
        currentTarget = actor;
    }

}
