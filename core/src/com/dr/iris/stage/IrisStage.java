package com.dr.iris.stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.dr.iris.Render.IrisRenderer;

/**
 * Created by Rayer on 2/12/15.
 */
public class IrisStage extends com.badlogic.gdx.scenes.scene2d.Stage {

    public Update_Status update(double delta) {
        return Update_Status.TERMINATED;
    }

    TiledMap map;
    Batch sb;

    public IrisStage(Viewport viewport) {
        super(viewport, new SpriteBatch());
        sb = new SpriteBatch();
        map = new TmxMapLoader().load("data/Wildness2.tmx");
        renderer = new IrisRenderer(map, sb);
    }

    IScriptInterpreter getScriptInterpreter() {
        return null;
    }
    IActorManager getActorManager() {
        return null;
    }
    ICamera getMainCamera() {
        return null;
    }
    IAudioManager getAudioManager() {
        return null;
    }
    IrisRenderer renderer;

    enum Update_Status {
        RUNNING,
        PAUSE,
        TERMINATED
    }

    @Override
    public void draw() {
        //super.draw();
        float delta = Gdx.graphics.getDeltaTime();
        renderer.setView((OrthographicCamera)this.getViewport().getCamera());
        renderer.render(delta);
    }
}
