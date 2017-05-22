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
        return getLogger(clazz.getCanonicalName());
    }

    public static Log getLogger(String canonicalName) {
        Log ret = new Log();
        String[] clazzNameSlice = canonicalName.split("\\.");
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

    public static Log getLogger() {
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();

        /*
        StackTraceElements[0] represents StackTraceElement itself, [1] represents Logger class
        So we need use [2]
         */
        StackTraceElement ste = stackTraceElements[2];
        return getLogger(ste.getClassName());
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
