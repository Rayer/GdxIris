package com.dr.iris;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.input.GestureDetector;
import com.dr.iris.Objects.ObjectManager;
import com.dr.iris.effect.EffectManager;
import com.dr.iris.log.Log;
import com.dr.iris.stage.GestureControl;
import com.dr.iris.stage.IrisStage;
import com.dr.iris.ui.UIObjectsManager;


public class Iris extends ApplicationAdapter {

    Log log = Log.getLogger();
    IrisStage stage;
    ObjectManager objectManager = ObjectManager.getInst();

    @Override
    public void create() {
        Gdx.app.setLogLevel(Application.LOG_DEBUG);

        //Set camera and batch to Stage
        stage = IrisStage.CreateIrisStage();

        //TODO: .......so complex
        Gdx.input.setInputProcessor(new GestureDetector(new GestureControl(stage)));

        super.create();
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
        stage.act(delta);
        UIObjectsManager.getInst().update(delta);
        objectManager.update(delta);
        EffectManager.getInst().update(delta);
        stage.draw();
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

