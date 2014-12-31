package com.dr.poc.desktop;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopLauncher {

    static String SCENE_NAME = System.getProperty("SCENE_NAME") == null ? "com.dr.poc.Iris" : System.getProperty("SCENE_NAME");

    public static void main(String[] arg) {
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "Scene Demo";
        cfg.useGL30 = false;
        cfg.width = 720;
        cfg.height = 640;
        //new LwjglApplication(new Iris(), cfg);

        try {
            Class<?> clazz = Class.forName(SCENE_NAME);
            new LwjglApplication((ApplicationListener) clazz.newInstance(), cfg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}