package com.dr.iris;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.dr.iris.Objects.ObjectManager;
import com.dr.iris.Render.IrisRenderer;
import com.dr.iris.character.GameActor;


/**
 * Created by Rayer on 1/1/15.
 */
public class Iris extends ApplicationAdapter {

    //TiledMap tiledMap;
    InputProcessor inputProcessor;
    IrisRenderer renderer;
    Camera camera;

    GameActor mainActor;


    ObjectManager objectManager;

    public Iris() {
        super();
    }

    @Override
    public void create() {

        //mainActor = new GameActor("trabiastudent_f");
        //mainActor.setPosition(20, 40);
        mainActor = objectManager.createActor("trabiastudent_f");
        mainActor.setPosition(20, 40);

        super.create();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void render() {

        super.render();
    }

    @Override
    public void pause() {
        super.pause();
    }

    @Override
    public void resume() {
        super.resume();
    }

    @Override
    public void dispose() {
        super.dispose();
    }


}

