package com.dr.iris.event;

/**
 * Created by Rayer on 5/31/15.
 */
public class EventInstance {

    EventPrototype prototype;
    Object values[];

    EventInstance(EventPrototype prototype, Object... values) {
        this.prototype = prototype;
        this.values = values;
        if (values.length != getPrototype().getSize())
            throw new IllegalArgumentException("Value size is not equal to prototype");

    }

    public EventPrototype getPrototype() {
        return prototype;
    }

    public <T> T getValue(String valueName) {

        int index;
        for (index = 0; index < getPrototype().getSize(); ++index) {
            if (valueName.equals(getPrototype().getNthFieldName(index))) break;
        }

        if (index >= getPrototype().getSize()) {
            throw new IllegalArgumentException("No property \"" + valueName + "\" is found!");
        }

        Class<?> targetClass = getPrototype().getNthType(index);
        return (T) targetClass.cast(values[index]);
    }


}
