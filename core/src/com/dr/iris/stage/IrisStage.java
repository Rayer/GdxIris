package com.dr.iris.stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.dr.iris.Objects.ObjectManager;
import com.dr.iris.Render.IrisRenderer;
import com.dr.iris.character.GameActor;
import com.dr.iris.character.MainActor;
import com.dr.iris.character.SimpleEnemyActor;
import com.dr.iris.character.enemyAction.SimpleEnemyAction1;
import com.dr.iris.character.enemyAction.SimpleEnemyAction2;
import com.dr.iris.log.Log;


/**
 * Iris Stage
 *
 * Stage management
 */
public class IrisStage extends com.badlogic.gdx.scenes.scene2d.Stage {

    static Log log = Log.getLogger();

    //Experimental items
    private MainActor mainActor;
    private ObjectManager objectManager = ObjectManager.getInst();

    static public IrisStage CreateIrisStage() {
        OrthographicCamera camera = new OrthographicCamera();
        log.debug("Set viewport to : " + Gdx.graphics.getWidth() + " / " + Gdx.graphics.getHeight());
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.update();
        Viewport viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);
        return new IrisStage(viewport);
    }

    private IrisStage(Viewport viewport) {
        super(viewport, new SpriteBatch());
        Batch sb = new SpriteBatch();
        TiledMap map = new TmxMapLoader().load("data/Wildness2.tmx");
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

        this.addActor(mainActor);
        this.addActor(enemy1);
        this.addActor(enemy2);
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

    public MainActor getMainActor() {
        return mainActor;
    }


}
