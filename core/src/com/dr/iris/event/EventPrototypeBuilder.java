package com.dr.iris.event;

/**
 * Created by Rayer on 6/1/15.
 */

import java.util.ArrayList;
import java.util.List;

/**
 * GenericEventPrototype
 * This is used to let user create an event prototype on demand
 */
class GenericEventPrototype implements EventPrototype {

    String[] fieldNames;
    Class<?>[] types;
    String eventName;

    GenericEventPrototype(String name, String[] fieldNames, Class<?>[] types) {
        eventName = name;
        this.fieldNames = fieldNames;
        this.types = types;
        if (fieldNames.length != types.length) {
            throw new IllegalArgumentException("Invalid prototype, attribute array length and type array length is not match");
        }
    }

    void setAttribute(String[] fieldNames, Class<?>[] types) {
        this.fieldNames = fieldNames;
        this.types = types;
    }

    @Override
    public String getName() {
        return eventName;
    }

    @Override
    public Class<?> getNthType(int nth) {
        return types[nth];
    }

    @Override
    public String getNthFieldName(int nth) {
        return fieldNames[nth];
    }

    @Override
    public int getSize() {
        return fieldNames.length;
    }
}

public class EventPrototypeBuilder {

    List<String> fieldNameList = new ArrayList<>();
    List<Class<?>> typeList = new ArrayList<>();
    String name;

    public EventPrototypeBuilder(String name) {
        this.name = name;
    }

    public EventPrototypeBuilder addFieldNameType(String name, Class<?> clazz) {
        fieldNameList.add(name);
        typeList.add(clazz);
        return this;
    }

    public EventPrototype create() {
        return new GenericEventPrototype(name, (String[]) fieldNameList.toArray(), (Class<?>[]) typeList.toArray());
    }
}
