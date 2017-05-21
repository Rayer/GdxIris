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
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.dr.iris.Objects.ObjectManager;
import com.dr.iris.Render.IrisRenderer;
import com.dr.iris.character.GameActor;
import com.dr.iris.character.MainActor;
import com.dr.iris.character.SimpleEnemyActor;
import com.dr.iris.character.enemyAction.SimpleEnemyAction1;
import com.dr.iris.character.enemyAction.SimpleEnemyAction2;
import com.dr.iris.effect.EffectManager;
import com.dr.iris.event.*;
import com.dr.iris.log.Log;
import com.dr.iris.stage.IrisStage;
import com.dr.iris.ui.UIObjectsManager;


/**
 * Created by Rayer on 1/1/15.
 */
public class Iris extends ApplicationAdapter implements GestureDetector.GestureListener {

    Log log = Log.getLogger(Iris.class);

    OrthographicCamera camera;

    IrisStage stage;

    MainActor mainActor;

    ObjectManager objectManager = ObjectManager.getInst();

    EventProxy eventHandler = new EventSelfParseProxy(this);


    @Override
    public void create() {
        Gdx.app.setLogLevel(Application.LOG_DEBUG);

        //Set camera and batch to Stage
        Viewport viewport = setupCamera();
        stage = new IrisStage(viewport);

        log.debug("Starting Iris with Viewport : " + viewport.getScreenWidth() + " / " + viewport.getScreenHeight());
        Gdx.input.setInputProcessor(new GestureDetector(this));

        GameActor.isDebug = true;

        mainActor = objectManager.createMainActor("trabiastudent_f");
        mainActor.setPosition(20, 40);


        //enemy 1
        GridPoint2 point1 = new GridPoint2(50, 400);
        SimpleEnemyActor enemy1 = objectManager.createEnemyActor("steampunk_f9", point1.x, point1.y);
        enemy1.getActorSpec().setBulletActions(SimpleEnemyAction1.getBulletActions(enemy1));
        enemy1.getActorSpec().setMoveActions(SimpleEnemyAction1.getMoveActions(enemy1));

        //enemy 2
        GridPoint2 point2 = new GridPoint2(400, 400);
        SimpleEnemyActor enemy2 = objectManager.createEnemyActor("steampunk_f9", point2.x, point2.y);
        enemy2.getActorSpec().setBulletActions(SimpleEnemyAction2.getBulletActions(enemy2));
        enemy2.getActorSpec().setMoveActions(SimpleEnemyAction2.getMoveActions(enemy2));

        super.create();
    }

    private Viewport setupCamera() {
        camera = new OrthographicCamera();
        //camera.zoom -= 0.3f; // zoom in
        log.debug("Set viewport to : " + Gdx.graphics.getWidth() + " / " + Gdx.graphics.getHeight());
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.update();
        return new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);
    }



    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        stage.getViewport().update(width, height, false);
    }

    @Override
    public void render() {
        super.render();
        float delta = Gdx.graphics.getDeltaTime();
        Gdx.gl.glClearColor(0, 1, 0, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //camera.update();

        //All update here should be gathered into a pre-render control
        UIObjectsManager.getInst().update(delta);
        objectManager.update(delta);
        EffectManager.getInst().update(delta);
        stage.draw();
    }

//    private GridPoint2 getScreenGrid() {
//        return new GridPoint2(screenGrid.x == 0 ? Gdx.graphics.getWidth() : screenGrid.x,
//                screenGrid.y == 0 ? Gdx.graphics.getHeight() : screenGrid.y);
//    }

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
        EventNexus.getInst().sendEvent(eventHandler, EventFactory.createEventByPrototype(EventPrototypes.NOTIFY_FIRE_BOMB, camera.position.x, camera.position.y));
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

    @Override
    public void pinchStop() {

    }
}

