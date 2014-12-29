package com.dr.poc;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Rayer on 12/28/14.
 */
public class CharacterRenderManager {
    static final String DATA_PATH = "data/";
    private static CharacterRenderManager defInst;
    Map<String, CharacterRenderInfo> cinfo_cache_map = new HashMap<>();

    public static CharacterRenderManager getInst() {
        if (defInst == null)
            defInst = new CharacterRenderManager();
        return defInst;
    }

    private File getPngPath(String character) {
        return new File(DATA_PATH + character + ".png");
    }

    private File getAtlasPath(String character) {
        return new File(DATA_PATH + character + ".atlas");
    }

    public CharacterRenderInfo getCharacter(String character) {

        if (cinfo_cache_map.containsKey(character))
            return cinfo_cache_map.get(character);

        if (!getPngPath(character).exists())
            throw new RuntimeException("character png is not found!");

        File atlas = getAtlasPath(character);
        if (!atlas.exists()) {
            createAtlas(character);
        }

        CharacterRenderInfo info = new CharacterRenderInfo();
        info.name = character;
        info.atlas = new TextureAtlas(DATA_PATH + character + ".atlas");

        cinfo_cache_map.put(character, info);

        return info;
    }

    private void createAtlas(String character) {
        File targetAtlas = new File(DATA_PATH + character + ".atlas");

        FileInputStream if_temp = null;
        FileOutputStream of_atlas = null;
        try {
            targetAtlas.createNewFile();
            of_atlas = new FileOutputStream(targetAtlas);

            File template = new File(DATA_PATH + "atlas.template");
            if_temp = new FileInputStream(template);

            of_atlas.write(("\n" + character + ".png\n").getBytes());

            byte[] b = new byte[1024];
            int off = 0;
            int len;
            while ((len = if_temp.read(b)) != -1) {
                of_atlas.write(b, off, len);
            }
            of_atlas.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if_temp.close();
                of_atlas.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }


    public void dispose() {
        for (CharacterRenderInfo info : cinfo_cache_map.values()) {
            info.dispose();
        }
    }
}
