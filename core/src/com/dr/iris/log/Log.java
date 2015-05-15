package com.dr.iris.log;

import com.badlogic.gdx.Gdx;

/**
 * Created by Rayer on 5/1/15.
 * This log is for customized Iris log
 */
public class Log {

    static final String TAG = "IRIS";

    private String component_alias;

    public static Log getLogger(Class<?> clazz) {
        Log ret = new Log();
        String[] clazzNameSlice = clazz.getCanonicalName().split("\\.");
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < clazzNameSlice.length; ++i) {
            if (i == clazzNameSlice.length - 1) {
                sb.append(clazzNameSlice[i]);
                sb.append(":");
            } else {
                sb.append(clazzNameSlice[i].charAt(0));
                sb.append(".");
            }

        }

        ret.component_alias = sb.toString();
        return ret;
    }

    public static void debug_s(String message) {
        Gdx.app.debug(TAG, message);
    }

    public static void info_s(String message) {
        Gdx.app.log(TAG, message);
    }

    public static void error_s(String message) {
        Gdx.app.error(TAG, message);
    }

    public void debug(String message) {
        Gdx.app.debug(TAG, component_alias + message);
    }

    public void info(String message) {
        Gdx.app.log(TAG, component_alias + message);
    }

    public void error(String message) {
        Gdx.app.error(TAG, component_alias + message);
    }
}
