package com.dr.iris.stage;

import com.dr.iris.character.ActorSpec;
import com.dr.iris.character.GameActor;

import java.util.List;

/**
 * Created by Rayer on 2/12/15.
 */
public interface IActorManager extends IResourcePool {
    GameActor createActor(ActorSpec spec, ActorInfo info);

    boolean destroyActor(GameActor actor);

    List<GameActor> getActors();

    int update();
}
