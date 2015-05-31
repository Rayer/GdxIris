package com.dr.iris.event;

/**
 * Created by Rayer on 5/31/15.
 */
public interface EventPrototype {

    String getName();

    Class<?> getNthType(int nth);

    String getNthFieldName(int nth);

    int getSize();
}
