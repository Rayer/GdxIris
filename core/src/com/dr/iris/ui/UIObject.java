package com.dr.iris.ui;

import com.badlogic.gdx.graphics.g2d.Batch;

/**
 * Created by Rayer on 5/2/15.
 */
public interface UIObject {
    boolean update(float delta);

    void draw(Batch batch);
}
