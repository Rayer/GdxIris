package com.dr.iris.event;

/**
 * Created by Rayer on 5/31/15.
 */
public class EventFactory {

    public static EventInstance createEventByPrototype(EventPrototype prototype, Object... values) {
        return new EventInstance(prototype, values);
    }

    public static EventInstance createEventByPrototype(String prototypeName, Object... values) {
        return null;
    }

}
