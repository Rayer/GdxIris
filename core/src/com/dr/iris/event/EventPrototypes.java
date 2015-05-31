package com.dr.iris.event;

import com.badlogic.gdx.math.Vector2;
import com.dr.iris.character.GameActor;

/**
 * Created by Rayer on 6/1/15.
 */
public class EventPrototypes {
    public static final EventPrototype NOTIFY_UNCLICK = new EventPrototypeBuilder("NOTIFY_UNCLICK").create();
    public static final EventPrototype NOTIFY_COLLIDE = new EventPrototypeBuilder("NOTIFY_COLLIDE")
            .addFieldNameType("target", GameActor.class)
            .addFieldNameType("delta", Vector2.class)
            .create();

}
