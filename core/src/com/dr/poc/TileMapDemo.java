package com.dr.poc;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.dr.iris.character.GameActor;
import com.dr.poc.Objects.LinearBulletSpec;
import com.dr.poc.Objects.ObjectManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.spi.LoggerContextFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rayer on 12/31/14.
 */
public class TileMapDemo implements ApplicationListener, GestureDetector.GestureListener {

    TiledMap map;
    OrthographicCamera camera;
    OrthogonalTiledMapRendererWithActorList render;
    SpriteBatch sb;

    float elapsed = 0;
    Vector3 camera_shift = new Vector3(0, 0.5f , 0);
    GameActor actor;
    GameActor actor2;

    Music bgm;

    Logger logger = LogManager.getLogger(this.getClass());


    private GridPoint2 screenGird = new GridPoint2(0, 0);
    CastingStatus casting = CastingStatus.NO_CASTING;
    //Map

    private GridPoint2 mapGrid;

    private GridPoint2 tilePixelGrid;

    private GridPoint2 mapSizeGrid;

    public TileMapDemo() {
    }


    //Pathfinding
    //AStarGridFinder<GridCell> finder = new AStarGridFinder<GridCell>(GridCell.class);
    //NavigationTiledMapLayer nav;


    public TileMapDemo(int x, int y) {
        screenGird.set(x, y);
    }



    @Override
    public void create() {

        logger.info("Creating!");
        sb = new SpriteBatch();

        screenGird = getScreenGrid();

        createCamera();

        //Load map
        loadMap();

        mapGrid = getMapGrid();
        tilePixelGrid = getTilePixelGrid();

        mapSizeGrid = getMapSizeGrid();


        //Load A* PF module
        //nav = (NavigationTiledMapLayer)map.getLayers().get("Navigation");


        actor = new GameActor("trabiastudent_f");
        actor.setPosition(20, 40);
        actor2 = new GameActor("steampunk_f5");
        actor2.setPosition(150, 140);
        actor2.setTouchable(Touchable.enabled);

        List<Actor> actorList = new ArrayList<Actor>();
        actorList.add(actor);
        actorList.add(actor2);

        render = new OrthogonalTiledMapRendererWithActorList(map, sb, actorList);
        Gdx.input.setInputProcessor(new GestureDetector(this));

        bgm = Gdx.audio.newMusic(Gdx.files.internal("Music/blood.mp3"));
        //bgm.play();
        //bgm.setLooping(true);

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void render() {
        float delta = Gdx.graphics.getDeltaTime();
        Gdx.gl.glClearColor(0, 1, 0, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //Update camera position
        //Camera.x - viewPort.width must > 0, or just set to 0
        //Camera.y - viewPort.height must > 0, or just set to 0

        //restrict 0, 0 side
        float cameraX = actor.getX() - (camera.viewportWidth / 2) > 0 ? actor.getX() : camera.viewportWidth / 2;
        float cameraY = actor.getY() - (camera.viewportHeight / 2) > 0 ? actor.getY() : camera.viewportHeight / 2;

        //restrict h, w side
        cameraX = actor.getX() + (camera.viewportWidth / 2) > mapSizeGrid.x ? mapSizeGrid.x - (camera.viewportWidth / 2) : cameraX;
        cameraY = actor.getY() + (camera.viewportHeight / 2) > mapSizeGrid.y ? mapSizeGrid.y - (camera.viewportHeight / 2) : cameraY;


        moveCamera(camera_shift);
        camera.update();
        ObjectManager.getInst().update(delta);

        //moveCamera(camera_shift);
        render.setView(camera);
        render.render(delta);

    }

    private void moveCamera(Vector3 moveVector) {
        camera.position.add(moveVector);
        for(Actor actor : render.actorList) {
            actor.moveBy(moveVector.x, moveVector.y);
        }
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

        map.dispose();
        render.dispose();
        sb.dispose();

        actor.dispose();

        bgm.dispose();
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {

        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        logger.info("Touch down invoked : " + x + "," + y + " and times : " + count);

        Vector3 clickCoordinates = new Vector3(x, y, 0);
        Vector3 position = camera.unproject(clickCoordinates);

        if (actor2.hit(position.x-actor2.getX(), position.y-actor2.getY(), true) != null) {
            ObjectManager obm = ObjectManager.getInst();
            obm.createBulletObject(new LinearBulletSpec(new Vector2(position.x, position.y), new Vector2(2, 2), 5.0f));

            logger.info("hit!");
        //processing moving
        } else if (casting == CastingStatus.NO_CASTING) {
            //Vector3 clickCoordinates = new Vector3(x, y, 0);
            //Vector3 position = camera.unproject(clickCoordinates);

            int cellX = (int) (position.x / this.tilePixelGrid.x);
            int cellY = (int) (position.y / this.tilePixelGrid.y);

            logger.info("Cell ID : " + cellX + ", " + cellY);

            actor.clearActions();
            //Fix acter speed 200 per second
            float length = (new Vector2(position.x - actor.getX(), position.y - actor.getY())).len();
            actor.addAction(Actions.moveTo(position.x, position.y, length / 200));
        } else {
            casting = CastingStatus.ADJUSTING;
        }

        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        //Enter casting mode
        Vector3 clickCoordinates = new Vector3(x, y, 0);
        Vector3 position = camera.unproject(clickCoordinates);

        actor.clearActions();

        casting = CastingStatus.ADJUSTING;
        logger.info("longpress invoked : " + position.x + "," + position.y);

        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {

        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        if (casting == CastingStatus.ADJUSTING) {
            logger.info("Start casting!");
            //casting = CastingStatus.CASTING;
            casting = CastingStatus.NO_CASTING;
        }
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

    //Casting
    enum CastingStatus {
        NO_CASTING,
        ADJUSTING,
        CASTING
    }

    private GridPoint2 getMapGrid() {
        MapProperties prop = map.getProperties();
        return new GridPoint2(prop.get("width", Integer.class), prop.get("height", Integer.class));
    }

    private void loadMap() {
        map = new TmxMapLoader().load("data/FlyingDemo.tmx");
    }

    private GridPoint2 getScreenGrid() {
        return new GridPoint2(screenGird.x == 0 ? Gdx.graphics.getWidth() : screenGird.x,
                screenGird.y == 0 ? Gdx.graphics.getHeight() : screenGird.y);
    }

    private void createCamera() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, screenGird.x, screenGird.y);
        //camera.setToOrtho(false);
        camera.update();

    }

    private GridPoint2 getTilePixelGrid() {
        MapProperties prop = map.getProperties();
        return new GridPoint2(prop.get("tilewidth", Integer.class), prop.get("tileheight", Integer.class));
    }

    private GridPoint2 getMapSizeGrid() {
        return new GridPoint2(mapGrid.x * tilePixelGrid.x, mapGrid.y * tilePixelGrid.y);
    }
}
