package com.dr.iris;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.GridPoint2;
import com.dr.iris.Objects.ObjectManager;
import com.dr.iris.Render.IrisRenderer;
import com.dr.iris.character.GameActor;


/**
 * Created by Rayer on 1/1/15.
 */
public class Iris extends ApplicationAdapter {

    TiledMap map;
    InputProcessor inputProcessor;
    SpriteBatch sb;
    IrisRenderer renderer;
    OrthographicCamera camera;
    GridPoint2 screenGrid = new GridPoint2();

    GameActor mainActor;


    ObjectManager objectManager = ObjectManager.getInst();

    public Iris() {
        super();
    }

    @Override
    public void create() {

        sb = new SpriteBatch();
        map = new TmxMapLoader().load("data/Wildness2.tmx");
        renderer = new IrisRenderer(map, sb);

        screenGrid = getScreenGrid();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, screenGrid.x, screenGrid.y);
        //camera.setToOrtho(false);
        camera.update();

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
        float delta = Gdx.graphics.getDeltaTime();
        Gdx.gl.glClearColor(0, 1, 0, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();

        objectManager.update(delta);
        renderer.setView(camera);
        renderer.render(delta);
    }

    private GridPoint2 getScreenGrid() {
        return new GridPoint2(screenGrid.x == 0 ? Gdx.graphics.getWidth() : screenGrid.x,
                screenGrid.y == 0 ? Gdx.graphics.getHeight() : screenGrid.y);
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

