package com.dr.iris.Objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Disposable;
import com.dr.iris.character.GameActor;

/**
 * Created by Rayer on 4/20/15.
 */
public class Bullet implements Disposable {

    static Texture bulletTexture;
    Sprite bulletSpirte;
    float ttl = 5.0f; //max life time is 5 sec

    //TODO: might need a effect spec
    static Texture effectTexture;
    Sprite effectSpirte;
    float effectTime = 1.0f;
    boolean effectSwitch = false;

    BulletSpec spec;

    public Bullet() {
        if(bulletTexture == null) {
            //create pixmap
//            Pixmap pixmap = new Pixmap(3, 3, Pixmap.Format.RGBA8888);
//            pixmap.setColor(Color.YELLOW);
//            pixmap.fill();
//            bulletTexture = new Texture(pixmap);
            bulletTexture = new Texture("data/bullet.png");
            effectTexture = new Texture("data/effect.png");
        }

        bulletSpirte = new Sprite(bulletTexture);
        bulletSpirte.setSize(8, 8);

        effectSpirte = new Sprite(effectTexture);
        effectSpirte.setSize(16, 16);
        effectSpirte.setAlpha(0.5f);
    }

    public void setSpec(BulletSpec spec) {
        this.spec = spec;
    }



    boolean update(float delta) {

        //TODO: Use primitive type like float x, float y, to avoid rapidly new object.
        if (spec.update(delta) == false) return false;
        bulletSpirte.setPosition(spec.getCurPos().x, spec.getCurPos().y);

        //effect
        if(effectSwitch == true) {
            effectTime -= delta;
            if(effectTime <= 0.0f) {
                effectTime = 1.0f;
                effectSwitch = false;
            }
        }

        //hit test
        for(GameActor actor : ObjectManager.getInst().getActorList()) {

            //Bypass hit check if firer is self
            if(spec.getFrom() == actor) continue;

            //Bypass the same faction
            if (spec.getFrom() != null && spec.getFrom().getFaction() == actor.getFaction()) continue;

            if(actor.isHit(spec.getCurPos().x, spec.getCurPos().y)) {
                actor.getHit(spec);
                ttl = 0;

                //effect
                if(effectSwitch == false) {
                    effectSwitch = true;
                    effectSpirte.setPosition(spec.getCurPos().x, spec.getCurPos().y);
                }
                return false;
            }
        }

        return true;
    }

    void draw(Batch batch) {
        bulletSpirte.draw(batch);

        //effect
        if(effectSwitch == true) {
            effectSpirte.draw(batch);
        }
    }

    @Override
    public void dispose() {

    }
}
