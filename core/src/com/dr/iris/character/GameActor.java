package com.dr.iris.character;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.utils.Disposable;
import com.dr.iris.Objects.*;


/**
 * Created by Rayer on 12/30/14.
 */
public class GameActor extends com.badlogic.gdx.scenes.scene2d.Actor implements Disposable {

    ActorSpec actorSpec = new ActorSpec();

    public enum Faction {
        ENEMY,
        NON_ENEMY
    }

    /**
     * Not used now
     */
    GroupInfo groupInfo = new GroupInfo();

    CharacterRenderInfo.FACING currentFacing = CharacterRenderInfo.FACING.DOWN;
    CharacterRenderInfo.FACING lastFacing = currentFacing;
    CharacterRenderInfo charRenderInfo;
    Animation currentAnimation;
    float elapsedTime = 0;

    String name;

    int health;

    public static boolean isDebug = false;

    //for debugging
    BitmapFont font;
    Texture debugTexture;

    Vector2 lastPos;

    int debugFrameOffset = 10;

    public GameActor(String characterName) {

        name = characterName;

        setTouchable(Touchable.enabled);
        lastPos = new Vector2(getX(), getY());
        charRenderInfo = CharacterRenderManager.getInst().getCharacter(characterName);
        currentAnimation = new Animation(5f, charRenderInfo.getRegion(currentFacing));
        setBounds(getX(), getY(), currentAnimation.getKeyFrame(0).getRegionWidth(), currentAnimation.getKeyFrame(0).getRegionHeight());

        addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
            }
        });

        //debug info
        font = new BitmapFont();
        font.setColor(Color.GREEN);
        font.setScale(0.8f);

        //frame
        TextureRegion tr = currentAnimation.getKeyFrame(0);
        Pixmap pixmap = new Pixmap(tr.getRegionWidth(), tr.getRegionHeight(), Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.YELLOW);
        pixmap.drawRectangle(0 - debugFrameOffset, 0 - debugFrameOffset, tr.getRegionWidth() + debugFrameOffset, tr.getRegionHeight() + debugFrameOffset);
        debugTexture = new Texture(pixmap);
        pixmap.dispose();

        health = 100;

    }

    public Faction getFaction() {
        return Faction.NON_ENEMY;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        //update current facing
        updateCurrentFacing();

    }

    public boolean isAlive() {
        return health > 0;
    }

    private void updateCurrentFacing() {
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
        if(isDebug) {
            batch.draw(debugTexture, getX(), getY());
            font.draw(batch, name + " : " + health, getX(), getY());
        }
        elapsedTime += parentAlpha;
        batch.draw(currentAnimation.getKeyFrame(elapsedTime, true), getX(), getY());
    }


    @Override
    public void dispose() {
        font.dispose();
        charRenderInfo.dispose();
        debugTexture.dispose();

    }

    public boolean isHit(float x, float y) {
        float x1 = getX();
        float x2 = getX() + getHeight();
        float y1 = getY();
        float y2 = getY() + getWidth();

        return (x > x1 && x < x2) && (y > y1 && y < y2);

    }

    public boolean isHitDebugFrame(float x, float y) {
        float x1 = getX() - debugFrameOffset;
        float x2 = getX() + getHeight() + debugFrameOffset;
        float y1 = getY() - debugFrameOffset;
        float y2 = getY() + getWidth() + debugFrameOffset;

        return (x > x1 && x < x2) && (y > y1 && y < y2);

    }

    public void getHit(BulletSpec spec) {
        health--;
    }

    public void shootTo(GameActor actor) {
        ObjectManager objectManager = ObjectManager.getInst();
        Vector2 deltaPos = new Vector2(actor.getX() - getX(), actor.getY() - getY() + 5);
        //LinearBulletSpec spec = new LinearBulletSpec(new Vector2(mainActor.getX(), mainActor.getY()), deltaPos, 360.0f, 12.0f);
        TracingBulletSpec spec = new TracingBulletSpec(getX(), getY(), actor, 240.0f, 12.0f);
        spec.setFrom(this);
        objectManager.createBulletObject(spec);
    }




}
