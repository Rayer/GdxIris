package com.dr.iris.Render;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.dr.iris.Objects.ObjectManager;
import com.dr.iris.effect.EffectManager;
import com.dr.iris.ui.UIObjectsManager;

/**
 * Created by Rayer on 1/1/15.
 */
public class IrisRenderer extends OrthogonalTiledMapRenderer {


    public IrisRenderer(TiledMap map, Batch batch) {
        super(map, batch);
    }

    public void render(float delta) {

        beginRender();
        MapLayers layers = map.getLayers();
        renderTileLayer((TiledMapTileLayer) layers.get("Ground"));

        ObjectManager.getInst().render(batch);

        UIObjectsManager.getInst().render(batch);
        renderTileLayer((TiledMapTileLayer) layers.get("Decoration"));

        EffectManager.getInst().render(batch);

        endRender();
    }
}
