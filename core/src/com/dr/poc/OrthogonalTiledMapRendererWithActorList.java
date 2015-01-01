package com.dr.poc;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rayer on 1/1/15.
 */
public class OrthogonalTiledMapRendererWithActorList extends OrthogonalTiledMapRenderer {

    List<Actor> actorList = new ArrayList<>();

    public OrthogonalTiledMapRendererWithActorList(TiledMap map, Batch batch, List<Actor> actorList) {
        super(map, batch);
        this.actorList = actorList;
    }

    public void render(float delta) {

        beginRender();
        MapLayers layers = map.getLayers();
        renderTileLayer((TiledMapTileLayer) layers.get("Ground"));

        for (Actor actor : actorList) {
            actor.act(delta);
            actor.draw(batch, 1);
        }


        renderTileLayer((TiledMapTileLayer) layers.get("Decoration"));
        endRender();
    }
}
