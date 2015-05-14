package com.dr.iris.effect;

import com.badlogic.gdx.graphics.g2d.Batch;

/**
 * Created by Rayer on 5/14/15.
 */
public interface AlphaEffect {
    boolean update(float delta);

    void draw(Batch batch);

}
