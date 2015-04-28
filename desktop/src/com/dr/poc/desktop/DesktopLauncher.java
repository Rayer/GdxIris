package com.dr.poc.desktop;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DesktopLauncher {

    static String SCENE_NAME = System.getProperty("SCENE_NAME") == null ? "com.dr.iris.Iris" : System.getProperty("SCENE_NAME");
    static Logger logger = LogManager.getLogger(DesktopLauncher.class);

    public static void main(String[] arg) {
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = SCENE_NAME;
        cfg.useGL30 = false;
        cfg.width = 720;
        cfg.height = 640;

        try {
            Class<?> clazz = Class.forName(SCENE_NAME);
            logger.info("Starting package : " + SCENE_NAME);
            new LwjglApplication((ApplicationListener) clazz.newInstance(), cfg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}