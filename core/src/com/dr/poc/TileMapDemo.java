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
import com.badlogic.gdx.input.GestureDetector;
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
public class TileMapDemo implements ApplicationListener, GestureDetector.GestureListener {

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

    //Casting
    boolean casting = false;


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
        Gdx.input.setInputProcessor(new GestureDetector(this));
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
    public boolean touchDown(float x, float y, int pointer, int button) {

        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        System.out.println("Touch down invoked : " + x + "," + y + " and times : " + count);
        //processing moving
        if(casting == false) {
            Vector3 clickCoordinates = new Vector3(x, y, 0);
            Vector3 position = camera.unproject(clickCoordinates);

            actor.clearActions();
            //Fix acter speed 200 per second
            float length = (new Vector2(position.x - actor.getX(), position.y - actor.getY())).len();
            actor.addAction(Actions.moveTo(position.x, position.y, length / 200));
        } else {
            casting = false;
        }

        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        //Enter casting mode
        Vector3 clickCoordinates = new Vector3(x, y, 0);
        Vector3 position = camera.unproject(clickCoordinates);

        actor.clearActions();

        casting = true;
        System.out.println("longpress invoked : " + clickCoordinates.x + "," + clickCoordinates.y);

        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        return false;
    }
}
