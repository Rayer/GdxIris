package com.dr.iris.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.dr.iris.character.GameActor;

/**
 * Created by Rayer on 5/2/15.
 */
public class LockOnTarget implements UIObject {
    static Texture lock_on_inner_texture;
    static Texture lock_on_outer_texture;

    static {
        Pixmap inner = new Pixmap(40, 40, Pixmap.Format.RGBA8888);
        inner.setColor(Color.RED);
        inner.drawCircle(20, 20, 20);
        inner.drawLine(10, 0, 10, 20);
        inner.drawLine(0, 10, 20, 10);

        Pixmap outer = new Pixmap(60, 60, Pixmap.Format.RGBA8888);
        outer.setColor(Color.GREEN);
        outer.drawCircle(30, 30, 30);
        outer.drawLine(30, 0, 30, 60);
        outer.drawLine(0, 30, 60, 30);

        lock_on_inner_texture = new Texture(inner);
        lock_on_outer_texture = new Texture(outer);
    }

    Sprite lock_on_inner_sprite;
    Sprite lock_on_outer_sprite;

    float x;
    float y;
    float angle = 0f;
    float angle_offset_per_sec = 360.0f;

    GameActor attachedActor;
    boolean spinning;

    public LockOnTarget(GameActor attachedActor, boolean spinning) {
        lock_on_inner_sprite = new Sprite(lock_on_inner_texture, 40, 40);
        lock_on_outer_sprite = new Sprite(lock_on_outer_texture, 60, 60);
        lock_on_inner_sprite.setOrigin(20, 20);
        lock_on_outer_sprite.setOrigin(30, 30);

        this.attachedActor = attachedActor;
    }

    public void setSpinning(boolean spinning) {
        this.spinning = spinning;
    }

    @Override
    public boolean update(float delta) {

        x = attachedActor.getCenterX();
        y = attachedActor.getCenterY();

        lock_on_inner_sprite.setPosition(x - 20, y - 20);
        lock_on_outer_sprite.setPosition(x - 30, y - 30);

        if (spinning) {
            angle += angle_offset_per_sec * delta;
        } else {
            angle = 0;
        }

        lock_on_inner_sprite.setRotation(angle);
        lock_on_outer_sprite.setRotation(-angle);

        return true;
    }

    @Override
    public void draw(Batch batch) {
        lock_on_inner_sprite.draw(batch);
        lock_on_outer_sprite.draw(batch);
    }

}
