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

    static private ObjectManager inst;
    Logger logger = LogManager.getLogger(ObjectManager.class);
    List<Bullet> bulletList = new ArrayList<>();
    List<Bullet> recycleList = new ArrayList<>();

    private ObjectManager() {

    }

    static public ObjectManager getInst() {
        if(inst == null)
            inst = new ObjectManager();

        return inst;
    }

    private Bullet getBullet() {
        return recycleList.isEmpty() ? new Bullet() : recycleList.remove(recycleList.size() - 1);
    }


    public boolean createBulletObject(IBulletSpec spec) {
        Bullet b = getBullet();

        b.setInitPosition(spec.getStart());
        b.setDirection(spec.getDirection());
        b.setTtl(spec.getMaxTTL());

        bulletList.add(b);
        logger.info("Bullet added, in list : " + bulletList.size() + " and in recycle list : " + recycleList.size());
        return true;
    }

    public boolean update(float delta) {
        for (int i = 0; i < bulletList.size(); ++i) {
            Bullet b = bulletList.get(i);
            if (b.update(delta) == false) {
                bulletList.remove(i);
                recycleList.add(b);
            }
        }
        return true;
    }

    public void render(Batch batch) {
        for(Bullet b : bulletList) {
            b.draw(batch);
        }
    }

}
