package com.dr.iris.action;

import com.dr.iris.character.GameActor;

/**
 * Created by Rayer on 1/1/15.
 */
public abstract class ActionBase {

    public enum ActionType {None, Bullet, Move};

    public ActionType actionType = ActionType.None;
    public float initCooldown = 0;
    public float actionCooldown = 0;

    public abstract boolean action (GameActor actor, float delta);

    protected boolean countCoolDown(float delta) {
        actionCooldown -= delta;
        if(actionCooldown < 0) {
            actionCooldown = initCooldown;
            return true;
        }

        return false;
    }
}
