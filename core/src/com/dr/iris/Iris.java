package com.dr.iris;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.dr.iris.Objects.ObjectManager;
import com.dr.iris.Render.IrisRenderer;
import com.dr.iris.character.GameActor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Random;


/**
 * Created by Rayer on 1/1/15.
 */
public class Iris extends ApplicationAdapter implements GestureDetector.GestureListener {

    TiledMap map;
    InputProcessor inputProcessor;
    SpriteBatch sb;
    IrisRenderer renderer;
    OrthographicCamera camera;
    GridPoint2 screenGrid = new GridPoint2();

    GameActor mainActor;

    Logger logger = LogManager.getLogger(Iris.class);


    ObjectManager objectManager = ObjectManager.getInst();

    public Iris() {
        super();
    }

    public Iris(int x, int y) {
        screenGrid.set(x, y);
    }


    @Override
    public void create() {

        sb = new SpriteBatch();
        map = new TmxMapLoader().load("data/Wildness2.tmx");
        renderer = new IrisRenderer(map, sb);
        Gdx.input.setInputProcessor(new GestureDetector(this));


        setupCamera();
        GameActor.isDebug = true;

        mainActor = objectManager.createMainActor("trabiastudent_f");
        mainActor.setPosition(20, 40);

        for (int i = 0; i < 5; ++i) {
            Random random = new Random();
            objectManager.createEnemyActor("steampunk_f9", random.nextInt(screenGrid.x), random.nextInt(screenGrid.y));
        }

        super.create();
    }

    private void setupCamera() {
        screenGrid = getScreenGrid();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, screenGrid.x, screenGrid.y);
        camera.update();
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


    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {

        mainActor.clearActions();
        Vector3 clickCoordinates = new Vector3(x, y, 0);
        Vector3 position = camera.unproject(clickCoordinates);

        boolean hit = false;
        for (GameActor actor : objectManager.getActorList()) {
            if (actor.getFaction() != GameActor.Faction.ENEMY) continue;

            if (actor.isHitDebugFrame(position.x, position.y)) {
                mainActor.shootTo(actor);
                hit = true;
            }

        }

        if (hit == false) {
            float length = (new Vector2(position.x - mainActor.getX(), position.y - mainActor.getY())).len();
            logger.info("Move character to " + position);
            //Fix acter speed 200 per second
            mainActor.addAction(Actions.moveTo(position.x, position.y, length / 200));
        }

        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
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

