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
import com.badlogic.gdx.maps.MapProperties;
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
    int mapSizeX;
    int mapSizeY;

    int screenSizeX = 0;
    int screenSizeY = 0;

    public TileMapDemo() {
    }

    public TileMapDemo(int x, int y) {
        screenSizeX = x;
        screenSizeY = y;
    }

    @Override
    public void create() {

        screenSizeX = screenSizeX == 0 ? Gdx.graphics.getWidth() : screenSizeX;
        screenSizeY = screenSizeY == 0 ? Gdx.graphics.getHeight() : screenSizeY;


        sb = new SpriteBatch();


        camera = new OrthographicCamera();
        camera.setToOrtho(false, screenSizeX, screenSizeY);
        //camera.setToOrtho(false);
        camera.update();


        map = new TmxMapLoader().load("data/Wildness2.tmx");
        MapProperties prop = map.getProperties();

        int mapWidth = prop.get("width", Integer.class);
        int mapHeight = prop.get("height", Integer.class);
        int tilePixelWidth = prop.get("tilewidth", Integer.class);
        int tilePixelHeight = prop.get("tileheight", Integer.class);

        mapSizeX = mapWidth * tilePixelWidth;
        mapSizeY = mapHeight * tilePixelHeight;


        actor = new GameActor("trabiastudent_f");
        actor.setPosition(20, 40);

        List<Actor> actorList = new ArrayList<>();
        actorList.add(actor);

        render = new OrthogonalTiledMapRendererWithActorList(map, sb, actorList);
        Gdx.input.setInputProcessor(this);
        bgm = Gdx.audio.newMusic(Gdx.files.internal("Music/blood.mp3"));
        bgm.play();

        bgm.setLooping(true);

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

        //Update camera position
        //Camera.x - viewPort.width must > 0, or just set to 0
        //Camera.y - viewPort.height must > 0, or just set to 0

        //restrict 0, 0 side
        float cameraX = actor.getX() - (camera.viewportWidth / 2) > 0 ? actor.getX() : camera.viewportWidth / 2;
        float cameraY = actor.getY() - (camera.viewportHeight / 2) > 0 ? actor.getY() : camera.viewportHeight / 2;

        //restrict h, w side
        cameraX = actor.getX() + (camera.viewportWidth / 2) > mapSizeX ? mapSizeX - (camera.viewportWidth / 2) : cameraX;
        cameraY = actor.getY() + (camera.viewportHeight / 2) > mapSizeY ? mapSizeY - (camera.viewportHeight / 2) : cameraY;


        //System.out.println("" + actor.getX() + " / " + camera.viewportWidth / 2 + " " + Gdx.graphics.getWidth());

        camera.position.set(cameraX, cameraY, 0);
        //System.out.println("Camera is set to : ( " + cameraX + ", " + cameraY + ")");
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
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
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
