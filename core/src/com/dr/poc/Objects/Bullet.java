package com.dr.poc.Objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Disposable;

/**
 * Created by Rayer on 4/20/15.
 */
public class Bullet implements Disposable {

    Sprite bulletSpirte;
    //static Pixmap pixmap;
    static Texture dynTexture;


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

    boolean update(long timer) {
        return true;
    }

    @Override
    public void dispose() {

    }
}
