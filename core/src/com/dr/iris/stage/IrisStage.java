package com.dr.iris.stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
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
import com.dr.iris.event.*;
import com.dr.iris.log.Log;


/**
 * Created by Rayer on 2/12/15.
 */
public class IrisStage extends com.badlogic.gdx.scenes.scene2d.Stage implements GestureDetector.GestureListener {

    TiledMap map;
    Batch sb;
    OrthographicCamera camera;
    Log log = Log.getLogger(this.getClass());

    //Experimental items
    MainActor mainActor;
    ObjectManager objectManager = ObjectManager.getInst();
    EventProxy eventHandler = new EventSelfParseProxy(this);

    static public IrisStage CreateIrisStage() {
        OrthographicCamera camera = new OrthographicCamera();
        //log.debug("Set viewport to : " + Gdx.graphics.getWidth() + " / " + Gdx.graphics.getHeight());
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.update();
        Viewport viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);
        return new IrisStage(viewport);
    }

    private IrisStage(Viewport viewport) {
        super(viewport, new SpriteBatch());
        sb = new SpriteBatch();
        map = new TmxMapLoader().load("data/Wildness2.tmx");
        renderer = new IrisRenderer(map, sb);
        initExpItems();
    }

    IrisRenderer renderer;

    enum Update_Status {
        RUNNING,
        PAUSE,
        TERMINATED
    }

    private void initExpItems() {
        GameActor.isDebug = true;
        camera = (OrthographicCamera)getViewport().getCamera();

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
    }

    @Override
    public void draw() {
        float delta = Gdx.graphics.getDeltaTime();
        renderer.setView((OrthographicCamera)this.getViewport().getCamera());
        renderer.render(delta);
    }


    public Update_Status update(double delta) {
        return Update_Status.TERMINATED;
    }

    /**
     * Control Gestures
     */

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
        float addx = dashMovement * (float) Math.cos(Math.toRadians(angle));
        float addy = dashMovement * (float) Math.sin(Math.toRadians(angle));
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
        return false;
    }

    @Override
    public void pinchStop() {

    }
}
