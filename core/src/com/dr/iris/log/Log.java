package com.dr.iris.log;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;

/**
 * Created by Rayer on 5/1/15.
 */
public class Log {

    static final String TAG = "IRIS";

    static {
        Gdx.app.setLogLevel(Application.LOG_DEBUG);
    }

    public static void debug(String message) {
        Gdx.app.debug(TAG, message);
    }

    public static void info(String message) {
        Gdx.app.log(TAG, message);
    }

    public static void error(String message) {
        Gdx.app.error(TAG, message);
    }
}
