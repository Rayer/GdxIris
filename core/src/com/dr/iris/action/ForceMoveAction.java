package com.dr.iris.action;

import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.dr.iris.character.GameActor;

/**
 * Created by shoi on 15/6/7.
 *
 * force move to target point
 */
public class ForceMoveAction extends ActionBase {

    GridPoint2 step;
    float moveSpeed;

    public ForceMoveAction(GridPoint2 step, float moveSpeed, float initCoolDown) {
        this.step = step;
        this.moveSpeed = moveSpeed;
        this.actionCooldown = initCoolDown;
        this.initCooldown= initCoolDown;
    }

    @Override
    public boolean action(GameActor actor, float delta) {
        if(countCoolDown(delta)) {
            move(actor);

            return true;
        }

        return false;
    }

    private void move(GameActor actor) {

        if(step == null) {
            return;
        }

        float moveToX = step.x;
        float moveToY = step.y;

        float distance = new Vector2(actor.getX() - moveToX, actor.getY() - moveToY).len();
        actor.addAction(Actions.moveTo(moveToX, moveToY, distance / moveSpeed));

    }
}
