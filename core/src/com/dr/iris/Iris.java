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
public class Iris extends ApplicationAdapter implements InputProcessor {

    TiledMap tiledMap;
    Stage stage;

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

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}

