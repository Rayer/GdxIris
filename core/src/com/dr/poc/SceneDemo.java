package com.dr.poc;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;

import java.util.Random;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;


/**
 * Created by Rayer on 12/30/14.
 */
public class SceneDemo implements ApplicationListener {


    private Stage stage;

    @Override
    public void create() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        String[] charList = {
                "steampunk_f9", "trabiastudent_f", "xmasgirl3", "misaamane"
        };

        Random random = new Random();

        for (int i = 0; i < 100; ++i) {
            MainActor mainActor = new MainActor(charList[random.nextInt(charList.length)]);

            mainActor.setPosition(random.nextInt(1280), random.nextInt(720));

            mainActor.addAction(sequence(moveTo(random.nextInt(1280), random.nextInt(720), random.nextInt(30)),
                    moveTo(random.nextInt(1280), random.nextInt(720), random.nextInt(30)),
                    moveTo(random.nextInt(1280), random.nextInt(720), random.nextInt(30)),
                    moveTo(random.nextInt(1280), random.nextInt(720), random.nextInt(30)),
                    moveTo(random.nextInt(1280), random.nextInt(720), random.nextInt(30))));


            stage.addActor(mainActor);
        }

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Gdx.graphics.getDeltaTime());
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

    public class MainActor extends Actor {

        CharacterRenderInfo.FACING currentFacing = CharacterRenderInfo.FACING.DOWN;
        CharacterRenderInfo.FACING lastFacing = currentFacing;
        CharacterRenderInfo charRenderInfo;
        Animation currentAnimation;
        float elapsedTime = 0;

        Vector2 lastPos;

        public MainActor(String characterName) {
            setTouchable(Touchable.enabled);
            lastPos = new Vector2(getX(), getY());
            charRenderInfo = CharacterRenderManager.getInst().getCharacter(characterName);
            currentAnimation = new Animation(5f, charRenderInfo.getRegion(currentFacing));
            setBounds(getX(), getY(), currentAnimation.getKeyFrame(0).getRegionWidth(), currentAnimation.getKeyFrame(0).getRegionHeight());

        }

        @Override
        public void act(float delta) {
            super.act(delta);
            //update current facing
            if (lastPos.x != getX() || lastPos.y != getY()) {
                //update facing
                Vector2 facingVector = lastPos.sub(this.getX(), this.getY());
                float facingAngle = facingVector.angle();
                //System.out.println("CurrentAngle : " + facingAngle);
                facingAngle -= 45;
                if (facingAngle < 0) facingAngle += 360;

                if (facingAngle >= 0 && facingAngle < 90) {
                    currentFacing = CharacterRenderInfo.FACING.DOWN;
                } else if (facingAngle >= 90 && facingAngle < 180) {
                    currentFacing = CharacterRenderInfo.FACING.RIGHT;
                } else if (facingAngle >= 180 && facingAngle < 270) {
                    currentFacing = CharacterRenderInfo.FACING.UP;
                } else if (facingAngle >= 270) {
                    currentFacing = CharacterRenderInfo.FACING.LEFT;
                }
                //if(facingAngle >)

                if (lastFacing != currentFacing) {

                    currentAnimation = new Animation(5f, charRenderInfo.getRegion(currentFacing));
                }

                lastFacing = currentFacing;
                lastPos.set(getX(), getY());
            }

        }

        @Override
        public void draw(Batch batch, float parentAlpha) {
            super.draw(batch, parentAlpha);
            elapsedTime += parentAlpha;
            batch.draw(currentAnimation.getKeyFrame(elapsedTime, true), getX(), getY());
            //TextureRegion currentTextureRegion = currentAnimation.getKeyFrame(elapsedTime, true);
            //batch.draw(currentTextureRegion.getTexture(), this.getX(),getY(),this.getOriginX(),this.getOriginY(),this.getWidth(),
            //        this.getHeight(),this.getScaleX(), this.getScaleY(),this.getRotation(),0,0,
            //        currentTextureRegion.getRegionWidth(),currentTextureRegion.getRegionHeight(),false,false);
        }

    }
}
