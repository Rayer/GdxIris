package com.dr.iris.event;

import com.badlogic.gdx.math.Vector2;
import com.dr.iris.Objects.Bullet;
import com.dr.iris.character.GameActor;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Rayer on 6/1/15.
 */
public class EventPrototypes {
    static Map<String, EventPrototype> epMap;
    public static final EventPrototype NOTIFY_UNCLICK = new EventPrototypeBuilder("NOTIFY_UNCLICK").create();

    public static final EventPrototype NOTIFY_COLLIDE = new EventPrototypeBuilder("NOTIFY_COLLIDE")
            .addFieldNameType("target", GameActor.class)
            .addFieldNameType("delta", Vector2.class)
            .create();

    public static final EventPrototype NOTIFY_BULLET_HIT = new EventPrototypeBuilder("NOTIFY_BULLET_HIT")
            .addFieldNameType("source", Bullet.class)
            .create();

    public static final EventPrototype TEST_MULTIPARAM_EVENT = new EventPrototypeBuilder("TEST_MULTIPARAM_EVENT")
            .addFieldNameType("test_1st_param", Integer.class)
            .addFieldNameType("test_2nd_param", String.class)
            .addFieldNameType("test_3rd_param", Float.class)
            .create();


    public static EventPrototype getPrototypeByName(String name) {
        return epMap.get(name);
    }

    public static EventPrototype registerPrototype(EventPrototype prototype) {
        if (epMap == null) epMap = new HashMap<>();
        epMap.put(prototype.getName(), prototype);
        return prototype;
    }

}
