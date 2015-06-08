package com.dr.iris.character.enemyAction;

import com.badlogic.gdx.math.GridPoint2;
import com.dr.iris.Objects.BulletGroupSpec;
import com.dr.iris.Objects.SweepingBulletGroup;
import com.dr.iris.action.ActionBase;
import com.dr.iris.action.BulletAction;
import com.dr.iris.action.ForceMoveAction;
import com.dr.iris.character.GameActor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shoi on 15/6/7.
 *
 * TODO: might need to re-factory to read files
 */
public class SimpleEnemyAction2 {

    public static List<ActionBase> getBulletActions(GameActor actor) {
        List<ActionBase> ret = new ArrayList<ActionBase>();

        BulletGroupSpec spec1 = new SweepingBulletGroup(actor, 220.0f, 180.0f, 4.0f);
        BulletAction bullet1 = new BulletAction(spec1, 3.0f);

        BulletGroupSpec spec2 = new SweepingBulletGroup(actor, 180.0f, 180.0f, 4.0f);
        BulletAction bullet2 = new BulletAction(spec2, 3.0f);

        BulletGroupSpec spec3 = new SweepingBulletGroup(actor, 200.0f, 180.0f, 4.0f);
        BulletAction bullet3 = new BulletAction(spec3, 3.0f);

        ret.add(bullet1);
        ret.add(bullet2);
        ret.add(bullet3);

        return ret;
    }

    public static List<ActionBase> getMoveActions(GameActor actor) {
        List<ActionBase> ret = new ArrayList<ActionBase>();

        for(GridPoint2 point : getEnemySpec()) {
            ForceMoveAction action = new ForceMoveAction(point, actor.getActorSpec().getMoveSpeed(), 5.0f);
            ret.add(action);
        }

        return ret;
    }

    private static GridPoint2[] getEnemySpec() {
        GridPoint2 step1 = new GridPoint2(400, 350);
        GridPoint2 step2 = new GridPoint2(400, 300);
        GridPoint2 step3 = new GridPoint2(400, 250);
        GridPoint2 step4 = new GridPoint2(400, 300);
        GridPoint2 step5 = new GridPoint2(400, 350);
        GridPoint2 step6 = new GridPoint2(400, 400);

        GridPoint2 ret[] = {step1, step2, step3, step4, step5, step6};

        return ret;
    }
}
