package com.dr.iris.Objects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.dr.iris.character.GameActor;
import com.dr.iris.character.MainActor;
import com.dr.iris.character.SimpleEnemyActor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rayer on 4/20/15.
 */
public class ObjectManager {

    static private ObjectManager inst;
    List<Bullet> bulletList = new ArrayList<>();
    List<Bullet> recycleList = new ArrayList<>();
    List<BulletGroupSpec> bulletGroupSpecs = new ArrayList<>();

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

    public boolean createBulletGroup(BulletGroupSpec spec) {

        bulletGroupSpecs.add(spec);
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

        for (int i = 0; i < bulletGroupSpecs.size(); ++i) {
            BulletGroupSpec spec = bulletGroupSpecs.get(i);
            if (spec.update(delta) == false) {
                bulletGroupSpecs.remove(i);
            }
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

    public MainActor createMainActor(String actorAtlas) {
        MainActor actor = new MainActor(actorAtlas);
        gameActorList.add(actor);
        return actor;
    }

    public SimpleEnemyActor createEnemyActor(String actorAtlas) {
        SimpleEnemyActor actor = new SimpleEnemyActor(actorAtlas);
        gameActorList.add(actor);
        return actor;
    }

    public GameActor createEnemyActor(String actorAtlas, float x, float y) {
        GameActor actor = new SimpleEnemyActor(actorAtlas);
        actor.setPosition(x, y);
        gameActorList.add(actor);
        return actor;
    }

    public List<GameActor> getActorList() {
        return gameActorList;
    }
}
