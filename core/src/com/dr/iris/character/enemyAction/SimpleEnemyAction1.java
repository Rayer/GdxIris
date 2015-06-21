package com.dr.iris.character.enemyAction;

import com.badlogic.gdx.math.GridPoint2;
import com.dr.iris.Objects.BulletGroupSpec;
import com.dr.iris.Objects.SweepingBulletGroup;
import com.dr.iris.action.ActionBase;
import com.dr.iris.action.BulletGroupAction;
import com.dr.iris.action.ForceMoveAction;
import com.dr.iris.character.GameActor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shoi on 15/6/7.
 * TODO: might need to re-factory to read files
 */
public class SimpleEnemyAction1 {

    public static List<ActionBase> getBulletActions(GameActor actor) {
        List<ActionBase> ret = new ArrayList<ActionBase>();

        BulletGroupSpec spec1 = new SweepingBulletGroup(actor, 270.0f, 180.0f, 4.0f);
        BulletGroupAction bullet1 = new BulletGroupAction(spec1, 3.0f);

        BulletGroupSpec spec2 = new SweepingBulletGroup(actor, 300.0f, 180.0f, 4.0f);
        BulletGroupAction bullet2 = new BulletGroupAction(spec2, 3.0f);

        ret.add(bullet1);
        ret.add(bullet2);

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
        GridPoint2 step1 = new GridPoint2(100, 400);
        GridPoint2 step2 = new GridPoint2(150, 400);
        GridPoint2 step3 = new GridPoint2(200, 400);
        GridPoint2 step4 = new GridPoint2(150, 400);
        GridPoint2 step5 = new GridPoint2(100, 400);
        GridPoint2 step6 = new GridPoint2(50, 400);

        GridPoint2 ret[] = {step1, step2, step3, step4, step5, step6};

        return ret;
    }
}
