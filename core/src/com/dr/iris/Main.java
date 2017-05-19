package com.dr.iris;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.dr.iris.Objects.ObjectManager;
import com.dr.iris.character.MainActor;
import com.dr.iris.log.Log;

/**
 * Created by rayer on 16/05/2017.
 */
public class Main implements ApplicationListener, GestureDetector.GestureListener {

    Log log = Log.getLogger(Main.class);
    private Stage stage;
    ObjectManager objectManager = ObjectManager.getInst();
    MainActor mainActor;
    OrthographicCamera camera;


    @Override
    public void create() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        mainActor = objectManager.createMainActor("trabiastudent_f");
        stage.addActor(mainActor);
        mainActor.moveBy(200, 300);
        Gdx.input.setInputProcessor(new GestureDetector(this));
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void render() {
        float delta = Gdx.graphics.getDeltaTime();
        objectManager.update(delta);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
        stage.draw();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }


    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        mainActor.clearActions();
//        Vector3 clickCoordinates = new Vector3(x, y, 0);
//        Vector3 position = camera.unproject(clickCoordinates);
        float length = (new Vector2(x - mainActor.getX(), y - mainActor.getY())).len();
        log.debug("Move character to " + x + ", " + y);
        //Fix acter speed 200 per second
        mainActor.addAction(Actions.moveTo(x, y, length / 200));
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

    @Override
    public void pinchStop() {

    }
}
