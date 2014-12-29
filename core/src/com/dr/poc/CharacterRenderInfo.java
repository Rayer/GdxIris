package com.dr.poc;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;


/**
 * Created by Rayer on 12/28/14.
 */
public class CharacterRenderInfo {

    String name;
    TextureAtlas atlas;

    public void dispose() {
        atlas.dispose();
    }

//    enum STATE {
//        WALK,
//        STOP
//    }

    public Array<TextureAtlas.AtlasRegion> getAllRegion() {
        return atlas.getRegions();
    }

    public Array<TextureAtlas.AtlasRegion> getRegion(FACING facing) {
        //getAllRegion().
        return atlas.findRegions(facing.getAtlasDefine());
    }

    enum FACING {
        UP("walk-fu"),
        DOWN("walk-fd"),
        LEFT("walk-fl"),
        RIGHT("walk-fr");

        private String atlas_define;

        FACING(String atlas_define) {
            this.atlas_define = atlas_define;
        }

        String getAtlasDefine() {
            return atlas_define;
        }
    }
}
