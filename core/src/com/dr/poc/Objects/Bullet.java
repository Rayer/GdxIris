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

    void setInitPosition(Vector2 position) {
        bulletSpirte.setPosition(position.x, position.y);
    }

    void setDirection(Vector2 dir) {
        direction = dir.nor();
    }

    void setTtl(float ttl) {
        this.ttl = ttl;
    }

    void reinit() {

    }
    boolean update(float delta) {

        ttl -= delta;
        if (ttl < 0) return false;

        //TODO: Use primitive type like float x, float y, to avoid rapidly new object.
        Vector2 pos = new Vector2(bulletSpirte.getX(), bulletSpirte.getY());
        pos.add(direction.x * delta * speed, direction.y * delta * speed);
        bulletSpirte.setPosition(pos.x, pos.y);
        return true;
    }

    void draw(Batch batch) {
        bulletSpirte.draw(batch);
    }

    @Override
    public void dispose() {

    }
}
