package com.dr.poc.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.dr.poc.SceneDemo;

public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "Scene Demo";
        cfg.useGL30 = false;
        cfg.width = 1280;
        cfg.height = 720;
        //new LwjglApplication(new Iris(), cfg);
        new LwjglApplication(new SceneDemo(), cfg);
    }
}
