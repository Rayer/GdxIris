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

    void setAttribute(String[] fieldNames, Class<?>[] types) {
        this.fieldNames = fieldNames;
        this.types = types;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public Class<?> getNthType(int nth) {
        return null;
    }

    @Override
    public String getNthFieldName(int nth) {
        return null;
    }

    @Override
    public int getSize() {
        return 0;
    }
}

public class EventPrototypeBuilder {

    List<String> fieldNameList = new ArrayList<>();
    List<Class<?>> typeList = new ArrayList<>();

    EventPrototypeBuilder addFieldNameType(String name, Class<?> clazz) {
        fieldNameList.add(name);
        typeList.add(clazz);
        return this;
    }

    EventPrototype create() {
        return null;
    }
}
