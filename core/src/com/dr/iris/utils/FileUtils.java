package com.dr.iris.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.io.File;

/**
 * Created by shoi on 15/5/18.
 */
public class FileUtils {
    static final String DATA_PATH = "data/";

    public static FileHandle getRes(String fileName) {
        return Gdx.files.internal(DATA_PATH + fileName);
    }


}
