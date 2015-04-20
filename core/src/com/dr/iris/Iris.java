package com.dr.iris;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.dr.iris.character.GameActor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rayer on 1/1/15.
 */
public class Iris extends ApplicationAdapter {

    TiledMap tiledMap;
    Stage stage;
    InputProcessor inputProcessor;

    List<GameActor> actorBaseList = new ArrayList<>();


    public Iris() {
        super();
    }

    @Override
    public void create() {
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

