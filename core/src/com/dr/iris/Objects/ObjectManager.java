package com.dr.iris.Objects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.dr.iris.character.GameActor;
import com.dr.iris.character.SimpleEnemyActor;
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

    List<GameActor> gameActorList = new ArrayList<>();


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


    public boolean createBulletObject(BulletSpec spec) {
        Bullet b = getBullet();
        b.setSpec(spec);
        bulletList.add(b);
        return true;
    }

    public boolean createExpBulletGroup(Vector2 pos, float speed, int count, Object from) {

        for (int i = 0; i < count; ++i) {
            Vector2 direction = new Vector2(1, 1);
            direction.setAngle(360.0f / count * i);
            LinearBulletSpec mb_spec = new LinearBulletSpec(pos, direction, speed, 6.0f);
            mb_spec.setFrom(from);
            createBulletObject(mb_spec);
        }

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

        for(GameActor a : gameActorList) {
            a.act(delta);
        }
        return true;
    }

    public void render(Batch batch) {
        for(Bullet b : bulletList) {
            b.draw(batch);
        }

        for(GameActor a : gameActorList) {
            a.draw(batch, 1);
        }
    }

    public GameActor createActor(String actorAtlas) {
        GameActor actor = new GameActor(actorAtlas);
        gameActorList.add(actor);
        return actor;
    }

    public GameActor createEnemyActor(String actorAtlas) {
        GameActor actor = new SimpleEnemyActor(actorAtlas);
        gameActorList.add(actor);
        return actor;
    }

    public List<GameActor> getActorList() {
        return gameActorList;
    }
}
