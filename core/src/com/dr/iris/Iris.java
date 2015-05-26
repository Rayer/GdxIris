package com.dr.iris;

import com.badlogic.gdx.Application;
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
import com.dr.iris.character.MainActor;
import com.dr.iris.character.SimpleEnemyActor;
import com.dr.iris.effect.EffectManager;
import com.dr.iris.log.Log;
import com.dr.iris.ui.UIObjectsManager;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;


/**
 * Created by Rayer on 1/1/15.
 */
public class Iris extends ApplicationAdapter implements GestureDetector.GestureListener {

    Log log = Log.getLogger(Iris.class);


    TiledMap map;
    InputProcessor inputProcessor;
    SpriteBatch sb;
    IrisRenderer renderer;
    OrthographicCamera camera;
    GridPoint2 screenGrid = new GridPoint2();

    MainActor mainActor;

    ObjectManager objectManager = ObjectManager.getInst();

    public Iris() {
        super();
    }

    public Iris(int x, int y) {
        screenGrid.set(x, y);
    }


    @Override
    public void create() {
        Gdx.app.setLogLevel(Application.LOG_DEBUG);

        log.debug("Starting Iris");
        sb = new SpriteBatch();
        map = new TmxMapLoader().load("data/Wildness2.tmx");
        renderer = new IrisRenderer(map, sb);
        Gdx.input.setInputProcessor(new GestureDetector(this));


        setupCamera();
        GameActor.isDebug = true;

        mainActor = objectManager.createMainActor("trabiastudent_f");
        mainActor.setPosition(20, 40);

//        for (int i = 0; i < 2; ++i) {
//            Random random = new Random();
//            objectManager.createEnemyActor("steampunk_f9", random.nextInt(screenGrid.x), random.nextInt(screenGrid.y));
//        }
        //enemy 1
        GridPoint2 point1 = new GridPoint2(50, 400);
        GridPoint2[] spec1 = getEnemySpec1();
        SimpleEnemyActor enemy1 = objectManager.createEnemyActor("steampunk_f9", point1.x, point1.y);
        enemy1.setActionSpec(spec1);

        //enemy 2
        GridPoint2 point2 = new GridPoint2(400, 400);
        GridPoint2[] spec2 = getEnemySpec2();
        SimpleEnemyActor enemy2 = objectManager.createEnemyActor("steampunk_f9", point2.x, point2.y);
        enemy2.setActionSpec(spec2);

        super.create();
    }

    private GridPoint2[] getEnemySpec1() {
        GridPoint2 step1 = new GridPoint2(100, 400);
        GridPoint2 step2 = new GridPoint2(150, 400);
        GridPoint2 step3 = new GridPoint2(200, 400);
        GridPoint2 step4 = new GridPoint2(150, 400);
        GridPoint2 step5 = new GridPoint2(100, 400);
        GridPoint2 step6 = new GridPoint2(50, 400);

        GridPoint2 ret[] = {step1, step2, step3, step4, step5, step6};

        return ret;
    }

    private GridPoint2[] getEnemySpec2() {
        GridPoint2 step1 = new GridPoint2(400, 350);
        GridPoint2 step2 = new GridPoint2(400, 300);
        GridPoint2 step3 = new GridPoint2(400, 250);
        GridPoint2 step4 = new GridPoint2(400, 300);
        GridPoint2 step5 = new GridPoint2(400, 350);
        GridPoint2 step6 = new GridPoint2(400, 400);

        GridPoint2 ret[] = {step1, step2, step3, step4, step5, step6};

        return ret;
    }

    private void setupCamera() {
        screenGrid = getScreenGrid();
        camera = new OrthographicCamera();
        camera.zoom -= 0.3f; // zoom in
        camera.setToOrtho(false, screenGrid.x, screenGrid.y);
        //camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        camera.update();
    }



    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        screenGrid.set(width, height);
        setupCamera();
    }

    @Override
    public void render() {
        super.render();
        float delta = Gdx.graphics.getDeltaTime();
        Gdx.gl.glClearColor(0, 1, 0, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();

        //All update here should be gathered into a pre-render control
        UIObjectsManager.getInst().update(delta);
        objectManager.update(delta);
        EffectManager.getInst().update(delta);
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
        log.debug("touchDown");
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        log.debug("tap");
        mainActor.clearActions();
        Vector3 clickCoordinates = new Vector3(x, y, 0);
        Vector3 position = camera.unproject(clickCoordinates);

        boolean hit = false;
        for (GameActor actor : objectManager.getActorList()) {
            if (actor.getFaction() != GameActor.Faction.ENEMY) continue;

            if (actor.isHitDebugFrame(position.x, position.y)) {
                mainActor.setShootingTo(actor);
                hit = true;
                if (actor instanceof SimpleEnemyActor) {
                    ((SimpleEnemyActor) actor).getClicked();
                }
            }

        }

        if (hit == false) {
            float length = (new Vector2(position.x - mainActor.getX(), position.y - mainActor.getY())).len();
            Log.debug_s("Move character to " + position);
            //Fix acter speed 200 per second
            mainActor.addAction(Actions.moveTo(position.x, position.y, length / 200));
        }

        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        log.debug("longPress");
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        log.debug("fling");
        doPlayerFling(velocityX, velocityY);
        return false;
    }

    private void doPlayerFling(float velocityX, float velocityY) {
        float dashMovement = 100.0f; // it should be player status.
        float dashSpeed = 0.1f; // it should be player status.

        Vector2 v = new Vector2(velocityX, velocityY);

        float angle = v.angle();
        float addx = dashMovement * (float)Math.cos(Math.toRadians(angle));
        float addy = dashMovement * (float)Math.sin(Math.toRadians(angle));
        addy = -addy;
        mainActor.clearActions();
        mainActor.addAction(Actions.moveTo(mainActor.getX() + addx, mainActor.getY() + addy, dashSpeed));
    }

    private Vector2 getPlayerVector2() {
        return new Vector2(mainActor.getX(), mainActor.getY());
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        //log.debug("pan");
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        //log.debug("panStop");
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        //log.debug("zoom");
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        //log.debug("pinch");
        return false;
    }
}

