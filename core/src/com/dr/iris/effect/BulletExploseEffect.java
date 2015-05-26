package com.dr.iris.effect;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.dr.iris.utils.FileUtils;

/**
 * Created by Rayer on 5/14/15.
 */
public class BulletExploseEffect implements AlphaEffect {

    static final float MAX_ALPHA = 0.4f;
    static Texture effectTexture;
    float currentAlpha = MAX_ALPHA;
    float x;
    float y;
    float duration;
    float alphaDecreasePerSec;
    Sprite sprite;

    float scale = 1.0f;

    public BulletExploseEffect(float x, float y, float duration) {

        if (effectTexture == null) {
            effectTexture = new Texture(FileUtils.getRes("effect2.png"));
        }

        sprite = new Sprite(effectTexture);
        sprite.setSize(16, 16);
        sprite.setAlpha(currentAlpha);

        this.x = x;
        this.y = y;
        this.duration = duration;

        alphaDecreasePerSec = MAX_ALPHA / duration;

        sprite.setPosition(x, y);

        //sprite.setCenter(x+16, y+16);
        sprite.setOrigin(8, 8);

    }


    @Override
    public boolean update(float delta) {
        duration -= delta;
        currentAlpha -= alphaDecreasePerSec * delta;
        scale += 0.1f;
        sprite.setScale(scale);
        sprite.setAlpha(currentAlpha);
        return duration < 0 ? false : true;
    }

    @Override
    public void draw(Batch batch) {
        sprite.draw(batch);
    }
}
