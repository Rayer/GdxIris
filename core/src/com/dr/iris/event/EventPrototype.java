package com.dr.iris.event;

/**
 * Created by Rayer on 5/31/15.
 */
public interface EventPrototype {
    Class<?> getNthType();

    String getNthName();

    int getSize();
}
