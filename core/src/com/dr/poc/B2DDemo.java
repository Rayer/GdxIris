package com.dr.poc;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.dr.iris.log.Log;

/**
 * Created by Rayer on 5/18/15.
 */
public class B2DDemo implements ApplicationListener {

    World world;
    Box2DDebugRenderer debugRender;

    OrthographicCamera camera;
    GridPoint2 screenGrid = new GridPoint2();
    SpriteBatch batch;

    Sprite sprite;
    Body body;

    Log logger = Log.getLogger(B2DDemo.class);

    @Override
    public void create() {
        Box2D.init();
        world = new World(new Vector2(0, -98f), true);
        debugRender = new Box2DDebugRenderer();

        batch = new SpriteBatch();
        sprite = new Sprite(new Texture(Gdx.files.internal("data/cross_2.png")));

        sprite.setPosition(Gdx.graphics.getWidth() / 2 - sprite.getWidth() / 2,
                Gdx.graphics.getHeight() / 2);

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(sprite.getX(), sprite.getY());
        body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(sprite.getWidth()/2, sprite.getHeight()/2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;

        Fixture fixture = body.createFixture(fixtureDef);

        shape.dispose();
    }

    public B2DDemo(int x, int y) {
        screenGrid.set(x, y);
    }

    public B2DDemo() {

    }

    private void setupCamera() {
        screenGrid = getScreenGrid();
        camera = new OrthographicCamera();
        camera.zoom -= 0.3f; // zoom in
        camera.setToOrtho(false, screenGrid.x, screenGrid.y);
        //camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        camera.update();
    }

    private GridPoint2 getScreenGrid() {
        return new GridPoint2(screenGrid.x == 0 ? Gdx.graphics.getWidth() : screenGrid.x,
                screenGrid.y == 0 ? Gdx.graphics.getHeight() : screenGrid.y);
    }

    @Override
    public void resize(int width, int height) {
        logger.info("Resize to : " + width + "," + height);
        screenGrid.set(width, height);
        setupCamera();
    }

    @Override
    public void render() {
        float delta = Gdx.graphics.getDeltaTime();
        world.step(delta, 6, 2);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();

        sprite.setPosition(body.getPosition().x, body.getPosition().y);


        batch.begin();
        sprite.draw(batch);
        batch.end();

        debugRender.render(world, camera.combined);

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }

}
