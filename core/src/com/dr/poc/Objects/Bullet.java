package com.dr.poc.Objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;

/**
 * Created by Rayer on 4/20/15.
 */
public class Bullet implements Disposable {

    static Texture dynTexture;
    Sprite bulletSpirte;
    Vector2 direction;
    float speed = 80.0f;
    float ttl = 5.0f; //max life time is 5 sec

    BulletSpec spec;

    public Bullet() {
        if(dynTexture == null) {
            //create pixmap
            Pixmap pixmap = new Pixmap(3, 3, Pixmap.Format.RGBA8888);
            pixmap.setColor(Color.YELLOW);
            pixmap.fill();
            dynTexture = new Texture(pixmap);
        }

        bulletSpirte = new Sprite(dynTexture);
    }

    public void setSpec(BulletSpec spec) {
        this.spec = spec;
    }



    boolean update(float delta) {


        //TODO: Use primitive type like float x, float y, to avoid rapidly new object.
        if (spec.update(delta) == false) return false;

        bulletSpirte.setPosition(spec.getCurPos().x, spec.getCurPos().y);
        return true;
    }

    void draw(Batch batch) {
        bulletSpirte.draw(batch);
    }

    @Override
    public void dispose() {

    }
}
