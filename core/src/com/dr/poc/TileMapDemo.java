package com.dr.poc;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.dr.iris.character.GameActor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rayer on 12/31/14.
 */
public class TileMapDemo implements ApplicationListener, InputProcessor {

    Texture img;
    TiledMap map;
    OrthographicCamera camera;
    OrthogonalTiledMapRendererWithActorList render;
    SpriteBatch sb;

    float elapsed = 0;
    GameActor actor;

    Music bgm;

    @Override
    public void create() {
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        sb = new SpriteBatch();


        camera = new OrthographicCamera();
        camera.setToOrtho(false, 680, 480);
        camera.update();
        map = new TmxMapLoader().load("data/Wildness2.tmx");

        actor = new GameActor("steampunk_f9");
        actor.setPosition(20, 40);

        List<Actor> actorList = new ArrayList<>();
        actorList.add(actor);

        render = new OrthogonalTiledMapRendererWithActorList(map, sb, actorList);
        Gdx.input.setInputProcessor(this);
        bgm = Gdx.audio.newMusic(Gdx.files.internal("Music/blood.mp3"));
        bgm.play();


    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void render() {
        float delta = Gdx.graphics.getDeltaTime();
        Gdx.gl.glClearColor(0, 1, 0, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //camera.project(new Vector3(actor.getX(), actor.getY(), 0));
        camera.position.set(actor.getX(), actor.getY(), 0);
        camera.update();
        render.setView(camera);
        render.render(delta);


    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.LEFT)
            camera.translate(-32, 0);
        if (keycode == Input.Keys.RIGHT)
            camera.translate(32, 0);
        if (keycode == Input.Keys.UP)
            camera.translate(0, 32);
        if (keycode == Input.Keys.DOWN)
            camera.translate(0, -32);
        if (keycode == Input.Keys.NUM_1)
            map.getLayers().get(0).setVisible(!map.getLayers().get(0).isVisible());
        if (keycode == Input.Keys.NUM_2)
            map.getLayers().get(1).setVisible(!map.getLayers().get(1).isVisible());
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        System.out.println("Touch down invoked : " + screenX + "," + screenY);
        Vector3 clickCoordinates = new Vector3(screenX, screenY, 0);
        Vector3 position = camera.unproject(clickCoordinates);

        actor.clearActions();
        //Fix acter speed 200 per second
        float length = (new Vector2(position.x - actor.getX(), position.y - actor.getY())).len();
        actor.addAction(Actions.moveTo(position.x, position.y, length / 200));
        return true;
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
