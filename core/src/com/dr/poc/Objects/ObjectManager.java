package com.dr.poc.Objects;

import com.badlogic.gdx.graphics.g2d.Batch;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rayer on 4/20/15.
 */
public class ObjectManager {

    Logger logger = LogManager.getLogger(ObjectManager.class);

    private ObjectManager() {

    }

    static private ObjectManager inst;
    static public ObjectManager getInst() {
        if(inst == null)
            inst = new ObjectManager();

        return inst;
    }

    List<Bullet> bulletList = new ArrayList<>();


    public boolean createBulletObject(IBulletSpec spec) {
        Bullet b = new Bullet();

        b.setInitPosition(spec.getStart());
        b.setDirection(spec.getDirection());

        bulletList.add(b);
        logger.info("Bullet added, in list : " + bulletList.size());
        return true;
    }

    public boolean update(float delta) {
        for(Bullet b : bulletList) {
            b.update(delta);
        }
        return true;
    }

    public void draw(Batch batch) {
        for(Bullet b : bulletList) {
            b.draw(batch);
        }
    }

}
