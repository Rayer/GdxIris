package com.dr.iris.Objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Disposable;
import com.dr.iris.character.GameActor;
import com.dr.iris.effect.BulletExploseEffect;
import com.dr.iris.effect.EffectManager;
import com.dr.iris.utils.FileUtils;

/**
 * Created by Rayer on 4/20/15.
 */
public class Bullet implements Disposable {

    static Texture bulletTexture;
    Sprite bulletSpirte;
    float ttl = 5.0f; //max life time is 5 sec


    BulletSpec spec;

    public Bullet() {
        if(bulletTexture == null) {
            bulletTexture = new Texture(FileUtils.getRes("bullet.png"));
        }

        bulletSpirte = new Sprite(bulletTexture);
        bulletSpirte.setSize(8, 8);

    }

    public void setSpec(BulletSpec spec) {
        this.spec = spec;
    }

    boolean update(float delta) {

        //TODO: Use primitive type like float x, float y, to avoid rapidly new object.
        if (spec.update(delta) == false) return false;
        bulletSpirte.setPosition(spec.getCurPos().x, spec.getCurPos().y);


        //hit test
        for(GameActor actor : ObjectManager.getInst().getActorList()) {

            //Bypass hit check if firer is self
            if(spec.getFrom() == actor) continue;

            //Bypass the same faction
            if (spec.getFrom() != null && spec.getFrom().getFaction() == actor.getFaction()) continue;

            if(actor.isHit(spec.getCurPos().x, spec.getCurPos().y)) {
                actor.getHit(spec);
                ttl = 0;

                EffectManager.getInst().addEffect(new BulletExploseEffect(spec.getCurPos().x, spec.getCurPos().y, 1));
                return false;
            }
        }

        return true;
    }

    void draw(Batch batch) {
        bulletSpirte.draw(batch);

    }


    @Override
    public void dispose() {

    }
}
