package com.dr.poc.desktop;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.dr.iris.log.Log;

public class DesktopLauncher {

    static String SCENE_NAME = System.getProperty("SCENE_NAME") == null ? "com.dr.iris.Iris" : System.getProperty("SCENE_NAME");

    public static void main(String[] arg) {
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = SCENE_NAME;
        cfg.useGL30 = false;
        cfg.width = 800;
        cfg.height = 1024;

        try {
            Class<?> clazz = Class.forName(SCENE_NAME);
            new LwjglApplication((ApplicationListener) clazz.newInstance(), cfg);
            Log.info("Starting package : " + SCENE_NAME);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}