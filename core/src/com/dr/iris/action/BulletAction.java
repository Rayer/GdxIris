package com.dr.iris.action;

import com.dr.iris.Objects.BulletGroupSpec;
import com.dr.iris.Objects.ObjectManager;
import com.dr.iris.Objects.SweepingBulletGroup;
import com.dr.iris.character.GameActor;

/**
 * Created by shoi on 15/6/7.
 */
public class BulletAction extends ActionBase {

    BulletGroupSpec spec;

    public BulletAction(BulletGroupSpec spec, float initCoolDown) {
        this.actionType = ActionType.Bullet;
        this.spec = spec;
        this.actionCooldown = initCoolDown;
        this.initCooldown= initCoolDown;
    }

    @Override
    public boolean action(GameActor actor, float delta) {
        if(countCoolDown(delta)) {
            fire();

            return true;
        }

        return false;
    }

    private void fire() {
        if(spec != null) {

            ObjectManager.getInst().createBulletGroup(spec.newInst());
        }
    }
}
