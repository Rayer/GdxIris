package com.dr.iris.stage;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.dr.iris.Objects.ObjectManager;
import com.dr.iris.character.GameActor;
import com.dr.iris.character.MainActor;
import com.dr.iris.character.SimpleEnemyActor;
import com.dr.iris.event.*;
import com.dr.iris.log.Log;

/**
 * Created by rayer on 22/05/2017.
 */
public class GestureControl implements GestureDetector.GestureListener {

    private static Log log = Log.getLogger();
    private ObjectManager objectManager = ObjectManager.getInst();
    private EventProxy eventHandler = new EventSelfParseProxy(this);

    //TODO: Remove instance and use interface instead
    private MainActor mainActor;
    private OrthographicCamera camera;

    public GestureControl(IrisStage irisStage) {
        mainActor = irisStage.getMainActor();
        camera = (OrthographicCamera)irisStage.getViewport().getCamera();
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

        if (!hit) {
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
