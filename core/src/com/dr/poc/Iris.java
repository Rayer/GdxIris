package com.dr.poc;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;

public class Iris extends ApplicationAdapter implements InputProcessor {
    SpriteBatch batch;
    Texture img;
    private BitmapFont font;

    private Animation animation;
    private float elapsedTime;

    private float width;
    private float height;
    private CharacterRenderInfo character;

    private Sprite sprite;

    @Override
    public void create() {
        batch = new SpriteBatch();
        img = new Texture("badlogic.jpg");
        font = new BitmapFont();
        font.setColor(Color.YELLOW);

        updateScreenParam();

        Gdx.input.setInputProcessor(this);

        //character = CharacterRenderManager.getInst().getCharacter("trabiastudent_f");
        character = CharacterRenderManager.getInst().getCharacter("xmasgirl3");

        animation = new Animation(1 / 3f, character.getRegion(CharacterRenderInfo.FACING.LEFT));

        Pixmap pixmap = new Pixmap(16, 16, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.BLUE);
        pixmap.fill();

        pixmap.setColor(Color.RED);
        pixmap.drawCircle(8, 8, 8);

        Texture texture = new Texture(pixmap);
        sprite = new Sprite(texture);
        sprite.setPosition(50, 50);

        pixmap.dispose();
    }

    @Override
    public void resize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    private void updateScreenParam() {
        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();
    }

    @Override
    public void dispose() {
        batch.dispose();
        img.dispose();
        font.dispose();
        //textureAtlas.dispose();
        CharacterRenderManager.getInst().dispose();

    }

    @Override
    public void render() {

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        sprite.draw(batch);

        //sprite.draw(batch);
        elapsedTime += Gdx.graphics.getDeltaTime();
        TextureRegion currentFrame = animation.getKeyFrame(elapsedTime, true);
        batch.draw(currentFrame, width / 2, height / 2);
        font.draw(batch, "KeyFrame : " + currentFrame.toString()
                , Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
        //batch.draw(sprite);
        //System.out.println("elapsed : " + elapsedTime);
        batch.end();
    }

    private void handleCharacter() {


    }

    @Override
    public boolean keyDown(int keycode) {

        switch (keycode) {
            case Input.Keys.UP:
                animation = new Animation(1 / 3f, character.getRegion(CharacterRenderInfo.FACING.UP));
                sprite.translateY(1f);
                break;
            case Input.Keys.DOWN:
                animation = new Animation(1 / 3f, character.getRegion(CharacterRenderInfo.FACING.DOWN));
                sprite.translateY(-1f);
                break;
            case Input.Keys.LEFT:
                animation = new Animation(1 / 3f, character.getRegion(CharacterRenderInfo.FACING.LEFT));
                sprite.translateX(-1f);
                break;
            case Input.Keys.RIGHT:
                animation = new Animation(1 / 3f, character.getRegion(CharacterRenderInfo.FACING.RIGHT));
                sprite.translateX(1f);
                break;
            case Input.Keys.SPACE:
                animation = new Animation(1 / 3f, character.getAllRegion());
                sprite.setPosition(50, 50);
                break;
        }


        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {

        screenY -= Gdx.graphics.getHeight();
        screenY = -screenY;

        double length = Math.sqrt(Math.pow(((double) (screenX - sprite.getX())), 2d) + Math.pow((double) (screenY - sprite.getY()), 2d));
        sprite.translate((float) ((screenX - sprite.getX()) / length), (float) ((screenY - sprite.getY()) / length));

        System.out.println("Screen : " + screenX + "/" + screenY);
        System.out.println("Sprite : " + sprite.getX() + "/" + sprite.getY());


        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
